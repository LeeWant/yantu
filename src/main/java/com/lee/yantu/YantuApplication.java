package com.lee.yantu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@Configuration
@EnableScheduling
public class YantuApplication extends SpringBootServletInitializer{//

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(YantuApplication.class);
    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(YantuApplication.class, args);
//    }

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("102400KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("1002400KB");
        return factory.createMultipartConfig();
    }
}
