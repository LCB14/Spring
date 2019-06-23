package com.lcb.spring.bean;

import org.springframework.stereotype.Component;

/**
 * @author changbao.li
 * @Description school bean
 * @Date 2019-06-22 16:02
 */
@Component
public class School {

    private String name;

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
