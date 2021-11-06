package com.evgeniy638.documents.configs;

import com.evgeniy638.documents.modules.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;

    @Autowired
    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                    .dataSource(dataSource)
                    .passwordEncoder(getPasswordEncoder())
                    .usersByUsernameQuery("select username, password, active from usr where username=?")
                    .authoritiesByUsernameQuery("select u.username, ur.roles from usr u inner join user_role ur on ur.user_id=u.id where u.username=?")
                .and()
                .inMemoryAuthentication()
                    .withUser("root").password(getPasswordEncoder().encode("root"))
                    .authorities(Role.ADMIN.toString());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/change-pass").permitAll()
                    .antMatchers("/home").hasAuthority(Role.STUDENT.toString())
                    .antMatchers("/admin/**").hasAuthority(Role.ADMIN.toString())
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .rememberMe()
                .and()
                    .logout()
                    .permitAll();
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
