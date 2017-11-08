package proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理类
 * @author light
 */
public class DynamicProxy implements InvocationHandler {
    //object 为代理的对象,设计成Object，更具有通用性
    private Object object;
    public DynamicProxy(Object object) {
        this.object = object;
    }

    public DynamicProxy() {
    }

    //绑定委托对象，并返回代理类
    public Object bind(Object object){
        this.object = object;
        //绑定该类实现的所有接口，取得代理类
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),object.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Enter "+object.getClass().getName()+"-"+method.getName()+",with arguments"+args);
        Object result = method.invoke(object,args);
        System.out.println("before return "+result);
        return result;
    }
}
