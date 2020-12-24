package cn.krain;

import com.cxytiandi.encrypt.springboot.annotation.EnableEncrypt;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEncrypt
@SpringBootApplication
@MapperScan("cn.krain.dao")
public class MbmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MbmsApplication.class, args);
    }

}
/*
*   改数据库信息
*   添加删除电影前删除其他信息的操作
*
* */