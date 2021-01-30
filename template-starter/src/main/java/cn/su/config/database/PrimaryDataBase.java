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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Author: sr
 * @Date: Create In 0:15 2020/10/1 0001
 * @Description: 主数据源
 */
@Configuration
@MapperScan(basePackages = "cn.sylr.tools.mapper.primarysql", sqlSessionFactoryRef = "primary1SqlSessionFactory")
public class PrimaryDataBase {
    // 将这个对象放入Spring容器中
    @Bean(name = "primary1DataBase")
    // 表示这个数据源是默认数据源
    @Primary
    // 读取application.properties中的配置参数映射成为一个对象
    // prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.primarydb")
    public DataSource getDateSourcePrimary()
    {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "primary1SqlSessionFactory")
    // 表示这个数据源是默认数据源
    @Primary
    // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primary1DataBase") DataSource datasource)
            throws Exception
    {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/primarysql/*.xml"));
        return bean.getObject();
    }

    @Bean("primarySqlSessionTemplate")
    // 表示这个数据源是默认数据源
    @Primary
    public SqlSessionTemplate primarySqlSessionTemplate(
            @Qualifier("primary1SqlSessionFactory") SqlSessionFactory sessionFactory)
    {
        return new SqlSessionTemplate(sessionFactory);
    }
}
