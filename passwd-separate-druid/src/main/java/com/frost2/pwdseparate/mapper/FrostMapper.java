package com.frost2.pwdseparate.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author frost2
 * @date 2023-03-21 10:56
 */
@Mapper
public interface FrostMapper {

    @Select("select name from frost2 limit 1")
    public String query();
}
