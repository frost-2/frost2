package com.frost2.pwdseparate.common.hikari;

import com.frost2.pwdseparate.common.apollo.ApolloUtils;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;

public class CustomizedHikariDataSource extends HikariDataSource {

    @Value("${spring.datasource.pwdKey}")
    private String pwdKey;

    @Override
    public String getPassword() {
        String localPwd = super.getPassword();
        //获取密码
        return ApolloUtils.ObtainPassword(pwdKey, localPwd);
    }
}
