package com.frost2.javaspi.batch;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author frost2
 * @date 2022-06-30 14:53
 */
public class FileImport {

    /**
     * 清空表table
     */
    public static void truncate(Connection conn, String table) throws SQLException {
        String sql = "truncate table " + table;
        CallableStatement callableStatement = conn.prepareCall(sql);
        callableStatement.execute();
        callableStatement.close();
    }

    /**
     * 获取文件夹下所有文件名
     */
    public static String[] getFolder(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("文件夹不存在:" + path);
        }
        if (!file.isDirectory()) {
            throw new RuntimeException("路径[" + path + "]不是文件夹");
        }
        String[] list = file.list();
        if (null == list || list.length == 0) {
            throw new RuntimeException("文件夹为空");
        }
        return list;
    }
}
