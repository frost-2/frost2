package com.frost2.pwdseparate.common.druid;

import com.alibaba.druid.util.DruidPasswordCallback;
import com.frost2.pwdseparate.common.apollo.ApolloUtils;

import java.util.Properties;

public class DruidCustomPasswordCallback extends DruidPasswordCallback {

    @Override
    public void setProperties(Properties properties) {

        super.setProperties(properties);

        //获取配置文件中的已经加密的密码
        String pwdKey = (String) properties.get("pwdKey");
        String localPwd = (String) properties.get("password");
        //获取密码
        String password = ApolloUtils.ObtainPassword(pwdKey, localPwd);

        setPassword(password.toCharArray());
    }

    @Override
    public void setPassword(char[] password) {
        super.setPassword(password);
    }

}