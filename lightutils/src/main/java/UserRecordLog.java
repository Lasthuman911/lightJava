import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author
 * @create 2017-11-15-16:26
 */
@Aspect
@Component
public class UserRecordLog {
    private static String[] types = { "java.lang.Integer", "java.lang.Double", "java.lang.Float", "java.lang.Long", "java.lang.Short", "java.lang.Byte", "java.lang.Boolean", "java.lang.Char", "java.lang.String", "int", "double", "long", "short", "byte", "boolean", "char", "float" };

    private final Logger logger = LoggerFactory.getLogger(UserRecordLog.class);
    private long start = 0;

    @Before("execution(* com..*.*(..))")
    public void before(JoinPoint joinpoint) {
        logger.info("/" + joinpoint.getSignature().getName() + ".do请求方法开始执行==> ");
        start = System.currentTimeMillis();
    }

    @After("execution(* com..*.*(..))")
    public void after(JoinPoint joinpoint) {
        long l = System.currentTimeMillis() - start;
        logger.info("/" + joinpoint.getSignature().getName() + ".do请求方法执行完成，耗时==> " + l);
    }

    /**
     * <p>
     * Title: searchControllerCall
     * </p>
     * <p>
     * Description: searchControllerCall
     * </p>
     */
    @Pointcut("execution(* com..*.*(..))")
    public void searchControllerCall() {
    }

    /**
     * <p>
     * Title: searchControllerCallCalls
     * </p>
     * <p>
     * Description: searchControllerCallCalls
     * </p>
     *
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
    /*@AfterReturning(value = "searchControllerCall()", argNames = "rtv", returning = "rtv")*/
    public void searchControllerCallCalls(JoinPoint joinPoint, Object rtv) throws Throwable {
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String methodName = joinPoint.getSignature().getName();

        String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName);

        String logContent = writeLogInfo(paramNames, joinPoint);
    /*    if(CurrentUser.getUser()!=null){
            String user = CurrentUser.getUser().getUsername();
            logger.info("user:" + user);
        }*/
        logger.info("operation[" + "clazzName: " + clazzName + ", methodName:" + methodName + ", param:" + logContent + "]");

    }
    /**
     * <p>
     * Title: writeLogInfo
     * </p>
     * <p>
     * Description: writeLogInfo
     * </p>
     *
     * @param paramNames
     * @param joinPoint
     * @return
     */
    private static String writeLogInfo(String[] paramNames, JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        boolean clazzFlag = true;
        for (int k = 0; k < args.length; k++) {
            Object arg = args[k];
            if (null == arg) {
                continue;
            }
            sb.append(paramNames[k] + " ");
            // 获取对象类型
            String typeName = arg.getClass().getName();

            for (String t : types) {
                if (t.equals(typeName)) {
                    sb.append("=" + arg + "; ");
                }
            }
            if (clazzFlag) {
                sb.append(getFieldsValue(arg));
            }
        }
        return sb.toString();
    }

    /**
     * 得到方法参数的名称
     *
     * @param cls
     * @param clazzName
     * @param methodName
     * @return string[]
     * @throws NotFoundException
     */
    private static String[] getFieldsName(Class<?> cls, String clazzName, String methodName) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);

        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++) {
            // paramNames即参数名
            paramNames[i] = attr.variableName(i + pos);
        }
        return paramNames;
    }

    /**
     * 得到参数的值
     *
     * @param obj
     * @return string
     */
    public static String getFieldsValue(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        String typeName = obj.getClass().getName();
        for (String t : types) {
            if (t.equals(typeName)) {
                return "";
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("【");
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                for (String str : types) {
                    if (f.getType().getName().equals(str)) {
                        sb.append(f.getName() + " = " + f.get(obj) + "; ");
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append("】");
        return sb.toString();
    }

}
