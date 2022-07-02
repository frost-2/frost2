package com.frost2.javaspi.spi.impl;

import com.frost2.javaspi.batch.FileImport;
import com.frost2.javaspi.spi.IParseBinFile;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author frost2
 * @date 2022-06-30 15:42
 */
public class ParseBinFileImpl implements IParseBinFile {

    @Override
    public void parseBinFile(Connection conn, List<HashMap<String, String>> xmlList, String binFileDir) throws Exception {
        String[] folders = FileImport.getFolder(binFileDir);
        for (String fileName : folders) {
            String binPath = binFileDir + File.separator + fileName;
            //TODO:线程池并发
            saveBinFile(conn, xmlList, binPath);
        }
    }

    private void saveBinFile(Connection conn, List<HashMap<String, String>> xmlList, String binPath) throws Exception {
        ArrayList<HashMap<String, String>> binList = new ArrayList<>(); //key:字段名,value:字段值
        //逐行解析bin文件，将数据保存到binList中
        String fileEncode = xmlList.get(0).get("FILE_ENCODE");
        Files.lines(Paths.get(binPath), Charset.forName(fileEncode)).forEach(line -> {
            binList.add(fieldValueHandle(xmlList,line));
        });
        //数据入库
    }

    private HashMap<String, String> fieldValueHandle(List<HashMap<String, String>> xmlList, String line) {
        HashMap<String, String> fieldMap = new HashMap<>();
        xmlList.forEach(binMap -> {
            String column = binMap.get("COLUMN");
            int width = Integer.parseInt(binMap.get("WIDTH"));
            String filter = binMap.get("FILTER");
            String fieldHandler = binMap.get("FIELDHANDLER");

        });
        return fieldMap;
    }
}
