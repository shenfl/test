package com.test.spring.bean;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestPrototype {
    @Test
    public void test() throws CloneNotSupportedException {
        Robot firstRobot = new Robot("Droid#1");
        Robot secondRobot = (Robot) firstRobot.clone();
        assertTrue("Cloned robot's instance can't be the same as the"
                        +" source robot instance",
                firstRobot != secondRobot);
        assertTrue("Cloned robot's name should be '"+firstRobot.getName()+"'"
                        +" but was '"+secondRobot.getName()+"'",
                secondRobot.getName().equals(firstRobot.getName()));
    }
    class Robot implements Cloneable {
        private String name;

        public Robot(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
