package com.test.jpython;

import org.python.util.PythonInterpreter;

public class First {
    public static void main(String[] args) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("days=('mod','Tue','Wed','Thu','Fri','Sat','Sun'); ");
        interpreter.exec("print days[1];");
        String aa = "aa.bb";
        System.out.println(aa.substring(aa.indexOf('.') + 1));
    }
}
