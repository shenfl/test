package com.test.asm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenfl on 2018/7/31
 */
public class ListHolder {
    public List<String> getList() {
        List<String> data = new ArrayList<>();
        data.add("ll");
        return data;
    }
}
