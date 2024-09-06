package com.example.notion_api.config;

import com.example.notion_api.jwt.JwtFilter;
import com.example.notion_api.jwt.JwtUtil;
import com.example.notion_api.oauth2.CustomSuccessHandler;
import com.example.notion_api.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    private final CustomSuccessHandler customSuccessHandler;
    private final JwtUtil jwtUtil;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomSuccessHandler customSuccessHandler, JwtUtil jwtUtil) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.jwtUtil = jwtUtil;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                }));

        // csrf 비활성화
        httpSecurity.csrf((auth) -> auth.disable());

        // Form 로그인 방식 비활성화
        httpSecurity.formLogin((auth) -> auth.disable());

        // HTTP Basic 인증 방식 비활성화
        httpSecurity.httpBasic((auth) -> auth.disable());

        //JWTFilter 추가
        httpSecurity
                .addFilterBefore(new JwtFilter(jwtUtil),
                        UsernamePasswordAuthenticationFilter.class);


        // oauth2(디폴트 설정)
        httpSecurity.oauth2Login((oauth2) -> oauth2
                .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService)))
                .successHandler(customSuccessHandler)
        );

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
