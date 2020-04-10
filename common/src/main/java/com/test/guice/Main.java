package com.test.guice;

import com.google.inject.Injector;

public class Main {
    public static void main(String[] args) {
        CustomModuleBuilder moduleBuilder = new CustomModuleBuilder();
        moduleBuilder.add(new ToolModule());
        moduleBuilder.add(new HumanModule());
        Injector injector = moduleBuilder.createInjector();
        Person person = injector.getInstance(Person.class);
        person.startwork();
    }
}
