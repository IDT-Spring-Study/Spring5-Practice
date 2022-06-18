package chapter03.config;

import chapter03.model.MemberDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberDaoComponent {

    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
    }
}
