package com.lcb.aop;

import com.lcb.spring.appconfig.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author changbao.li Date: 2019-10-30 Time: 15:26
 * @version $
 */
public class AopTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);
        TestBean testBean = annotationConfigApplicationContext.getBean(TestBean.class);
        testBean.test();
    }
}
