import org.aopalliance.intercept.Interceptor;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 分页
 *
 * @author wzm
 * @create 2017-11-15-16:14
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PagePlugin implements Interceptor {
    // 数据库方言
    private static String dialect = "";
    // mapper.xml中需要拦截的ID(正则匹配)
    private static String pageSqlId = "";

    public Object intercept(Invocation ivk) throws Throwable {
        if (ivk.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
            BaseStatementHandler delegate =
                    (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler,
                            "delegate");
            MappedStatement mappedStatement =
                    (MappedStatement) ReflectHelper
                            .getValueByFieldName(delegate, "mappedStatement");

            if (mappedStatement.getId().matches(pageSqlId)) {
                // 拦截要分页的SQL
                BoundSql boundSql = delegate.getBoundSql();
                // 分页SQL<select>中parameterType属对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为
                Object parameterObject = boundSql.getParameterObject();
                if (parameterObject == null) {
                    throw new NullPointerException("parameterObject尚未实例化！");
                } else {
                    Connection connection = ((Connection) ivk.getArgs()[0]);
                    String sql = boundSql.getSql();
                    // 记录统计
                    String countSql = "select count(0) from (" + sql + ") tmp_count";
                    PreparedStatement countStmt = connection.prepareStatement(countSql);
                    BoundSql countBS =
                            new BoundSql(mappedStatement.getConfiguration(), countSql,
                                    boundSql.getParameterMappings(), parameterObject);
                    setParameters(countStmt, mappedStatement, countBS, parameterObject);
                    ResultSet rs = countStmt.executeQuery();
                    int count = 0;
                    if (rs.next()) {
                        count = rs.getInt(1);
                    }
                    rs.close();
                    countStmt.close();
                    Page<?> page = null;
                    if (parameterObject instanceof Page) { // 参数就是Page实体
                        page = (Page<?>) parameterObject;
                        page.setTotalCount(count);
                    } else if (parameterObject instanceof Map) {
                        page = (Page<?>) ((Map<?, ?>) parameterObject).get("page");
                        page.setTotalCount(count);
                    } else { // 参数为某个实体，该实体拥有Page属
                        Field pageField =
                                ReflectHelper.getFieldByFieldName(parameterObject, "page");
                        if (pageField != null) {
                            page =
                                    (Page<?>) ReflectHelper.getValueByFieldName(parameterObject,
                                            "page");
                            if (page == null) {
                                page = new Page<Object>();
                            }
                            // 注释
                            page.setTotalCount(count);
                            // 通过反射，对实体对象设置分页对象
                            ReflectHelper.setValueByFieldName(parameterObject, "page", page);
                        } else {
                            throw new NoSuchFieldException(parameterObject.getClass().getName()
                                    + "不存在 page 属！");
                        }
                    }
                    String pageSql = generatePageSql(sql, page);
                    // 将分页sql语句反射回BoundSql.
                    ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);
                }
            }
        }
        return ivk.proceed();
    }

    /**
     *
     * @throws java.sql.SQLException
     */
    @SuppressWarnings("unchecked")
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement,
                               BoundSql boundSql, Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters")
                .object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject =
                    parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value =
                                    configuration.newMetaObject(value).getValue(
                                            propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    @SuppressWarnings("rawtypes")
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter "
                                + propertyName + " of statement " + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }

    /**
     * 根据数据库方言，生成特定的分页sql
     */
    private String generatePageSql(String sql, Page<?> page) {
        if (page != null && dialect != null && !"".equals(dialect)) {
            StringBuffer pageSql = new StringBuffer();
            if ("mysql".equals(dialect)) {
                pageSql.append(sql);
                pageSql.append(" limit " + page.getCurrentResult() + "," + page.getPageSize());
            } else if ("oracle".equals(dialect)) {
                pageSql.append("select * from ( select * from ( select ulccc.*,rownum as dpRowNo  from  (  ");
                pageSql.append(sql);
                pageSql.append(") ulccc ) ulbbb   where  ulbbb.dpRowNo<=")
                        .append(page.getCurrentResult() + page.getPageSize());
                pageSql.append(") ulaaa  where  ulaaa.dpRowNo> ")
                        .append(page.getCurrentResult());
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, (org.apache.ibatis.plugin.Interceptor) this);
    }

    public void setProperties(Properties p) {
        dialect = p.getProperty("dialect");
        pageSqlId = p.getProperty("pageSqlId");
    }

}
