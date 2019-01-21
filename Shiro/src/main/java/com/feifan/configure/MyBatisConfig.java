package com.feifan.configure;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.feifan.interceptor.MysqlInterceptor;

/**
 * Mybatis 配置类 不通过springboot 使用自己配置的数据源
 * 
 * @author Donald
 * @date 2019/01/19
 * @see
 */
@Configuration
// @ConfigurationProperties // 读区主配置文件application.properties
@PropertySource(value = {"MyBatis.properties"})
public class MyBatisConfig {

    // 配置数据源
    @Bean(value = "dataSource", destroyMethod = "close", initMethod = "init")
    public DataSource getDataSource(@Value("${spring.datasource.driver-class-name}") String driver,
        @Value("${spring.datasource.url}") String url, @Value("${spring.datasource.username}") String username,
        @Value("${spring.datasource.password}") String password) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(2);
        dataSource.setMaxActive(100);
        dataSource.setMaxWait(30000);
        dataSource.setPoolPreparedStatements(false);
        dataSource.setDefaultAutoCommit(false);
        return dataSource;
    }

    // sqlsessionFactory
    @Bean("sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        // 读取mapper 配置文件
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
        factoryBean.setMapperLocations(resources);
        factoryBean.setTypeAliasesPackage("com.feifan.to");
        // 配置驼峰命名规则
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        factoryBean.setConfiguration(configuration);
        // 加入SQL 语句执行拦截器
        factoryBean.setPlugins(new Interceptor[] {new MysqlInterceptor()});
        return factoryBean.getObject();
    }

    // 配置事务管理器
    @Bean
    public DataSourceTransactionManager getDatasourceTrans(@Qualifier("dataSource") DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    public SqlSessionTemplate geSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
        return sessionTemplate;
    }

    /**
     * 以编程方式配置嵌入式servlet容器，可以通过注册实现该 WebServerFactoryCustomizer 接口的Spring bean
     * TomcatServletWebServerFactory，JettyServletWebServerFactory并且UndertowServletWebServerFactory 是专用变体，
     * ConfigurableServletWebServerFactory分别为Tomcat，Jetty和Undertow提供了额外的自定义setter方法。
     * 
     * @return
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                // 对嵌入式servlet容器的配置
                // factory.setPort(8081);
                /* 注意：new ErrorPage(stat, path);中path必须是页面名称，并且必须“/”开始。
                    底层调用了String.java中如下方法：
                    public boolean startsWith(String prefix) {
                        return startsWith(prefix, 0);
                    }*/
                ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/service/error");
                ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/service/error-400");
                ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/service/error");
                factory.addErrorPages(errorPage400, errorPage404, errorPage500);
            }
        };

    }

}
