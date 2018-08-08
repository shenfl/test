package com.test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shenfl on 2018/8/8
 */
public class IKDictChange {
    public static void main(String[] args) throws IOException {
        Set<String> set = new HashSet<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/dasouche1/IdeaProjects/test/common/src/main/resources/ext.dic"), Charset.forName("utf-8")));
        String line;
        while ((line = br.readLine()) != null) {
            set.add(line);
        }
        System.out.println(set.size());

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("/Users/dasouche1/IdeaProjects/test/common/src/main/resources/good.dic"), Charset.forName("utf-8")));

        int count = 0;
        br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/dasouche1/IdeaProjects/test/common/src/main/resources/main2012.dic"), Charset.forName("utf-8")));
        b:while ((line = br.readLine()) != null) {
            for (String s : set) {
                if (equals(line, s) == 1) continue b;
            }
            pw.println(line);
            count++;
        }
        pw.flush();
        pw.close();
        System.out.println(count);
    }

    private static int equals(String line, String word) {
        if (line.length() < word.length()) return -1;
        int i;
        if (line.length() > word.length()) {
            for (i = 0; i < word.length(); i++) {
                if (word.charAt(i) != line.charAt(line.length() - 1 - i)) break;
            }
            if (i > 0) {
                return 1;
            }
            i = 0;
            for (; i < word.length(); i++) {
                if (word.charAt(word.length() - 1 - i) != line.charAt(i)) break;
            }
            if (i > 0) return 1;
        } else {
            for (i = 0; i < word.length(); i++) {
                if (word.charAt(word.length() - 1 - i) != line.charAt(i)) break;
            }
            if (i > 0) return 1;
        }
        return -1;
    }
}
