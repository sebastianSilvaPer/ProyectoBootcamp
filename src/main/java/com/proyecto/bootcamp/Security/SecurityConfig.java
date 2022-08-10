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
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.algorithms.Algorithm;
import com.proyecto.bootcamp.Security.Exceptions.RestAccessDeniedHandler;
import com.proyecto.bootcamp.Security.Exceptions.RestAuthenticationEntryPoint;
import com.proyecto.bootcamp.Security.Filters.CustomAuthenticationFilter;
import com.proyecto.bootcamp.Security.Filters.CustomAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Algorithm algorithm;

    @Autowired
    private TextEncryptor textEncryptor;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
                authenticationManagerBean(), algorithm, textEncryptor);

        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        authorizations(http);

        http.authorizeRequests()
                .anyRequest()
                .authenticated();
        http.addFilter(customAuthenticationFilter);

        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());

        http.addFilterBefore(new CustomAuthorizationFilter(algorithm, textEncryptor),
                UsernamePasswordAuthenticationFilter.class);
    }

    public HttpSecurity authorizations(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/login/**", "/token/refresh/**").permitAll();

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/cursos").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/cursos").hasAnyAuthority("ROL_PROFESOR", "ROL_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/cursos").hasAnyAuthority("ROL_PROFESOR", "ROL_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/cursos").hasAnyAuthority("ROL_ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/cursos/{id}").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/cursos/{id}").hasAnyAuthority("ROL_PROFESOR",
                "ROL_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/cursos/{id}").hasAnyAuthority("ROL_ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/cursos/{idCurso}/materias").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/cursos/{idCurso}/materias")
                .hasAnyAuthority("ROL_PROFESOR", "ROL_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/cursos/{idCurso}/materias")
                .hasAnyAuthority("ROL_PROFESOR", "ROL_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/cursos/{idCurso}/materias")
                .hasAnyAuthority("ROL_PROFESOR", "ROL_ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/cursos/{idCurso}/materias/{id}").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/cursos/{idCurso}/materias/{id}")
                .hasAnyAuthority("ROL_PROFESOR", "ROL_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/cursos/{idCurso}/materias/{id}")
                .hasAnyAuthority("ROL_PROFESOR", "ROL_ADMIN");

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/usuarios/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/usuarios").hasAnyAuthority("ROL_ADMIN");
        return http;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    RestAccessDeniedHandler accessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    @Bean
    RestAuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }
}
