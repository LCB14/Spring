package com.lcb.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author changbao.li Date: 2019-10-30 Time: 15:23
 * @version $
 */
@Configuration
@ComponentScan("com.lcb.aop")
public class AppConfig {

    @Bean
    public TestBean testBean(){
        return new TestBean();
    }

    @Bean
    public AspectJTest aspectJTest(){
        return new AspectJTest();
    }
}
