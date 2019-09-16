package com.test.spi;

public class TestSPI {
    public static void main(String[] args) {
        ExtensionFactory spi = ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getExtension("spi");
        System.out.println(spi);
    }
}
