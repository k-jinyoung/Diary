/*
package com.codehows.diary.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final UserDetailsService userService;

    //스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/login", "/signup", "/user").permitAll()
                                .anyRequest().authenticated()                )
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login").permitAll()
                                .defaultSuccessUrl("/calendar")
                )
                .logout((logoutConfig) ->
                        logoutConfig
                                .logoutSuccessUrl("/login").permitAll()
                                .invalidateHttpSession(true)
                )
                .csrf((csrfConfig) ->
                        csrfConfig
                                .disable()
                )
                .build();

    }

    //인증 관리자 관련 설정
    */
/*@Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth, BCryptPasswordEncoder passwordEncoder, UserDetailService userDetailService) throws Exception{
        auth
                .userDetailsService(userDetailService)      //사용자 정보 서비스 설정
                .passwordEncoder(passwordEncoder);

        return auth.build();            //AuthenticationManager 반환
    }*//*


    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return new ProviderManager(authProvider);
    }

    //패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
*/
