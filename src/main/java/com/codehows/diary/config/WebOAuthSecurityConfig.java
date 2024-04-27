package com.codehows.diary.config;

import com.codehows.diary.config.TokenAuthenticationFilter;
import com.codehows.diary.config.jwt.TokenProvider;
import com.codehows.diary.config.oauth.OAuth2SuccessHandler;
import com.codehows.diary.config.oauth.OAuth2UserCustomService;
import com.codehows.diary.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.codehows.diary.repository.RefreshTokenRepository;
import com.codehows.diary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {
    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Bean
    public WebSecurityCustomizer configure() {          //스프링 시큐리티 비활성화
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/img/**", "/css/**", "/js/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //토큰 방식으로 인증을 하기 때문에 기존에 사용하던 폼로그인, 세션 비활성화
        http
                .csrf((csrf) ->
                        csrf.disable()
                )
                .httpBasic((httpBasic) ->
                        httpBasic.disable()
                )
                .formLogin((formLogin) ->
                        formLogin.disable()
                )
                .logout((logout) ->
                        logout.disable()
                )

                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //헤더를 왁인할 커스텀 필터 추가
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        //토큰 재발급 URL은 인증 없이 접근 가능하도록 설정. 나머지 QPI URL은 인증 필요
        http.authorizeRequests()
                .requestMatchers("/api/token").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll();

        http
                .oauth2Login((oauth2Login) ->
                        oauth2Login
                                .loginPage("/login")
                                .authorizationEndpoint(authorization -> authorization
                                        .authorizationRequestRepository(oAuthAuthorizationRequestBasedOnCookieRepository()))
                                .successHandler(oAuth2SuccessHandler())
                                .userInfoEndpoint(userInfo -> userInfo
                                        .userService(oAuth2UserCustomService)));

        http
                .logout((logout) ->
                        logout
                                .logoutSuccessUrl("/login"));

        http
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), new AntPathRequestMatcher("/api/**")));

        return http.build();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler(){
        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenRepository,
                oAuthAuthorizationRequestBasedOnCookieRepository(),
                userService
        );
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(){
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuthAuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
