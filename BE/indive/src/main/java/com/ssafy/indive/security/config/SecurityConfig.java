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


    //AuthenticationManager 를 만드는 역할
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private CorsConfig corsConfig;

    @Autowired
    private MemberRepository memberRepository;

    //필드 주입은 권장되는 방법이 아님
//    @Autowired
//    private PrincipalOauth2UserService principalOauth2UserService;

    public SecurityConfig() {
    }
    //해당 메서드의 리턴되는 오브젝트를 Ioc로 등록해줌
//    @Bean
//    public BCryptPasswordEncoder encoderPwd(){
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // AuthenticationManager는 인증을 도와주는 친구..
        AuthenticationManager authenticationManagerObject = authenticationManagerBuilder.getObject();
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManagerObject);
        jwtAuthenticationFilter.setFilterProcessesUrl("/members/login");
////////////////
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
                //.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()))

                //TODO :
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                //////////////////////////////////////
                .anyRequest().permitAll(); // 나머지는 누구나 들어갈 수 있음
//                    .and() //권한 없는 페이지로 갔을 때 로그인 페이지로 이동하게
//                .formLogin()
//                .loginPage("/loginForm")
//               //유저네임 파라미터를 바꾸라면 .usernameParameter 써야함
//               .loginProcessingUrl("/login") // login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줍니다.
//               .defaultSuccessUrl("/")
//                   .and()
//               .oauth2Login()
//               .loginPage("/loginForm") // 구글 로그인이 완료된 뒤의 후처리가 필요함
//        //1. 코드받기(인증) , 2. 엑세스토큰(권한), 3. 프로필정보 가져오기 4. 회원가입 자동으로 진행시키기
//        //4의 정보가 모자라다면 정보를 추가해야 함
//        //참고로 로그인이 완료된 후에는 엑세스 토큰 + 사용자 프로필 정보를 한번에 가져온다.
//                .userInfoEndpoint()
//                .userService(principalOauth2UserService);
        return http.build();

    }
}