package com.test.fastjson;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Person {
    private int id;
    private String name;
    @JSONField(name = "address_full")
    private AddressPlace address;
    @JSONField(name = "addresses")
    private List<AddressPlace> addresses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressPlace getAddress() {
        return address;
    }

    public void setAddress(AddressPlace address) {
        this.address = address;
    }

    public List<AddressPlace> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressPlace> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", addresses=" + addresses +
                '}';
    }
}
