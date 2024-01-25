package com.ict.fitme.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(value = {"com.ict.fitme.account.dao", "com.ict.fitme.board.dao"},sqlSessionFactoryRef ="sqlSessionFactory" )
public class MybatisConfig {
	
	private final ApplicationContext applicationContext;
	public MybatisConfig(ApplicationContext applicationContext) {
		this.applicationContext=applicationContext;
	}
	
	@Bean
	SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
		SqlSessionFactory factory=null;
		try {
			SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
			factoryBean.setDataSource(dataSource);
			factoryBean.setTypeAliasesPackage("com.ict.fitme");
			factoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/**/*.xml"));
			org.apache.ibatis.session.Configuration configuration =
			          new org.apache.ibatis.session.Configuration();
			      configuration.setMapUnderscoreToCamelCase(true);
			      factoryBean.setConfiguration(configuration);
			factory=factoryBean.getObject();
		}
		catch(Exception e) {e.printStackTrace();}
		return factory;
	}
	
	@Bean
	SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
