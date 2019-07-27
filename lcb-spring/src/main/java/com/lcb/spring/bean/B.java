package com.lcb.spring.bean;

import org.springframework.stereotype.Component;

/**
 * @author changbao.li
 * @since 27 七月 2019
 */
@Component
public class B {
    public A a;

    public B(A a){
        this.a = a;
    }
}
