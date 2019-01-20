package com.feifan.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 函数入口启动类
 * 
 * @author Donald
 * @date 2019/01/18
 * @see
 */
@SpringBootApplication(scanBasePackages = {"com.feifan"})
@MapperScan(basePackages = {"com.feifan.dao"}) // mapper需单独开启扫描
@EnableWebMvc // 开启MVC配置
@EnableTransactionManagement // 开启事务
@EnableCaching // 开启缓存
public class MainRun {

    /**
     * @Cacheable 应用缓存区，对方法返回结果进行缓存 ----用于查询方法
     * @CacheEvict清除缓存区数据 ---用于增加、修改、删除方法
     * @CachePut 在不影响方法执行的情况下更新缓存
     * @Cacheing 将多个缓存操作重新组合到一个方法中
     * @CacheConfig 在类级别上共享一些与缓存相关的设置
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MainRun.class, args);
    }
}
