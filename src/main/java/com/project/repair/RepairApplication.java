package com.project.repair;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.project.repair.Mapper")
public class RepairApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepairApplication.class, args);
    }

}


/*11111*/