package com.test.spring.bean;

import com.test.spring.Student;
import com.test.spring.User;
import com.test.spring.Welcomer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
public class TestSpringPrototype {

    @Autowired
    private Welcomer welcomer;

    @Autowired
    private BeanFactory beanFactory;
    @Resource(name = "user1")
    private User user1;
    @Resource(name = "user2")
    private User user2;
    @Autowired
    private Student student;

    @Test
    public void test() {
        System.out.println(this.welcomer);
        System.out.println(welcomer.getMessage());
        System.out.println("-------------");
        System.out.println(beanFactory.getClass());
        Welcomer welcomer = (Welcomer) beanFactory.getBean("welcomer");
        System.out.println(welcomer);
        System.out.println(welcomer.getMessage());
        welcomer = (Welcomer) beanFactory.getBean("welcomer");
        System.out.println(welcomer);
        System.out.println(welcomer.getMessage());
        System.out.println(student);
    }

    /**
     * http://blog.csdn.net/ya_1249463314/article/details/68484422
     */
    @Test
    public void testExpression() {
        user1.printUser();
        user2.printUser();
        ExpressionParser parser =new SpelExpressionParser();
        assertThat(parser.parseExpression("3 between {2,5}").getValue(Boolean.class), is(true));
    }

}
