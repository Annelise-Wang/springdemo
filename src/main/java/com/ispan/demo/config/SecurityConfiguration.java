package com.ispan.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;
   
    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        
    }
    
   

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
    	 requestCache.setMatchingRequestParameterName("mycustomparameter");
        http.userDetailsService(userDetailsService)
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                    .anyRequest().authenticated())
            .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/indexPage")
            .failureUrl("/login/error"))
            .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.permitAll()
                    //.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            		//.logoutUrl("/logout").permitAll()
                    .logoutSuccessUrl("/login").permitAll())          
        .requestCache(cache -> cache.requestCache(requestCache));
        return http.build();
    }

    
    
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    private final UserDetailsService userDetailsService;
//
//    public SecurityConfiguration(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests(authorizeRequests ->
//                authorizeRequests
//                    .requestMatchers("/login", "/login/error").permitAll()
//                    .anyRequest().authenticated()
//            )
//            .formLogin(formLogin ->
//                formLogin
//                    .loginPage("/login")
//                    .defaultSuccessUrl("/index")
//                    .failureUrl("/login/error")
//                    .permitAll()
//            )
//            .logout(logout ->
//                logout
//                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/login")
//                    .permitAll()
//            );
//    }
}



