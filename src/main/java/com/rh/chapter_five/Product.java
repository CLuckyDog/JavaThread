package com.rh.chapter_five;

/**
 * @author ：pan_zhongjian
 * @version :
 * @date ：Created in 2019/11/6 15:09
 * @description: 不变模式demo
 * @modified By:
 */
public final class Product { /*确保无法被继承，即不存在子类*/
    /*下面三个字段，私有属性，不会被其他对象获取，final保证属性不会被2次赋值*/
    private final String no;
    private final String name;
    private final double price;

    public Product(String no, String name, double price) {/*在创建对象时，必须制定数据*/
        super();    /*因为创建之后，无法进行修改*/
        this.no = no;
        this.name = name;
        this.price = price;
    }

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
