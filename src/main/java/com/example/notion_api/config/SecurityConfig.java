package com.example.notion_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        // csrf 비활성화
        httpSecurity.csrf((auth) -> auth.disable());

        // Form 로그인 방식 비활성화
        httpSecurity.formLogin((auth) -> auth.disable());

        // HTTP Basic 인증 방식 비활성화
        httpSecurity.httpBasic((auth) -> auth.disable());

        // oauth2(디폴트 설정)
        httpSecurity.oauth2Login(Customizer.withDefaults());

        // 경로별 인가 작업
        httpSecurity.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated());

        // 세션 설정 : STABLELESS
        httpSecurity.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }
}
