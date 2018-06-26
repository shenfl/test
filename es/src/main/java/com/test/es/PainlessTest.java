package com.test.es;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.painless.*;
import org.elasticsearch.painless.antlr.Walker;
import org.elasticsearch.script.CompiledScript;
import org.elasticsearch.script.ExecutableScript;
import org.elasticsearch.script.ScriptType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PainlessTest {
    public static void main(String[] args) {
//        String script = "List x = new ArrayList(); x.add(2); x.add(3); x.add(-2); Iterator y = x.iterator(); int total = 0; while (y.hasNext()) total += y.next(); return total;";
//        String script = "int a = 1000; return params['a.aa'] + params.b + a;"; // 两种调用方式
//        String script = "double aa = GeoPoints.getLat(); int a = 1000; return params['a.aa'] + params.b + a;"; //
//        String script = "Map m = new HashMap(); m.put(\"ss\", 32); int a = 1000; return m;"; // 两种调用方式
        String script = "if (params.c == null || params.c.length() == 0) {return null;} else { List all = new ArrayList(); StringBuilder one = new StringBuilder(); for(int i = 0; i < params.c.length(); i++) { if(params.c.charAt(i) == ',') { all.add(one.toString()); one = new StringBuilder();} else {one.append(params.c.charAt(i));}} all.add(one.toString()); return all.toArray();}"; // array
        PainlessScriptEngineService scriptEngine = new PainlessScriptEngineService(Settings.EMPTY);
        Map<String,String> compilerSettings = new HashMap<>();
        compilerSettings.put(CompilerSettings.INITIAL_CALL_SITE_DEPTH, "10");

        // 5.5.1
        Definition definition = Definition.BUILTINS;
        ScriptInterface scriptInterface = new ScriptInterface(definition, GenericElasticsearchScript.class);
        CompilerSettings pickySettings = new CompilerSettings();
        pickySettings.setPicky(true);
//        pickySettings.setRegexesEnabled(CompilerSettings.REGEX_ENABLED.get(Settings.EMPTY));
        pickySettings.setRegexesEnabled(true);
        Walker.buildPainlessTree(scriptInterface, "ssss", script, pickySettings,
                definition, null);

        // 5.3.3
//        CompilerSettings pickySettings = new CompilerSettings();
//        pickySettings.setPicky(true);
//        pickySettings.setRegexesEnabled(CompilerSettings.REGEX_ENABLED.get(Settings.EMPTY));
//        Walker.buildPainlessTree("ssss", script, pickySettings,null);

        Object object = scriptEngine.compile(null, script, compilerSettings);
        CompiledScript compiled = new CompiledScript(ScriptType.INLINE, "ssss", "painless", object);

        long start = System.currentTimeMillis();
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < 1; i++) {
            params.put("a.aa", 100);
            params.put("b", i);
            params.put("c", "klsfe,krr,ww");
            ExecutableScript executableScript = scriptEngine.executable(compiled, params);
            Object run = executableScript.run();
            System.out.println(run);
            System.out.println(((Object[])run).length);
            System.out.println(Arrays.toString((Object[])run));
        }
        System.out.println("cost: " + (System.currentTimeMillis() - start));
    }

    private static Random random() {
        return new Random();
    }
}
