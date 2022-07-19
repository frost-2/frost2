package com.frost2.javaspi.batch;

import com.frost2.javaspi.common.XMLField;
import com.frost2.javaspi.spi.IParseBinFile;
import com.frost2.javaspi.spi.IParseXmlFile;
import com.frost2.javaspi.spi.impl.ParseBinFileImpl;
import com.frost2.javaspi.spi.impl.ParseXmlFileImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author frost2
 * @date 2022-06-30 11:19
 */
public class ParseBinFile {

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://8.135.25.37:3306/frost2?useUnicode=true&useSSL=false&characterEncoding=UTF-8", "root", "WeiPing@1004");
        String xmlFilePath = "E:\\Frost2-Files\\IdeaWorkSpace\\temp\\javaSpi\\test.xml";
        //该文件夹只保存bin文件
        String binFileDir = "E:\\Frost2-Files\\IdeaWorkSpace\\temp\\javaSpi\\bin";
        ParseBinFile parseBinFile = new ParseBinFile();
        parseBinFile.impData(xmlFilePath, binFileDir, conn);
    }

    /**
     * 数据导入对外入口
     *
     * @param xmlFilePath xml文件绝对路径
     * @param binFileDir  bin文件所在文件夹
     * @param conn        JDBC连接,这里可以不用关闭，因为调用处获取连接时一定会关闭。
     * @return 是否导入成功
     */
    public void impData(String xmlFilePath, String binFileDir, Connection conn) throws Exception {

        //解析XML文件获取规则
        List<HashMap<String, String>> xmlList = parseXmlFile(xmlFilePath);

        //导入策略:先清空后添加
        HashMap<String, String> summary = xmlList.get(0);
        FileImport.truncate(conn, summary.get(XMLField.TABLE_NAME));

        //处理BIN文件
        parseBinFile(conn, xmlList, binFileDir);
    }

    /**
     * JAVA SPI实现解析XML文件,如果不存在扩展则使用默认的方法解析XML文件。
     * 解析后的bin文件摘要信息保存在list[0],bin文件字段信息保存在后续位置。
     *
     * @param xmlFilePath XML文件绝对路径
     * @return bin文件规则信息
     */
    private List<HashMap<String, String>> parseXmlFile(String xmlFilePath) throws Exception {
        ServiceLoader<IParseXmlFile> serviceLoader = ServiceLoader.load(IParseXmlFile.class);
        Iterator<IParseXmlFile> iterator = serviceLoader.iterator();
        IParseXmlFile parseXmlFile;
        //多个实现类取第一个
        if (iterator.hasNext()) {
            parseXmlFile = iterator.next();
        } else {
            parseXmlFile = new ParseXmlFileImpl();
        }
        return parseXmlFile.parseXmlFile(xmlFilePath);
    }

    /**
     * 根据bin文件规则信息解析BIN文件，将数据导入到指定的表
     *
     * @param conn       JDBC连接
     * @param xmlList    BIN文件规则信息
     * @param binFileDir bin文件所在文件夹
     */
    private void parseBinFile(Connection conn, List<HashMap<String, String>> xmlList, String binFileDir) throws Exception {
        ServiceLoader<IParseBinFile> serviceLoader = ServiceLoader.load(IParseBinFile.class);
        Iterator<IParseBinFile> iterator = serviceLoader.iterator();
        IParseBinFile parseBinFile;
        //多个实现类取第一个
        if (iterator.hasNext()) {
            parseBinFile = iterator.next();
        } else {
            parseBinFile = new ParseBinFileImpl();
        }
        parseBinFile.parseBinFile(conn, xmlList, binFileDir);
    }

}
