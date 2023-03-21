package com.frost2.pwdseparate.common.druid;

import com.alibaba.druid.util.DruidPasswordCallback;

import java.util.Properties;

public class DruidCustomPasswordCallback extends DruidPasswordCallback {

    private static final String TRUE = "true";

    @Override
    public void setProperties(Properties properties) {

        super.setProperties(properties);

        //获取配置文件中的已经加密的密码
        String decodeSwitch = (String) properties.get("decodeSwitch");
        String pwdKey = (String) properties.get("pwdKey");
        String localPwd = (String) properties.get("password");

        String pwdValue;
        if (TRUE.equals(decodeSwitch)) {
            pwdValue = getApolloPwd(pwdKey);
        } else {
            pwdValue = getLocalPwd(localPwd);
        }
        setPassword(pwdValue.toCharArray());
    }

    /**
     * 从Apollo配置中心读取pwdKey对应pwdValue
     */
    private String getApolloPwd(String pwdKey) {
        return "root";
    }

    /**
     * 解密本地密码localPwd
     */
    private String getLocalPwd(String localPwd) {
        return "root";
    }
}