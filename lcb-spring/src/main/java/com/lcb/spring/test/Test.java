package com.lcb.spring.test;

import com.lcb.spring.appconfig.AppConfig;
import com.lcb.spring.bean.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author changbao.li
 * @Description spring 特性测试
 * @Date 2019-06-22 16:00
 */
public class Test {

     public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);
        Student student = annotationConfigApplicationContext.getBean(Student.class);

        System.out.println(student.toString());
    }
}
