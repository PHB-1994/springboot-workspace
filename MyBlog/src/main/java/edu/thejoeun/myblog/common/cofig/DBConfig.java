package edu.thejoeun.myblog.common.cofig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.SessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/*
* 기존 java - dataBase 심하게 느렸다.
* 미국 개발자 Brett Wo..  성능에 민감한 Java 애플리케이션 개발을 진행하며
* 기존 연결에 불만을 가져 만든 풀  HikariCP(  Connection Pool  )
* 업계에서 가장 빠른 커넥션 풀 생성
* SpringBoot 에서 Java-DataBase 연결할 때 기본으로 가져가는 속성
*
* 자동적으로 pool-size 나 다른 세팅이 기본으로 적용되어 있으나,
* 개발자가 원하는 속성으로 수정 가능
* 수정한 설정은 config.properties 존재
*
* 이 내용은 최초 1회만 작성 후 수정 XXXXX
*/
@Configuration
@PropertySource("classpath:/config.properties") // src/main/resources/config.properties 파일에 작성한 변수명칭들 참고
public class DBConfig {
    // import org.springframework.context.ApplicationContext;
    @Autowired
    private ApplicationContext  applicationContext; // 현재 프로젝트 파일 내용 모두 참고

    @Value("${spring.datasource.url}") //properties에 작성한 속성명칭읽을 때 사용
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    // HIKARICP 설정 //

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariConfig  hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        // src/main/resources/config.properties 파일에서 읽어온
        // spring.datasource.hikari 로 시작하는 모든 값을 이 설정 안에서 사용하겠다.
        // return new HikariConfig();
        return config;
    }
    // import javax.sql.DataSource;
    @Bean
    public DataSource dataSource(HikariConfig config) {
        DataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }
    // MyBatis 설정
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        // mappers 내부에 작성한 xml(SQL)이 모이는 경로 설정
        // Mybatis 코드 수행 시  mapper.xml 을 읽을 수 있도록 설정
        // sessionFactory.setMapperLocations(현재프로젝트에서(어디에 위치한 mapper.xml 파일인지 위치 설정));

        sessionFactory.setMapperLocations(
                applicationContext.getResources("classpath:/mappers/**/*.xml")
        );

        // application.properties 에 작성한 model - mapper 를 연결해주는 속성 설정을
        // 여기에 작성
        // 해당 패키지 내 모든 클래스의 별칭을 등록
        // mybatis는 특정 클래스 지정 시 패키지명칭.클래스명칭을 모두 작성해야함
        // -> 긴 이름을 짧게 부를 수 있도록 설정

        // setTypeAliasesPackage("패키지") 이용 시 클래스 파일명을 별칭으로 등록
        sessionFactory.setTypeAliasesPackage("edu.thejoeun.myblog");

        // 마이바티스 설정 파일 경로 지정
        sessionFactory.setConfigLocation(
                applicationContext.getResource("classpath:/mybatis-config.xml")
        );

        // 설정된 내용이 모두 적용된 상태로 객체 반환
        return sessionFactory.getObject();
    }

    // DBCP(DataBase Connection Pool)
    // SqlSessionTemplate : Connection + DBCP + MyBatis + 트랜잭션 제어 처리
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) {
        return new SqlSessionTemplate(factory);
    }


    // save-point 나 자동 커밋이 안되는 oracle 의 경우
    // 트랜잭션 매니저를 따로 작성해주지 않으면
    // insert update delete  할 때 마다 트랜잭션 코드를 service마다 작성을 하나하나 해주어야함
    // 위 작업을 알아서 커밋해라 개발자가 추가로 원하는 롤백이나 커밋이 있으면 여기서 설정하자
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
