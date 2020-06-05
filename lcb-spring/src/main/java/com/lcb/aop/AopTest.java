package com.lcb.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author changbao.li Date: 2019-10-30 Time: 15:26
 * @version $
 */
public class AopTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(BeanConfig.class);
        BeanInfo testBean = annotationConfigApplicationContext.getBean(BeanInfo.class);
        testBean.test();
    }
}
