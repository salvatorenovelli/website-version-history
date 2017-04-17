package com.github.salvatorenovelli.seo.websiteversioning.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.github.salvatorenovelli.seo.websiteversioning.security.AuthoritiesConstants.ADMIN;
import static com.github.salvatorenovelli.seo.websiteversioning.security.AuthoritiesConstants.USER;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/home").permitAll()
            .antMatchers("/admin/**").hasAuthority(ADMIN)
            .anyRequest().authenticated()
        .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
        .and()
            .logout()
            .permitAll()
        .and()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .authorizeRequests()
            .antMatchers("/api/**").authenticated()
        .and()
            .csrf().disable();//TODO: remove this
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,
                                @Value("${default.user}") String defaultUser,
                                @Value("${default.password}") String defaultPassword,
                                @Value("${admin.user}") String adminUser,
                                @Value("${admin.password}") String adminPassword
    ) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser(defaultUser).password(defaultPassword).roles("USER")
        .and()
            .withUser(adminUser).password(adminPassword).roles("USER", "ADMIN");
    }
}