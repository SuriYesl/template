package cn.su.config.database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Author: sr
 * @Date: Create In 0:20 2020/10/1 0001
 * @Description: 次数据源
 */
@Configuration
@MapperScan(basePackages = "cn.sylr.tools.mapper.secondsql", sqlSessionFactoryRef = "second1SqlSessionFactory")
public class SecondDataBase {
    @Bean(name = "second1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource getDateSourceSecond()
    {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "second1SqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("second1DataSource") DataSource datasource)
            throws Exception
    {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/secondsql/*.xml"));
        return bean.getObject();
    }

    @Bean("secondSqlSessionTemplate")
    public SqlSessionTemplate secondSqlSessionTemplate(
            @Qualifier("second1SqlSessionFactory") SqlSessionFactory sessionFactory)
    {
        return new SqlSessionTemplate(sessionFactory);
    }
}
