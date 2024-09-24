package com.itb.lip2.academicologininf3an.security;

import com.itb.lip2.academicologininf3an.filter.CustomAuthenticationFilter;
import com.itb.lip2.academicologininf3an.filter.CustomAuthorizationFilter;
import com.itb.lip2.academicologininf3an.repository.UsuarioRepository;
import com.itb.lip2.academicologininf3an.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final UsuarioService usuarioService;



    public SecurityConfig(UserDetailsService userDetailsService, UsuarioService usuarioService) {
        this.userDetailsService = userDetailsService;
        this.usuarioService = usuarioService;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), usuarioService);
        customAuthenticationFilter.setFilterProcessesUrl("/academico/api/v1/login");
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS).
                and().authorizeRequests().antMatchers("/academico/api/v1/login/**","/academico/api/v1/users/**", "/academico/api/v1/logout/**").permitAll();
        http.authorizeRequests().
                antMatchers("/academico/api/v1/professor/**").hasAnyAuthority("ROLE_INSTRUCTOR").
                antMatchers("/academico/api/v1/aluno/**").hasAnyAuthority("ROLE_STUDENT").
                antMatchers("/academico/api/v1/funcionario/**").hasAnyAuthority("ROLE_FUNCIONARIO").
                antMatchers("/academico/api/v1/admin/**").hasAnyAuthority("ROLE_ADMIN").

                anyRequest().authenticated();
          http.addFilter(customAuthenticationFilter);
          http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
