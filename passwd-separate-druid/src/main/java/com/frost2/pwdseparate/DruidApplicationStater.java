package com.frost2.pwdseparate;

import com.frost2.pwdseparate.mapper.FrostMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@SpringBootApplication
@MapperScan(basePackages = "com.frost2.pwdseparate.mapper")
public class DruidApplicationStater implements ApplicationRunner {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FrostMapper frostMapper;

    public static void main(String[] args) {
        SpringApplication.run(DruidApplicationStater.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        String sql = "select * from frost2";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        list.forEach(System.out::println);

        String name = frostMapper.query();
        System.out.println(name);

    }
}
