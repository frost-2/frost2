package com.frost2.pwdseparate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@ImportResource(locations = {"classpath:spring/*.xml"})
@SpringBootApplication
public class PwdSeparateApplication implements ApplicationRunner {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(PwdSeparateApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        String sql = "select * from frost2";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        list.forEach(System.out::println);
    }
}
