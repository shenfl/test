package com.test.ongl;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import ognl.SimpleNode;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestOngl {
    public static void main(String[] args) throws OgnlException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "shenfl");
        OgnlContext context = (OgnlContext)Ognl.createDefaultContext(map, new OgnlMemberAccess(), new OgnlClassResolver(), null);
        Object value = Ognl.getValue(Ognl.parseExpression("name"), context, map);
        System.out.println(value);

        Student student = new Student(88, "hehe");
        context = (OgnlContext)Ognl.createDefaultContext(null, new OgnlMemberAccess(), new OgnlClassResolver(), null);
        context.setRoot(student);
        value = Ognl.getValue(Ognl.parseExpression("name"), context, student);
        System.out.println(value);
    }
    @Test
    /**
     * https://github.com/jkuhnert/ognl/blob/master/src/test/java/org/ognl/test/ChainTest.java
     */
    public void test1() throws OgnlException {
        OgnlContext context = (OgnlContext) Ognl.createDefaultContext(null, new OgnlMemberAccess());

        SimpleNode node = (SimpleNode) Ognl.parseExpression("#name");
        assertFalse(node.isChain(context));

        node = (SimpleNode) Ognl.parseExpression("#name.lastChar");
        assertTrue(node.isChain(context));

        node = (SimpleNode) Ognl.parseExpression("#{name.lastChar, #boo}");
        assertTrue(node.isChain(context));

        node = (SimpleNode) Ognl.parseExpression("boo = #{name.lastChar, #boo, foo()}");
        assertTrue(node.isChain(context));

        node = (SimpleNode) Ognl.parseExpression("{name.lastChar, #boo, foo()}");
        assertTrue(node.isChain(context));

        node = (SimpleNode) Ognl.parseExpression("(name.lastChar, #boo, foo())");
        assertTrue(node.isChain(context));
    }

    static class Student {
        int id;
        String name;
        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
