package com.ssafy.indive.security.config;



import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.security.config.jwt.JwtAuthenticationFilter;
import com.ssafy.indive.security.config.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션
@EnableGlobalMethodSecurity(securedEnabled = true , prePostEnabled = true) // secured 어노테이션 활성화,
public class SecurityConfig {


    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private CorsConfig corsConfig;

    @Autowired
    private MemberRepository memberRepository;


    public SecurityConfig() {
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        AuthenticationManager authenticationManagerObject = authenticationManagerBuilder.getObject();
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerObject);
        jwtAuthenticationFilter.setFilterProcessesUrl("/members/login");

        http
                .addFilter(corsConfig.corsFilter())
                //.addFilterBefore(new MyFilter3(), SecurityContextHolderFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .formLogin().disable()
                .httpBasic().disable()

                .addFilter(jwtAuthenticationFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManagerObject))
                .addFilter(new JwtAuthorizationFilter(authenticationManagerObject, memberRepository))

                //TODO : 수정필
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                //////////////////////////////////////
                .anyRequest().permitAll();

        return http.build();

    }
}