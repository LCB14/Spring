package com.lcb.spring.other;

import org.springframework.stereotype.Component;

/**
 * @author changbao.li
 * @since 08 九月 2019
 */
@Component
public class CommonHouse implements House{
    @Override
    public void sOut() {
        System.out.println("common house");
    }
}
