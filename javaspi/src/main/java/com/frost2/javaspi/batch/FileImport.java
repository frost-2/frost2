package com.frost2.javaspi.batch;

import com.frost2.javaspi.common.XMLField;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * @param conn
     * @param binList
     * @param xmlList
     */
    public static void batchInsert(Connection conn, ArrayList<HashMap<String, String>> binList, List<HashMap<String, String>> xmlList) throws SQLException {
        String insertSql = cretePrepareSql(xmlList);
        PreparedStatement stmt = conn.prepareStatement(insertSql);

        int batchNum = Integer.parseInt(xmlList.get(0).get(XMLField.BATCH_NUM));

        //i从1开始，避免i为0时，i%batchNum=0，提前触发批量执行
        int size = binList.size() + 1;
        for (int i = 1; i < size; i++) {
            //循环xml文件,匹配字段和字段值
            for (int j = 1; j < xmlList.size(); j++) {
                HashMap<String, String> map = xmlList.get(j);
                String column = map.get(XMLField.COLUMN);
                stmt.setString(j, binList.get(i - 1).get(column).trim());
            }
            //分批处理
            stmt.addBatch();
            if (i % batchNum == 0) {
                stmt.execute();
                stmt.clearBatch();
            }
        }
        //处理遗漏数据:总数小于batchNum和最后一批
        stmt.executeBatch();
        stmt.clearBatch();
        stmt.close();
    }

    //生成预插入语句
    private static String cretePrepareSql(List<HashMap<String, String>> xmlList) {
        String tableName = xmlList.get(0).get(XMLField.TABLE_NAME);
        xmlList = xmlList.stream().skip(1).collect(Collectors.toList());
        StringBuilder head = new StringBuilder("insert into " + tableName + "(");
        StringBuilder tail = new StringBuilder(") values(");
        for (HashMap<String, String> map : xmlList) {
            String column = map.get(XMLField.COLUMN) + ",";
            head.append(column);
            tail.append("?,");
        }
        String strHead = head.substring(0, head.length() - 1);
        String strTail = tail.substring(0, tail.length() - 1);
        return strHead + strTail + ")";
    }
}
