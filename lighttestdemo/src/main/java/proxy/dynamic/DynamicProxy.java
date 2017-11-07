package proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理
 * @author light
 */
public class DynamicProxy implements InvocationHandler {
    //object 为代理的对象

    public DynamicProxy(Object object) {
        this.object = object;
    }

    private Object object;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("befor");
        Object result = method.invoke(object,args);
        System.out.println("after");
        return result;
    }
}
