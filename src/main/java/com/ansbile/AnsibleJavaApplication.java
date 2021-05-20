package com.ansbile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.ansbile.dao.reposity")
@EntityScan("com.ansbile.dao.entity")
public class AnsibleJavaApplication {


    public static void main(String[] args) {
        SpringApplication.run(AnsibleJavaApplication.class, args);

    }

}
