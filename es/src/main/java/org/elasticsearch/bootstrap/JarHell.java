package org.elasticsearch.bootstrap;

import java.net.URL;

/**
 * Created by shenfl on 2018/5/7
 */
public class JarHell {
    private JarHell() {}
    public static void checkJarHell() throws Exception {}
    public static void checkJarHell(URL urls[]) throws Exception {}
    public static void checkVersionFormat(String targetVersion) {}
    public static void checkJavaVersion(String resource, String targetVersion) {}
    public static URL[] parseClassPath() {return new URL[]{};}
}
