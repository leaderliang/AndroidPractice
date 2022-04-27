package com.java.practice;


/**
 * TODO
 *
 * 因为字符串是常量，直接初始化的话，字符串引用会指向堆中字符串常量池里的字符串对象，如果新初始化的值在字符串常量里有，就不会重新创建一个，会指向同一个对象。
 * 而new出来的对象指向的是堆里的某个字符串对象。
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @version 1.0.0
 * @since 2022/04/19 22:45
 */
public class StringTest {

    String str  = new String("Hello");
    char[] mChars = {'a','b','c'};


    public static void main(String[] args) {
        String s1 = "HelloWorld";
        String s2 = "Hello" + new String("World");
//        String s2 = new String("HelloWorld");
        String s3 = "HelloWorld";
        System.out.println("比较 == ");
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s2 == s3);
        System.out.println("\n比较 equals");
        System.out.println(s1.equals(s2));
        System.out.println(s1.equals(s3));


        StringTest stringTest = new StringTest();
        stringTest.changeVaule(stringTest.str, stringTest.mChars);
        System.out.println("\n"+stringTest.str);
        System.out.println(stringTest.mChars);

    }

    public void changeVaule(String str,char[] mChars){
        str = "HelloWorld";
        mChars[0] = 'b';
        mChars[1] = 'b';
    }
}
