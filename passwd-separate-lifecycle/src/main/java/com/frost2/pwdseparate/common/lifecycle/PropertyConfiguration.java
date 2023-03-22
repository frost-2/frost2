package com.frost2.pwdseparate.common.lifecycle;

import com.frost2.pwdseparate.common.apollo.ApolloUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * 优势:可提供最新配置信息供外部使用
 * 劣势:PropertyPlaceholderConfigurer将被废弃，不利于后期升级
 *
 * @author frost2
 * @date 2023-03-20 20:07
 */
public class PropertyConfiguration extends PropertyPlaceholderConfigurer {

    private static Properties properties = new Properties();
    private static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
    private static final String SPRING_DATASOURCE_PWDKEY = "spring.datasource.pwdKey";

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        String password = props.getProperty(SPRING_DATASOURCE_PASSWORD);
        String pwdKey = props.getProperty(SPRING_DATASOURCE_PWDKEY);
        //获取密码
        props.setProperty(SPRING_DATASOURCE_PASSWORD, ApolloUtils.obtainPassword(pwdKey, password));
        //保存最新的配置信息以供使用
        properties = props;
        super.processProperties(beanFactoryToProcess, props);
    }

    public static String getProperties(String key) {
        return properties.getProperty(key);
    }
}
