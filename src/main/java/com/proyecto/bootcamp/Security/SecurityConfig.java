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

import com.proyecto.bootcamp.Security.Filters.CustomAuthenticationFilter;
import com.proyecto.bootcamp.Security.Filters.CustomAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/login/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/cursos/**").hasAnyAuthority("ROL_PROFESOR","ROL_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/cursos/**").hasAnyAuthority("ROL_PROFESOR","ROL_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/cursos/**").hasAnyAuthority("ROL_PROFESOR","ROL_ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.POST, "/materias/**").hasAnyAuthority("ROL_PROFESOR","ROL_ADMIN","ROL_ESTUDIANTE");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/materias/**").hasAnyAuthority("ROL_PROFESOR","ROL_ADMIN","ROL_ESTUDIANTE");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/materias/**").hasAnyAuthority("ROL_PROFESOR","ROL_ADMIN");
        
        http.authorizeRequests()
            .anyRequest()
            .authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    } 

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManager();
    }
    
}
