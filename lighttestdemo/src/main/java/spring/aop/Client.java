package spring.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {
        //错误的实例-没有使用spring的方式
        /*Person xiaoming = new PersonImpl();
        xiaoming.eat();
        xiaoming.write();*/
        ApplicationContext cx = new ClassPathXmlApplicationContext("aop.xml");
        Person person = (Person) cx.getBean("personImpl");
        person.eat();
        person.write();
    }
}
