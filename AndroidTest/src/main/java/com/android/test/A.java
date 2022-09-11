package com.android.test;

/**
 * TODO
 * <p>
 * Note:
 * 1、Java8对接口进行了修改：
 * <p>
 * Java8之前，接口中的成员只有两种：
 * （1）全局的静态的常量：public static final，可以省略
 * （2）公共的抽象的方法：public abstract，可以省略
 * <p>
 * Java8之后，接口又增加了两种新成员：（可以有方法体）
 * （3）静态方法：public static，不可以省略
 * 调用方式：接口名.静态方法(实参列表)
 * （4）默认方法：public default，不可以省略
 * 接口中权限修饰符都是public ，且都可以省略不写
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @since 2022/08/12 10:40
 */
interface A {
    public static final int num = 2;

    void test();

    void develop();

    public abstract void test2();

    /**
     * 为什么Java8要允许接口中定义静态方法？
     * 是因为JDK发展了一段时间后，发现类库中，多了很多这样的成组的API：
     * （1）Path接口和Paths工具类
     * （2）Collection接口和Collections工具类…
     * 一般工具类中都是静态方法，这些静态方法，基本上都是为前面这个对应接口服务的。 这样的话，就会出现很多API，使得程序员的学习成本增加了，使用成本也增加了。
     *
     * 于是把这样的静态方法，直接挪到接口中定义就好了。减少了这样的工具类的出现。
     */
    // Java8之后，接口又增加了两种新成员：（可以有方法体）
    public static void test1() {
        System.out.println("接口中的静态方法");
    }

    /**
     * 为什么么Java8要允许接口中定义默认方法？
     * 是因为有的时候，一个接口它的大多数实现类对接口的抽象方法的实现代码是一样，那么我们写好几次就太麻烦了。
     * 即相当于，默认方法是原来的抽象方法有了一个默认实现。如果实现类的实现和接口一样，就不需要重写，如果不一样就重写即可。
     */
    public default void test3() {
        System.out.println("接口中的默认方法");
    }
}
