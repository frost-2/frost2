package com.frost2.pwdseparate.common.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DruidConfig {

    @Value("${spring.datasource.type}")
    private String dbType;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.druid.connection-properties}")
    private String connectionProperties;
    @Value("${spring.datasource.druid.password-callback}")
    private String passwordCallbackClassName;

    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() throws Exception {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setDbType(dbType);
        datasource.setDriverClassName(driverClassName);
        datasource.setUrl(url);
        datasource.setUsername(username);
        //从Apollo读取配置信息并替换
        datasource.setPassword(password);
        datasource.setConnectionProperties(connectionProperties);
        datasource.setPasswordCallbackClassName(passwordCallbackClassName);
        return datasource;
    }
}