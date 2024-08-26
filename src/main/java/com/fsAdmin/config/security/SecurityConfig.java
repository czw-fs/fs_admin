package com.fsAdmin.config.security;


import com.fsAdmin.config.security.login.filter.JwtFilter;
import com.fsAdmin.config.security.login.filter.UsernameAuthenticationFilter;
import com.fsAdmin.config.security.login.handle.AuthenticationExceptionHandler;
import com.fsAdmin.config.security.login.handle.CustomAccessDeniedHandler;
import com.fsAdmin.config.security.login.handle.LoginFailHandler;
import com.fsAdmin.config.security.login.handle.LoginSuccessHandler;
import com.fsAdmin.config.security.login.provider.UsernamePasswordAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationExceptionHandler authenticationExceptionHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;
    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
        disableSomeHttpSetting(http);
        // 所有路径都需要认证
        http.securityMatcher("/**")
                .cors((cors)-> cors.configurationSource(corsConfigurationSource()))//配置自定义跨域
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                ;

        // 用户名、密码登录
        // /login走认证过滤器
        UsernameAuthenticationFilter usernameLoginFilter = new UsernameAuthenticationFilter(
                new AntPathRequestMatcher("/login", HttpMethod.POST.name()),
                new ProviderManager(usernamePasswordAuthenticationProvider),
                loginSuccessHandler,
                loginFailHandler
        );
        //注意顺序，先添加的过滤器排在前面，所以先添加jwtFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(usernameLoginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /** 不鉴权的api */
    @Bean
    public SecurityFilterChain publicApiFilterChain(HttpSecurity http) throws Exception {
        disableSomeHttpSetting(http);
        http
                // 使用securityMatcher限定当前配置作用的路径
                .securityMatcher("/static/**")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return http.build();
    }

    /**
     * 禁用不必要的默认filter，处理异常响应内容
     * 禁用SpringSecurity默认filter。这些filter都是非前后端分离项目的产物，用不上.
     */
    private void disableSomeHttpSetting(HttpSecurity http) throws Exception {
        /**
         *          yml配置文件将日志设置DEBUG模式，就能看到加载了哪些filter
         *          logging:
         *             level:
         *                org.springframework.security: DEBUG
         *          表单登录/登出、session管理、csrf防护等默认配置，如果不disable。会默认创建默认filter
         */
        http.formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)// requestCache用于重定向，前后端分析项目无需重定向，requestCache也用不上
                .requestCache(cache -> cache.requestCache(new NullRequestCache()))
                .anonymous(AbstractHttpConfigurer::disable);// 无需给用户一个匿名身份

        // 处理 SpringSecurity 异常响应结果。响应数据的结构，改成业务统一的JSON结构。不要框架默认的响应结构
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        // 认证失败异常
                        .authenticationEntryPoint(authenticationExceptionHandler)
                        // 鉴权失败异常
                        .accessDeniedHandler(customAccessDeniedHandler)
        );
        // 其他未知异常. 尽量提前加载。
//        http.addFilterBefore(globalSpringSecurityExceptionHandler, SecurityContextHolderFilter.class);
    }


    /**
     * 跨域配置
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setMaxAge(Duration.ofHours(1));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
    //    @Bean
//    public AuthenticationManager customAuthenticationManager(
//            UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider) {
//        return new ProviderManager(usernamePasswordAuthenticationProvider);
//    }

//    @Bean
//    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter jwtFilter) {
//        FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>(jwtFilter);
//        /**
//         * 当你@Component 注解并继承 OncePerRequestFilter 时，
//         * Spring Boot 会自动将 JwtFilter 注册到默认的过滤器链中，因此它会自动应用于所有请求。
//         *
//         * 你在 SecurityFilterChain 配置中通过 http.addFilterBefore(jwtFilter, SecurityContextHolderFilter.class)
//         * 显式地将 JwtFilter 添加到过滤器链中。这会导致 JwtFilter 再次被添加，并在指定的位置执行。
//         *
//         * 所以JwtFilter会被调用两次
//         * 要避免这样所以
//         * registration.setEnabled(false);
//         *
//         *  @Autowired 注解依然可以正常工作，因为 FilterRegistrationBean 的 enabled 属性仅影响自动注册，而不影响 Spring 的依赖注入机制。
//         *  FilterRegistrationBean 的 enabled=false，避免了过滤器的自动注册，
//         *  同时在 Spring Security 配置中手动添加过滤器来控制其在过滤器链中的位置和执行顺序。

    //但是经过我测试@Component的过滤器和http.addFilterBefore(jwtFilter, SecurityContextHolderFilter.class)只会执行一次
    //官网文档看不懂了

//    通过在 AuthorizationFilter 之前添加filter，我们确保 TenantFilter 在认证 filter 之后被调用。你也可以使用 HttpSecurity#addFilterAfter 将 filter 添加到某个特定的 filter 之后，或者使用 HttpSecurity#addFilterAt 将 filter 添加到 filter chain 中的某个位置。
//
//    就这样，现在 TenantFilter 将在过 filter chain 中被调用，并将检查当前用户是否对租户ID有访问权。
//
//    当你把你的 filter 声明为 Spring Bean 时要小心，可以用 @Component 注解它，也可以在配置中把它声明为 Bean，因为 Spring Boot 会自动 在嵌入式容器中注册它。这可能会导致 filter 被调用两次，一次由容器调用，一次由 Spring Security 调用，而且顺序不同。
//
//    如果你仍然想把你的 filter 声明为 Spring Bean，以利用依赖注入，避免重复调用，你可以通过声明 FilterRegistrationBean Bean 并将其 enabled 属性设置为 false 来告诉 Spring Boot 不要向容器注册它：
//
//    @Bean
//    public FilterRegistrationBean<TenantFilter> tenantFilterRegistration(TenantFilter filter) {
//        FilterRegistrationBean<TenantFilter> registration = new FilterRegistrationBean<>(filter);
//        registration.setEnabled(false);
//        return registration;
//    }
//         */
//        registration.setEnabled(false); // 禁用自动注册
//        return registration;
//    }
}