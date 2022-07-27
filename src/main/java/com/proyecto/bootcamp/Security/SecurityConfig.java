package com.proyecto.bootcamp.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.algorithms.Algorithm;
import com.proyecto.bootcamp.Security.Filters.CustomAuthenticationFilter;
import com.proyecto.bootcamp.Security.Filters.CustomAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired 
    private Algorithm algorithm;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(),algorithm);
        
        http.csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        authorizations(http);

        http.authorizeRequests()
            .anyRequest()
            .authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    } 

    public HttpSecurity authorizations(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/login/**","/usuarios/refresh/**").permitAll();
        
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/cursos/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/cursos/**").hasAnyAuthority("ROL_PROFESOR","ROL_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/cursos/**").hasAnyAuthority("ROL_PROFESOR","ROL_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/cursos/**").hasAnyAuthority("ROL_PROFESOR","ROL_ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/materias/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/materias/**").hasAnyAuthority("ROL_PROFESOR","ROL_ADMIN","ROL_ESTUDIANTE");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/materias/**").hasAnyAuthority("ROL_PROFESOR","ROL_ADMIN","ROL_ESTUDIANTE");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/materias/**").hasAnyAuthority("ROL_PROFESOR","ROL_ADMIN");
        return http;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManager();
    }
    
}
