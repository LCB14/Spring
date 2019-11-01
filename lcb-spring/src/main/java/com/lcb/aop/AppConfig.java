package com.lcb.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author changbao.li Date: 2019-10-30 Time: 15:23
 * @version $
 */
@Configuration
@ComponentScan("com.lcb.aop")
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public TestBean testBean(){
        return new TestBean();
    }
}
