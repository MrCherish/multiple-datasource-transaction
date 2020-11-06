package com.xtt.transaction.configuration;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.io.IOException;
import java.util.Properties;

@Configuration
@MapperScan(value = "com.xtt.transaction.mapper.mt",sqlSessionFactoryRef = "mtSqlSessionFactory")
public class MtDataSourceConfiguration {

    @Bean("mtDataSourceProperties")
    public Properties rtDataSourceProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        Resource resource = new PathMatchingResourcePatternResolver().getResource("classpath:mtDataSource.properties");
        propertiesFactoryBean.setLocations(resource);
        propertiesFactoryBean.afterPropertiesSet();
        Properties properties = propertiesFactoryBean.getObject();
        return properties;
    }

    @Bean("mtDataSource")
    public DataSource rtDataSource(@Qualifier("mtDataSourceProperties") Properties properties) {

        DruidXADataSource druidXADataSource = new DruidXADataSource();
        druidXADataSource.configFromPropety(properties);

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(druidXADataSource);
        atomikosDataSourceBean.setUniqueResourceName("mtDataSource");
        atomikosDataSourceBean.setPoolSize(5);
        return atomikosDataSourceBean;
    }


    @Bean(name = "mtSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("mtDataSource") DataSource mtDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mtDataSource);
        return sessionFactory.getObject();
    }

}
