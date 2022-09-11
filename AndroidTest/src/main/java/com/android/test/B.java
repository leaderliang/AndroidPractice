package com.android.test;

import java.util.Comparator;


/**
 * TODO
 * <p>
 * Note:
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @since 2022/08/12 10:41
 */
public class B implements A, Comparator {
    @Override
    public void test() {
        int a = num + 1;

        String s = Constants.Strings.DETAIL_ORDER_CODE;
    }

    @Override
    public void develop() {

    }

    @Override
    public void test2() {

    }


    @Override
    public void test3() {
        A.super.test3();
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
