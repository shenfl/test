package com.test.java;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by shenfl on 2018/5/24
 * https://blog.csdn.net/zmx729618/article/details/54093532
 */
public class TestWeakReference {

    public static void main(String[] args) {
        Car car = new Car(22000,"silver");
        ReferenceQueue<Car> queue = new ReferenceQueue<>();
        WeakReference<Car> weakCar = new WeakReference<Car>(car, queue);
        int i=0;
        while(true) {
//            System.out.println("here is the strong reference 'car' "+car);
            if(weakCar.get()!=null){
                i++;
                System.out.println("Object is alive for "+i+" loops - "+weakCar);
                System.out.println(weakCar.isEnqueued());
                Reference<? extends Car> poll = queue.poll();
                if (poll != null) System.out.println("equals: " + (weakCar == poll));
                System.out.println(poll); // 一直是null
                System.out.println("----------");
            } else {
                System.out.println(weakCar.isEnqueued());
                Reference<? extends Car> poll = queue.poll();
                System.out.println(weakCar.isEnqueued());
                if (poll != null) System.out.println("equals: " + (weakCar == poll));
                System.out.println(poll); // 当被回收后能poll出来WeakReference对象
                System.out.println("Object has been collected.");
                System.out.println("----------");
                break;
            }
        }
    }

    static class Car {
        private double price;
        private String colour;

        public Car(double price, String colour){
            this.price = price;
            this.colour = colour;
        }

        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
        public String getColour() {
            return colour;
        }
        public void setColour(String colour) {
            this.colour = colour;
        }

        public String toString(){
            return colour +"car costs $"+price;
        }

    }
}
