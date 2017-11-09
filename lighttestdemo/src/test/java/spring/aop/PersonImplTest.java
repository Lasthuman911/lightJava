package spring.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * aop的简单单元测试类
 * SpringJUnit4ClassRunner 需要添加spring-test jar包
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:aop.xml")
public class PersonImplTest {
    @Autowired
    private Person person;
    @Test
    public void eat() throws Exception {
        person.eat();
    }

    @Test
    public void write() throws Exception {
        person.write();
    }

}