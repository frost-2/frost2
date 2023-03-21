package com.frost2.javaspi.spi;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

/**
 * @author frost2
 * @date 2022-06-30 15:38
 */
public interface IParseBinFile {
    void parseBinFile(Connection conn, List<HashMap<String, String>> xmlList, String binFileDir) throws Exception;
}
