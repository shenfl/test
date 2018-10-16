package com.test.arithmetic;

/**
 * Created by shenfl on 2018/8/16
 */
import java.util.Arrays;
import java.util.Random;

public class CardGame {
    public static void main(String[] args) {
        String[] allCards = new String[54];
        allCards = allCard();
        String[] ranCards = ranCard(allCards, 9);
        String[] persons = { "张三", "李四", "王五" };
        System.out.println(Arrays.toString(ranCards));
        print(ranCards, persons);
        String[][] st = new String[3][3];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                st[i][j] = ranCards[index++];
            }
        }
        long[] score = new long[3];
        for (int i = 0; i < persons.length; i++) {
            if (sanEqual(st[i])) {
                score[i] += transform(st[i])[0] * 1000000000000l;
            }
            if (tongHua(st[i])) {
                score[i] += 10000000000l;
            }
            if (shun(st[i])) {
                score[i] += 100000000;
            }
            if (twoEqual(st[i])) {
                score[i] += 1000000 * transform(st[i])[1];
            }
            score[i] = score[i] + transform(st[i])[2] * 10000
                    + transform(st[i])[1] * 100 + transform(st[i])[0];
        }
        String str = (score[0] > score[1]) ? (score[0] > score[2] ? "张三"
                : "王五") : (score[1] > score[2] ? "李四" : score[0] > score[2] ? "张三" : "王五");
        System.out.println(str + "赢");
    }

    public static String[] allCard() {
        String[] color = { "红桃", "黑桃", "梅花", "方块" };
        String[] figure = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J",
                "Q", "K", "A" };
        String[] cards = new String[54];
        int index = 0;
        for (int i = 0; i < color.length; i++) {
            for (int j = 0; j < figure.length; j++) {
                cards[index++] = color[i] + figure[j];
            }
        }
        cards[52] = "大王";
        cards[53] = "小王";
        return cards;
    }

    public static String[] ranCard(String[] allCards, int num) {
        String[] ranCards = new String[num];
        Random rd = new Random();
        for (int i = 0; i < ranCards.length; i++) {
            int index = rd.nextInt(allCards.length - 2);
            ranCards[i] = allCards[index];
            allCards = cutCard(allCards, index);
        }
        return ranCards;
    }

    public static String[] cutCard(String[] allCards, int index) {
        String[] cards = new String[allCards.length - 1];
        for (int i = 0, j = 0; i < allCards.length && j < cards.length; i++, j++) {
            if (i == index) {
                i++;
            }
            cards[j] = allCards[i];
        }
        return cards;
    }

    public static void print(String[] ranCards, String[] person) {
        int index = 0;
        for (int i = 0; i < person.length; i++) {
            System.out.print(person[i] + ":");
            for (int j = 0; j < 3; j++) {
                System.out.print(ranCards[index++]);
            }
            System.out.println();
        }
    }

    public static int[] transform(String[] cards) {
        int[] arr = new int[cards.length];
        String[] st = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q",
                "K", "A" };
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < st.length; j++) {
                if (cards[i].charAt(2) == st[j].charAt(0)) {
                    arr[i] = j;
                }
            }
        }
        Arrays.sort(arr);
        return arr;
    }

    public static boolean sanEqual(String[] str) {
        if (str[0].charAt(2) == str[1].charAt(2)
                && str[1].charAt(2) == str[2].charAt(2)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean tongHua(String[] str) {
        if (str[0].charAt(0) == str[1].charAt(0)
                && str[1].charAt(0) == str[2].charAt(0)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean shun(String[] str) {
        if (transform(str)[1] - transform(str)[0] == 1
                && transform(str)[2] - transform(str)[1] == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean twoEqual(String[] str) {
        if (transform(str)[0] == transform(str)[1]
                || transform(str)[1] == transform(str)[2]) {
            return true;
        } else {
            return false;
        }
    }
}
