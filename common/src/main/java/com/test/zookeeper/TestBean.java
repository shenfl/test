package com.test.zookeeper;

import org.apache.jute.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shenfl on 2018/6/13
 */
public class TestBean implements Record {

    private int intV;
    private String stringV;

    public TestBean() {

    }

    public TestBean(int intV, String stringV) {
        this.intV = intV;
        this.stringV = stringV;
    }

    public int getIntV() {
        return intV;
    }

    public String getStringV() {
        return stringV;
    }
//get/set方法

    @Override
    public void deserialize(InputArchive archive, String tag)
            throws IOException {
        archive.startRecord(tag);
        this.intV = archive.readInt("intV");
        this.stringV = archive.readString("stringV");
        archive.endRecord(tag);
    }

    @Override
    public void serialize(OutputArchive archive, String tag) throws IOException {
        archive.startRecord(this, tag);
        archive.writeInt(intV, "intV");
        archive.writeString(stringV, "stringV");
        archive.endRecord(this, tag);
    }

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryOutputArchive boa = BinaryOutputArchive.getArchive(baos);
        new TestBean(1, "testbean1").serialize(boa, "tag1");
        byte array[] = baos.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(array);
        BinaryInputArchive bia = BinaryInputArchive.getArchive(bais);
        TestBean newBean1 = new TestBean();
        newBean1.deserialize(bia, "tag1");

        System.out.println("intV = " + newBean1.getIntV() + ",stringV = "
                + newBean1.getStringV());
        bais.close();
        baos.close();


        AtomicInteger integer = new AtomicInteger(100);
        boolean b = integer.compareAndSet(102, 101);
        System.out.println(b);
    }
}
