package com.frost2.jarprovider.autoConfig;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author frost2
 * @date 2023-03-29 15:30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(TestAutoConfiguration1.class)
//@Import(TestAutoConfiguration2.class)
//@Import(TestAutoConfiguration3.class)
public @interface EnableTestAutoConfig {
}
