package com.test.spring.propertyRegistry;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import java.beans.PropertyEditorSupport;

public class CustomCarEditor extends PropertyEditorSupport implements PropertyEditorRegistrar {

    //1. 将字面值转换为属性类型对象
    public void setAsText(String text){
        if(text == null || text.indexOf(",") == -1){
            throw new IllegalArgumentException("设置的字符串格式不正确");
        }
        String[] infos = text.split(",");
        Car car = new Car();
        car.setBrand(infos[0]);
        car.setMaxSpeed(Integer.parseInt(infos[1]));
        car.setPrice(Double.parseDouble(infos[2]));

        //2. 调用父类的setValue()方法设置转换后的属性对象
        setValue(car);
    }

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Car.class, this);
    }
}