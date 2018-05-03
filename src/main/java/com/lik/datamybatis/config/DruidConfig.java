package com.lik.datamybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lik
 */
@Configuration
public class DruidConfig {

//    加载配置文件
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }

//    配置监控
//    1.配置一个管理后台的servlet
//    2.配置一个filter

    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("loginUsername","admin");
        stringStringHashMap.put("loginPassword","admin");
        stringStringHashMap.put("allow","localhost");
        servletRegistrationBean.setInitParameters(stringStringHashMap);

        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean webStatFilter(){
        Map<String, String> stringStringHashMap = new HashMap<>();

        stringStringHashMap.put("exclusions","*.js,*.css,/druid/*");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        filterRegistrationBean.setInitParameters(stringStringHashMap);
        return filterRegistrationBean;
    }

}
