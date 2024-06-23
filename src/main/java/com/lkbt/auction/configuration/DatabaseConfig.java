package com.lkbt.auction.configuration;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 1. Created by   : ycsong
 * 2. Created Date : 2023-05-08
 * 3. Description  : Database 설정 클래스
 * 4. History
 *    > 2023-05-08 : 최초 생성
 */
@Configuration
@MapperScan(basePackages = { "com.lkbt.auction.mapper" })
public class DatabaseConfig {

  /**
   * HikariCP 설정
   * @return
   */
  @Bean
  @Primary
  @Profile("local")
  @ConfigurationProperties(prefix = "spring.datasource.hikari")
  HikariConfig hikariConfig() {
    return new HikariConfig();
	}

  /**
   * Local 에서 사용할 DataSource
   * 개발, 운영환경에서는 jndi 를 사용하여 설정할 수 있어 구분 함
   */
  @Bean(name = "dataSource")
  @Primary
  @Profile("local")
  DataSource dataSourceLocal() {
    return new HikariDataSource(hikariConfig());
  }

  @Bean(name = "dataSource")
  @Profile({ "develop", "production", "intergration" })
  DataSource dataSource() throws NamingException {
    JndiDataSourceLookup lookup = new JndiDataSourceLookup();
    lookup.setResourceRef(true);
    DataSource ds = lookup.getDataSource("jdbc/nhjmk");
    return ds;
  }

  // @Bean
  // public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
  //   SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
  //   PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
  //   sessionFactory.setDataSource(dataSource);
  //   sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
  //   sessionFactory.setTypeAliasesPackage("com.lkbt.auction.model.dto");
  //   sessionFactory.setVfs(SpringBootVFS.class);
  //   return sessionFactory.getObject();
  // }

  /**
   * transaction manager
   */
  @Bean
  PlatformTransactionManager txManager(DataSource dataSource) {
      DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
      dataSourceTransactionManager.setNestedTransactionAllowed(true); // nested
      return dataSourceTransactionManager;
  }

}
