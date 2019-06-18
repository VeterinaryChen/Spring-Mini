package com.njau.spring;

import com.njau.spring.starter.MiniApplication;

/**
 * @author: jeffchen
 */
public class Application {

    public static void main (String[] args) {

        System.out.println("Hello World!");
        MiniApplication.run(Application.class,args);
    }
}
