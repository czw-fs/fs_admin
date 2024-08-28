package com.fsAdmin.config.security;


import com.fsAdmin.config.security.cors.CorsConfig;
import com.fsAdmin.config.security.haveLoginFilterChain.JwtFilter;
import com.fsAdmin.config.security.loginChain.filter.UsernameAuthenticationFilter;
import com.fsAdmin.config.security.loginChain.handle.AuthenticationExceptionHandler;
import com.fsAdmin.config.security.haveLoginFilterChain.handle.CustomAccessDeniedHandler;
import com.fsAdmin.config.security.loginChain.handle.LoginFailHandler;
import com.fsAdmin.config.security.loginChain.handle.LoginSuccessHandler;
import com.fsAdmin.config.security.loginChain.provider.UsernamePasswordAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private final AuthenticationExceptionHandler authenticationExceptionHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;
    private final JwtFilter jwtFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    /**
     * 登录请求的过滤器链
     */
    @Bean
    public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
        disableSomeHttpSetting(http);

        http.cors(cors -> cors.configurationSource(corsConfigurationSource))
                .securityMatchers(config -> config.requestMatchers(new AntPathRequestMatcher("/login", "POST")))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/login", "POST").permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationExceptionHandler)// 认证失败异常
                );

        // 用户名、密码登录
        UsernameAuthenticationFilter usernameLoginFilter = new UsernameAuthenticationFilter(
                new AntPathRequestMatcher("/login", "POST"),
                new ProviderManager(usernamePasswordAuthenticationProvider),
                loginSuccessHandler,
                loginFailHandler
        );

        http.addFilterBefore(usernameLoginFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 非登录请求的过滤器链
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
        disableSomeHttpSetting(http);

        http.cors(cors -> cors.configurationSource(corsConfigurationSource))
                .securityMatchers(config -> {
                    config.requestMatchers(
                                    new AntPathRequestMatcher("/dict/**"))
                            .requestMatchers(new AntPathRequestMatcher("/dictItem/**"))
                            .requestMatchers(new AntPathRequestMatcher("/menu/**"))
                            .requestMatchers(new AntPathRequestMatcher("/role/**"))
                            .requestMatchers(new AntPathRequestMatcher("/user/**"))
                            .requestMatchers(new AntPathRequestMatcher("/static/**"));
                }).authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/static/**").permitAll()
                        .anyRequest().authenticated()
                ).exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(customAccessDeniedHandler)// 鉴权失败异常
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 禁用不必要的默认filter，处理异常响应内容
     * 禁用SpringSecurity默认filter。这些filter都是非前后端分离项目的产物，用不上.
     */
    private void disableSomeHttpSetting(HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)// requestCache用于重定向，前后端分析项目无需重定向，requestCache也用不上
                .requestCache(cache -> cache.requestCache(new NullRequestCache()))
                .anonymous(AbstractHttpConfigurer::disable);// 无需给用户一个匿名身份
    }
}