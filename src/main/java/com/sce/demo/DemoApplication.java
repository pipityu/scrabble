package com.sce.demo;

import com.sce.demo.model.Game;
import com.sce.demo.model.Token;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.*;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);


        String beanArray[] = ctx.getBeanDefinitionNames();
        Arrays.sort(beanArray);
/*        for(String name : beanArray){
            System.out.println(name);
        }
*/


    }
}
