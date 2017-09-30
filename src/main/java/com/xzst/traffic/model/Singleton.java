package com.xzst.traffic.model;

import org.rosuda.JRI.Rengine;

/**
 * Created by 张超 on 2017/9/30.
 */
public class Singleton {
    private static final Singleton singleton = new Singleton();
    private  static final Rengine re = new Rengine(new String[] { "--vanilla" }, false, null);
    // 限制产生多个对象
    private Singleton() {
    }

    // 通过该方法获得实例对象
    public static Rengine getSingleton() {
        return re;
    }

    // 类中其他方法尽量是static
    public static void doSomething() {

    }
}
