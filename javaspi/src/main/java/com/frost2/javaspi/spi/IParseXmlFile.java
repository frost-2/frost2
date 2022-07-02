package com.frost2.javaspi.spi;

import java.util.HashMap;
import java.util.List;

/**
 * @author frost2
 * @date 2022-06-30 14:30
 */
public interface IParseXmlFile {
    List<HashMap<String, String>> parseXmlFile(String xmlFilePath) throws Exception;
}
