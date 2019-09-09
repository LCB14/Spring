/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.annotation;

import java.util.Arrays;
import java.util.function.Supplier;

import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Standalone application context, accepting annotated classes as input - in particular
 * {@link Configuration @Configuration}-annotated classes, but also plain
 * {@link org.springframework.stereotype.Component @Component} types and JSR-330 compliant
 * classes using {@code javax.inject} annotations. Allows for registering classes one by
 * one using {@link #register(Class...)} as well as for classpath scanning using
 * {@link #scan(String...)}.
 *
 * <p>In case of multiple {@code @Configuration} classes, @{@link Bean} methods defined in
 * later classes will override those defined in earlier classes. This can be leveraged to
 * deliberately override certain bean definitions via an extra {@code @Configuration}
 * class.
 *
 * <p>See @{@link Configuration}'s javadoc for usage examples.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @see #register
 * @see #scan
 * @see AnnotatedBeanDefinitionReader
 * @see ClassPathBeanDefinitionScanner
 * @see org.springframework.context.support.GenericXmlApplicationContext
 * @since 3.0
 */
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {

    /**
     * 注解bean定义读取器，主要作用是用来读取被注解的了bean
     */
    private final AnnotatedBeanDefinitionReader reader;

    /**
     * 扫描器，它仅仅是在我们外部手动调用 scan 等方法时才有用，常规方式是不会用到scanner对象的
     */
    private final ClassPathBeanDefinitionScanner scanner;


    /**
     * Create a new AnnotationConfigApplicationContext that needs to be populated
     * through {@link #register} calls and then manually {@linkplain #refresh refreshed}.
     * <p>
     * 此处会隐式调用父类GenericApplicationContext的构造方法初始化一个beanFactory
     */
    public AnnotationConfigApplicationContext() {
        // 创建BeanDefinition读取器，用于把普通bean转换成BeanDefinition对象
        this.reader = new AnnotatedBeanDefinitionReader(this);

        // 此处的scanner用处不是很大，它仅仅是在用户外部手动调用 .scan 等方法时才有用，常规方式是不会用到此处的scanner对象的
        this.scanner = new ClassPathBeanDefinitionScanner(this);
    }

    /**
     * Create a new AnnotationConfigApplicationContext with the given DefaultListableBeanFactory.
     *
     * @param beanFactory the DefaultListableBeanFactory instance to use for this context
     */
    public AnnotationConfigApplicationContext(DefaultListableBeanFactory beanFactory) {
        super(beanFactory);
        this.reader = new AnnotatedBeanDefinitionReader(this);
        this.scanner = new ClassPathBeanDefinitionScanner(this);
    }

    /**
     * Create a new AnnotationConfigApplicationContext, deriving bean definitions
     * from the given annotated classes and automatically refreshing the context.
     *
     * @param annotatedClasses one or more annotated classes,
     *                         e.g. {@link Configuration @Configuration} classes
     */
    public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
        /**
         * 实例化reader和scanner以及beanFactory
         *
         * 首先调用父类GenericApplicationContext的构造函数，
         * 在父类的构造函数里面就是初始化DefaultListableBeanFactory，并且赋值给beanFactory。
         *
         *  本类的构造函数里面初始化了
         *  一个读取器：AnnotatedBeanDefinitionReader reader。
         *  一个扫描器:ClassPathBeanDefinitionScanner scanner。
         *
         *  this()方法最终触发逻辑是将spring内置的一些后置处理器注册到bean容器中！
         */
        this();

        /**
         * 将配置bean添加到容器
         *
         * 把传入的配置bean进行注册，这里有两种情况：（注意：自定义的bean需要等到refresh()方法执行完毕后才能完成注册)
         * 1、传入传统的配置类 -- 添加了@Configuration注解
         * 2、传入常规的bean（虽然一般没有人会这么做） -- 未添加@Configuration注解
         *
         * 看到后面就会知道spring把传统的带上@Configuration注解的配置类称之为FULL配置类，不带@Configuration注解的称之为Lite配置类。
         */
        register(annotatedClasses);

        /**
         * 刷新容器
         *
         * 注册用户自定义的bean到容器中
         */
        refresh();
    }

    /**
     * Create a new AnnotationConfigApplicationContext, scanning for bean definitions
     * in the given packages and automatically refreshing the context.
     *
     * @param basePackages the packages to check for annotated classes
     */
    public AnnotationConfigApplicationContext(String... basePackages) {
        this();
        scan(basePackages);
        refresh();
    }


    /**
     * Propagates the given custom {@code Environment} to the underlying
     * {@link AnnotatedBeanDefinitionReader} and {@link ClassPathBeanDefinitionScanner}.
     */
    @Override
    public void setEnvironment(ConfigurableEnvironment environment) {
        super.setEnvironment(environment);
        this.reader.setEnvironment(environment);
        this.scanner.setEnvironment(environment);
    }

    /**
     * Provide a custom {@link BeanNameGenerator} for use with {@link AnnotatedBeanDefinitionReader}
     * and/or {@link ClassPathBeanDefinitionScanner}, if any.
     * <p>Default is {@link org.springframework.context.annotation.AnnotationBeanNameGenerator}.
     * <p>Any call to this method must occur prior to calls to {@link #register(Class...)}
     * and/or {@link #scan(String...)}.
     *
     * @see AnnotatedBeanDefinitionReader#setBeanNameGenerator
     * @see ClassPathBeanDefinitionScanner#setBeanNameGenerator
     */
    public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
        this.reader.setBeanNameGenerator(beanNameGenerator);
        this.scanner.setBeanNameGenerator(beanNameGenerator);
        getBeanFactory().registerSingleton(
                AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR, beanNameGenerator);
    }

    /**
     * Set the {@link ScopeMetadataResolver} to use for detected bean classes.
     * <p>The default is an {@link AnnotationScopeMetadataResolver}.
     * <p>Any call to this method must occur prior to calls to {@link #register(Class...)}
     * and/or {@link #scan(String...)}.
     */
    public void setScopeMetadataResolver(ScopeMetadataResolver scopeMetadataResolver) {
        this.reader.setScopeMetadataResolver(scopeMetadataResolver);
        this.scanner.setScopeMetadataResolver(scopeMetadataResolver);
    }


    //---------------------------------------------------------------------
    // Implementation of AnnotationConfigRegistry
    //---------------------------------------------------------------------

    /**
     * Register one or more annotated classes to be processed.
     * <p>Note that {@link #refresh()} must be called in order for the context
     * to fully process the new classes.
     *
     * @param annotatedClasses one or more annotated classes,
     *                         e.g. {@link Configuration @Configuration} classes
     * @see #scan(String...)
     * @see #refresh()
     */
    @Override
    public void register(Class<?>... annotatedClasses) {
        Assert.notEmpty(annotatedClasses, "At least one annotated class must be specified");
        this.reader.register(annotatedClasses);
    }

    /**
     * Perform a scan within the specified base packages.
     * <p>Note that {@link #refresh()} must be called in order for the context
     * to fully process the new classes.
     *
     * @param basePackages the packages to check for annotated classes
     * @see #register(Class...)
     * @see #refresh()
     */
    @Override
    public void scan(String... basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        this.scanner.scan(basePackages);
    }


    //---------------------------------------------------------------------
    // Adapt superclass registerBean calls to AnnotatedBeanDefinitionReader
    //---------------------------------------------------------------------

    @Override
    public <T> void registerBean(@Nullable String beanName, Class<T> beanClass,
                                 @Nullable Supplier<T> supplier, BeanDefinitionCustomizer... customizers) {

        this.reader.registerBean(beanClass, beanName, supplier, customizers);
    }

}
