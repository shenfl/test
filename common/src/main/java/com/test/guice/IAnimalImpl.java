package com.test.guice;

public class IAnimalImpl implements IAnimal {
    @Override
    public void work() {
        System.out.println("animals can also do work");
    }
}