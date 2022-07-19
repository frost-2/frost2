package com.frost2.javaspi.spi.impl;

import com.frost2.javaspi.batch.FileImport;
import com.frost2.javaspi.common.StringUtils;
import com.frost2.javaspi.common.XMLField;
import com.frost2.javaspi.spi.IFieldValueHandler;
import com.frost2.javaspi.spi.IParseBinFile;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

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
            if (fileName.endsWith(".bin")) {
                saveBinFile(conn, xmlList, binPath);
            }
        }
    }

    private void saveBinFile(Connection conn, List<HashMap<String, String>> xmlList, String binPath) throws Exception {
        ArrayList<HashMap<String, String>> binList = new ArrayList<>(); //key:字段名,value:字段值
        //逐行解析bin文件，将数据保存到binList中
        String fileEncode = xmlList.get(0).get(XMLField.FILE_ENCODE);
        Files.lines(Paths.get(binPath), Charset.forName(fileEncode)).forEach(line -> {
            binList.add(fieldValueHandle(xmlList.stream().skip(1).collect(Collectors.toList()), line));
        });
        //数据入库
        if (!binList.isEmpty()) {
            FileImport.batchInsert(conn, binList, xmlList);
        }
    }

    //处理行数据,将数据保存到map
    private HashMap<String, String> fieldValueHandle(List<HashMap<String, String>> xmlList, String line) {
        HashMap<String, String> fieldMap = new HashMap<>();
        for (HashMap<String, String> binMap : xmlList) {
            String column = binMap.get(XMLField.COLUMN);

            int width = Integer.parseInt(binMap.get(XMLField.WIDTH));
            String field = StringUtils.substring(line, width);
            line = StringUtils.replaceFirst(line, field);

            String fieldHandler = binMap.get(XMLField.FIELD_HANDLER);
            if ("Y".equalsIgnoreCase(fieldHandler)) {
                field = fieldValueHandler(field);
            }
            fieldMap.put(column, field);
        }
        return fieldMap;
    }

    //多个实现类，按照行顺序依次处理filed
    private String fieldValueHandler(String field) {
        ServiceLoader<IFieldValueHandler> serviceLoader = ServiceLoader.load(IFieldValueHandler.class);
        for (IFieldValueHandler fieldValueHandler : serviceLoader) {
            field = fieldValueHandler.transfer(field);
        }
        return field;
    }
}
