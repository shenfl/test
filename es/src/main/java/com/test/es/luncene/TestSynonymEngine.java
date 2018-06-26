package com.test.es.luncene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestSynonymEngine implements SynonymEngine {

    public static final Map<String, String[]> map = new HashMap<>();

    static {
        map.put("quick", new String[]{"fast", "speedy"});
    }

    @Override
    public String[] getSynonyms(String s) throws IOException {
        return map.get(s);
    }
}
