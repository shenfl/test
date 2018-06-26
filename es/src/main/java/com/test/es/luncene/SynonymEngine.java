package com.test.es.luncene;

import java.io.IOException;

public interface SynonymEngine {
    String[] getSynonyms(String s) throws IOException;
}
