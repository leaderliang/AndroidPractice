package com.android.handlerthread;

import java.util.ArrayList;

/**
 * TODO
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/06/23 17:39
 */
class JavaTest {

    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList<String>();

        arrayList.add("1");
        arrayList.add("3");
        arrayList.add("112");
        arrayList.add("1345");
        arrayList.add("123498");
        arrayList.add("1345");

        for (int i = 0; i < arrayList.size(); i++) {
            if (i == 3){
                arrayList.remove(i);
            }
            System.out.println("i= " + i + " "+ arrayList.get(i));
        }

    }



}
