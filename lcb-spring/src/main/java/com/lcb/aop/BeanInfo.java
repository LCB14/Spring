package com.lcb.aop;

/**
 * @author changbao.li Date: 2019-10-30 Time: 15:12
 * @version $
 */
public class BeanInfo {

    private String testStr = "testStr";

    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }

    public void test(){
        System.out.println("test");
    }
}
