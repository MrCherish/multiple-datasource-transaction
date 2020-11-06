package com.fn.transaction.configuration;

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
@MapperScan(value = "com.fn.transaction.mapper.rt",sqlSessionFactoryRef = "rtSqlSessionFactory")
public class RtDataSourceConfiguration {

    @Bean("rtDataSourceProperties")
    public Properties rtDataSourceProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        Resource resource = new PathMatchingResourcePatternResolver().getResource("classpath:rtDataSource.properties");
        propertiesFactoryBean.setLocations(resource);
        propertiesFactoryBean.afterPropertiesSet();
        Properties properties = propertiesFactoryBean.getObject();
        return properties;
    }

    @Bean("rtDataSource")
    @Primary
    public DataSource rtDataSource(@Qualifier("rtDataSourceProperties") Properties properties) {

        DruidXADataSource druidXADataSource = new DruidXADataSource();
        druidXADataSource.configFromPropety(properties);

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(druidXADataSource);
        atomikosDataSourceBean.setUniqueResourceName("rtDataSource");
        atomikosDataSourceBean.setPoolSize(5);
        return atomikosDataSourceBean;
    }

    /**
     * 使用这个来做总事务 后面的数据源就不用设置事务了
     *
     */
    @Bean(name = "transactionManager")
    @Primary
    public JtaTransactionManager regTransactionManager () {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);

    }

    @Bean(name = "rtSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("rtDataSource") DataSource rtDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(rtDataSource);
        return sessionFactory.getObject();
    }

}
