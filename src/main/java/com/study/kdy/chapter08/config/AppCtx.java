package com.study.kdy.chapter08.config;

import com.study.kdy.chapter08.model.MemberDao;
import com.study.kdy.chapter08.service.ChangePasswordService;
import com.study.kdy.chapter08.service.TransactionMemberDaoService;
import com.study.kdy.chapter08.service.TransactionWrappingService;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class AppCtx {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver"); // 드라이버 설정
        ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8"); // DB URL 설정
        ds.setUsername("spring5"); // ID
        ds.setPassword("spring5"); // PWD
        ds.setInitialSize(2); // 커넥션풀 초기 커넥션 갯수
        ds.setMaxActive(10); // 커넥션풀 최대 커넥션 갯수
        ds.setMaxIdle(10); // 커넥션풀 최대 유휴 커넥션 갯수 -> 최대 커넥션과 동일하게 유지해야 경고가 발생하지 않는다.
        ds.setTestWhileIdle(true); // 유휴상태 커넥션 테스트
        ds.setMinEvictableIdleTimeMillis(60000 * 3); // 유휴상태 커넥션 유지시간 -> 이후 최소 갯수를 제외하고 해제됨.
        ds.setTimeBetweenEvictionRunsMillis(10 * 1000); // 유휴 커넥션 검사 주기
        return ds;
    }

    @Bean
    public MemberDao memberDao() {
        return new MemberDao(dataSource());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public ChangePasswordService changePasswordService() {
        var changePasswordService = new ChangePasswordService();
        changePasswordService.setMemberDao(memberDao());
        return changePasswordService;
    }

    @Bean
    public TransactionMemberDaoService transactionMemberDaoService() {
        return new TransactionMemberDaoService(memberDao());
    }

    @Bean
    public TransactionWrappingService transactionWrappingService() {
        return new TransactionWrappingService(transactionMemberDaoService());
    }

}
