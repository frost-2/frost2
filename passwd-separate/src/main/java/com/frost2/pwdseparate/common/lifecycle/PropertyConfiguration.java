package com.frost2.pwdseparate.common.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * @author frost2
 * @date 2023-03-20 20:07
 */
public class PropertyConfiguration extends PropertyPlaceholderConfigurer {
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        String property = props.getProperty("spring.datasource.password");
        props.setProperty("spring.datasource.password","root");
        super.processProperties(beanFactoryToProcess, props);
    }
}
