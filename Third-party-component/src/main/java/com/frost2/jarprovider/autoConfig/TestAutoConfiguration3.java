package com.frost2.jarprovider.autoConfig;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author frost2
 * @date 2023-03-29 15:03
 */
public class TestAutoConfiguration3 implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{TestAutoConfiguration3.class.getName()};
    }

    public static void method() {
        System.out.println("TestAutoConfiguration2");
    }

}
