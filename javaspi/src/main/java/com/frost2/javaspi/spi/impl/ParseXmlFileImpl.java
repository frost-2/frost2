package com.frost2.javaspi.spi.impl;

import com.frost2.javaspi.common.XMLField;
import com.frost2.javaspi.spi.IParseXmlFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 默认实现XML文件解析,解析后数组首位保存BIN文件摘要信息，
 * 后续位置依次保存字段信息与BIN文件每行数据中的字段顺序一一对应
 *
 * @author frost2
 * @date 2022-06-30 14:32
 */
public class ParseXmlFileImpl implements IParseXmlFile {

    @Override
    public List<HashMap<String, String>> parseXmlFile(String xmlFilePath) throws Exception {
        List<HashMap<String, String>> list = new ArrayList<>();

        File file = new File(xmlFilePath);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        //bin文件摘要信息
        NodeList summaryNode = document.getElementsByTagName(XMLField.FILE_SUMMARY);
        Element element = (Element) summaryNode.item(0);
        HashMap<String, String> summaryMap = new HashMap<>();
        summaryMap.put(XMLField.FILE_NAME, element.getAttribute(XMLField.FILE_NAME));
        summaryMap.put(XMLField.TABLE_NAME, element.getAttribute(XMLField.TABLE_NAME));
        summaryMap.put(XMLField.BATCH_NUM, element.getAttribute(XMLField.BATCH_NUM));
        summaryMap.put(XMLField.FILE_ENCODE, element.getAttribute(XMLField.FILE_ENCODE));
        list.add(summaryMap);

        //bin文件字段信息
        NodeList fieldNode = document.getElementsByTagName(XMLField.FIELD);
        for (int i = 0; i < fieldNode.getLength(); i++) {
            HashMap<String, String> fieldMap = new HashMap<>();
            Element fieldElement = (Element) fieldNode.item(i);
            fieldMap.put(XMLField.COLUMN, fieldElement.getAttribute(XMLField.COLUMN));
            fieldMap.put(XMLField.WIDTH, fieldElement.getAttribute(XMLField.WIDTH));
            fieldMap.put(XMLField.DESC, fieldElement.getAttribute(XMLField.DESC));
            fieldMap.put(XMLField.FIELD_HANDLER, fieldElement.getAttribute(XMLField.FIELD_HANDLER));
            list.add(fieldMap);
        }
        return list;
    }
}
