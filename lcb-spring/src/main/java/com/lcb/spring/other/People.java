package com.lcb.spring.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author changbao.li
 * @since 08 九月 2019
 */
@Component
public class People {

    @Autowired
    House commonHouse;

    public void sOut(){
        commonHouse.sOut();
    }
}
