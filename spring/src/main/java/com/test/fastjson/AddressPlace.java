package com.test.fastjson;

public class AddressPlace {
    private String province;
    private String place;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "AddressPlace{" +
                "province='" + province + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
