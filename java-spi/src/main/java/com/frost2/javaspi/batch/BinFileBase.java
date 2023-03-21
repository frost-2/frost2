package com.frost2.javaspi.batch;

import com.frost2.javaspi.common.XMLField;

import java.util.HashMap;

/**
 * @author frost2
 * @date 2022-07-21 15:15
 */
public class BinFileBase {

    //bin文件基本信息
    protected String FILE_SUMMARY;
    protected String FILE_NAME;
    protected String TABLE_NAME;
    protected String BATCH_NUM;
    protected String FILE_ENCODE;
    protected String PARSING_XML;

    public BinFileBase() {
    }

    public BinFileBase(HashMap<String, String> summary) {
        this.FILE_SUMMARY = summary.get(XMLField.FILE_SUMMARY);
        this.FILE_NAME = summary.get(XMLField.FILE_NAME);
        this.TABLE_NAME = summary.get(XMLField.TABLE_NAME);
        this.BATCH_NUM = summary.get(XMLField.BATCH_NUM);
        this.FILE_ENCODE = summary.get(XMLField.FILE_ENCODE);
        this.PARSING_XML = summary.get(XMLField.PARSING_XML);
    }
}
