package com.frost2.myspi.entity;

/**
 * @author frost2
 * @date 2022-07-03 0:40
 */
public class ImplInfo {
    private String alias;
    private String implPath;
    private int order;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getImplPath() {
        return implPath;
    }

    public void setImplPath(String implPath) {
        this.implPath = implPath;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
