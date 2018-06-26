package com.test.antlr;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEngineDemo {
    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine se = manager.getEngineByName("js");
        String str = "\'\u5236\'";// 传 "\u5236", JS是认不出来的。 传"\'u5236\'"
        String result =  (String) se.eval(str);
        System.out.println(result);
    }
}
