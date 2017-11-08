package proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类
 * @author light
 */
public class DynamicProxy implements InvocationHandler {
    //object 为代理的对象

    public DynamicProxy(Object object) {
        this.object = object;
    }

    //目标对象的引用，设计成Object，更具有通用性
    private Object object;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Enter "+object.getClass().getName()+"-"+method.getName()+",with arguments"+args);
        Object result = method.invoke(object,args);
        System.out.println("before return "+result);
        return result;
    }
}
