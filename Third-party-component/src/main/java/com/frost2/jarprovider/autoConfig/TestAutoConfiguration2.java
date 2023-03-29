package com.frost2.jarprovider.autoConfig;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author frost2
 * @date 2023-03-29 15:03
 */
public class TestAutoConfiguration2 implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //创建多个db,即可添加多个bean
        RootBeanDefinition bd = new RootBeanDefinition();
        bd.setBeanClass(TestAutoConfiguration2.class);
        registry.registerBeanDefinition("testAutoConfiguration2", bd);

        RootBeanDefinition bd1 = new RootBeanDefinition();
        bd1.setBeanClass(TestAutoConfiguration1.class);
        registry.registerBeanDefinition("testAutoConfiguration1", bd1);
    }

    public static void method() {
        System.out.println("TestAutoConfiguration2");
    }
}
