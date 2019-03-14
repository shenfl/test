package com.test.es.hadoop;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author y15079
 * @create 2018-03-22 16:48
 * @desc
 **/
public class InfoBean implements Writable {

    private int order_id;
    private String dateString;
    private String p_id;
    private int amount;
    private String pname;
    private int category_id;
    private float price;

    //flag=0表示这个对象是封装订单表记录
    //flag=1表示这个对象是封装产品信息记录
    private String flag;

    public InfoBean() {
    }

    public void set(int order_id, String dateString, String p_id, int amount, String pname, int category_id, float price, String flag) {
        this.order_id = order_id;
        this.dateString = dateString;
        this.p_id = p_id;
        this.amount = amount;
        this.pname = pname;
        this.category_id = category_id;
        this.price = price;
        this.flag = flag;
    }

    /**
     * private int p_id;
     private String pname;
     private int category_id;
     private float price;
     * @param dataOutput
     * @throws IOException
     */
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(order_id);
        dataOutput.writeUTF(dateString);
        dataOutput.writeUTF(p_id);
        dataOutput.writeUTF(pname);
        dataOutput.writeInt(category_id);
        dataOutput.writeFloat(price);
        dataOutput.writeUTF(flag);
    }

    public void readFields(DataInput dataInput) throws IOException {
        this.order_id = dataInput.readInt();
        this.dateString = dataInput.readUTF();
        this.p_id = dataInput.readUTF();
        this.pname = dataInput.readUTF();
        this.category_id = dataInput.readInt();
        this.price = dataInput.readFloat();
        this.flag = dataInput.readUTF();
    }

    @Override
    public String toString() {
        return "InfoBean{" +
                "order_id=" + order_id +
                ", dateString='" + dateString + '\'' +
                ", p_id=" + p_id +
                ", pname='" + pname + '\'' +
                ", category_id=" + category_id +
                ", price=" + price +
                ", flag='" + flag + '\'' +
                '}';
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
