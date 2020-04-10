package com.test.guice;

import com.google.inject.AbstractModule;

public class HumanModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Person.class).asEagerSingleton();
    }
}