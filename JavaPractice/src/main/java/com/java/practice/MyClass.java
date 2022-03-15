package com.java.practice;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyClass {

    private int stackLength = 0;

    private void testStack() {
        stackLength++;
        testStack();
    }

    public static void main(String[] args) {
        /*System.out.println(Math.pow(2, 32) / 1024 / 1024);*/
        MyClass myClass = new MyClass();
        try {
            myClass.testStack();
        } catch (Throwable e) {
            System.out.println(e.toString() + "-----" + myClass.stackLength);
            throw e;
        }

    }


}