package com.test.ongl;

import ognl.DefaultClassResolver;

public class OgnlClassResolver extends DefaultClassResolver {

    @Override
    protected Class toClassForName(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

}
