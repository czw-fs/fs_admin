package com.fsAdmin.config.security;



import com.fsAdmin.config.security.haveLoginFilterChain.JwtFilter;
import com.fsAdmin.config.security.loginChain.filter.UsernameAuthenticationFilter;
import com.fsAdmin.config.security.haveLoginFilterChain.handle.AuthenticationExceptionHandler;
import com.fsAdmin.config.security.haveLoginFilterChain.handle.CustomAccessDeniedHandler;
import com.fsAdmin.config.security.loginChain.handle.LoginFailHandler;
import com.fsAdmin.config.security.loginChain.handle.LoginSuccessHandler;
import com.fsAdmin.config.security.loginChain.provider.UsernamePasswordAuthenticationProvider;
import com.fsAdmin.modules.System.menu.mapper.MenuMapper;
import com.fsAdmin.modules.System.role.mapper.RoleMapper;
import com.fsAdmin.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
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
    private final JwtUtil jwtUtil;
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;

    /**
     * 登录请求的过滤器链
     */
    @Bean
    public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
        disableSomeHttpSetting(http);

        http
                .securityMatchers(config -> config.requestMatchers(new AntPathRequestMatcher("/login", "POST")))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/login", "POST").permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationExceptionHandler)// 认证失败异常
                );

        UsernameAuthenticationFilter authenticationFilter = new UsernameAuthenticationFilter(
                new AntPathRequestMatcher("/login", "POST"),
                new ProviderManager(usernamePasswordAuthenticationProvider),
                loginSuccessHandler,
                loginFailHandler
        );

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
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

        http
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
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler) // 授权失败处理
                        .authenticationEntryPoint(authenticationExceptionHandler)
                );

        JwtFilter jwtFilter = new JwtFilter(jwtUtil, roleMapper, menuMapper);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // 添加 JWT 过滤器

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
                .requestCache(RequestCacheConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable);// 无需给用户一个匿名身份
    }
}