package spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {
        ApplicationContext cx = new ClassPathXmlApplicationContext("aop.xml");
        Person person = (Person) cx.getBean("personImpl");
        person.eat();
        person.write();
    }
}
