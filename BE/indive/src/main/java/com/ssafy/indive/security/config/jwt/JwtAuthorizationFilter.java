package com.ssafy.indive.security.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.domain.member.repository.MemberRepository;
import com.ssafy.indive.security.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

// 인가
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	//TODO : 이건왜있는걸까

	private MemberRepository memberRepository;
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
		super(authenticationManager);
		this.memberRepository = memberRepository;
	}

	//인증이나 권한이 필요할 때 해당 필터를 타게 됨
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		//super.doFilterInternal(request, response, chain);
		System.out.println("인증이나 권한이 필요한 주소 요청");

		//사용자에게서 토큰을 받고
		String jwtHeader = request.getHeader("Authorization");
		System.out.println("jwtheader : "+jwtHeader);
		//JSW 토큰을 검증해서 정상적인 사용자인지 확인

		//header 가 있는지 확인
		if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
			chain.doFilter(request,response);
			return;
		}

		//*JWT 토큰을 검증해서 정상적인 사용자인지 확인
		//딱 토큰만 남김
		String jwtToken = request.getHeader("Authorization").replace("Bearer ","");
		//서명
		//*토큰 주인의 유저네임이 뭔지 판별
		String username = null;
		try {
			username = JWT.require(Algorithm.HMAC512("INDIVE")).build().verify(jwtToken).getClaim("username").asString();
		} catch (TokenExpiredException e) {
			//1. 리프레시가 기간 남았는지 확인한다
			Cookie[] cookies = request.getCookies();

			for (Cookie cookie : cookies) {
				if ("refreshToken".equals(cookie.getName())) {
					String refreshToken = cookie.getValue().replace("Bearer ","");
					try {
						Date date = JWT.require(Algorithm.HMAC512("INDIVE")).build().verify(refreshToken).getExpiresAt();
					}  catch (TokenExpiredException ex) {
						return;
					}
					//토큰을 재발급한다.
					String neqAccessToken = JWT.create()
							.withSubject("INDIVE")
							.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24*14))
							.withClaim("seq",JWT.require(Algorithm.HMAC512("INDIVE")).build().verify(refreshToken).getClaim("seq").asString())
							.withClaim("username",JWT.require(Algorithm.HMAC512("INDIVE")).build().verify(refreshToken).getClaim("username").asString())
							.sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 서버만 알고 있는 secret 값

					response.addHeader("Authorization","Bearer "+neqAccessToken);
					username = JWT.require(Algorithm.HMAC512("INDIVE")).build().verify(refreshToken).getClaim("username").asString();
				}
			}
			//2. 엑세스 새로 발급해준다
		}

		//엑세스가 만료되었는지


		//*유저네임이 있다면 아래 메소드를 실행시킴
		//서명이 정상적으로 됨
		if(username!=null){
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+username);
			Member member  = memberRepository.findByEmail(username).orElseThrow(IllegalArgumentException::new);

			//*유저 정보 들어 있음
			PrincipalDetails principalDetails = new PrincipalDetails(member);
			//Jwt 토큰을 통해서 서명이 정상이면 Authenticatino 객체를 만들어줌
			//* authentication 만듦. 왜 만드냐면 securitycontext 에 담기 위해서.
			//securitycontext 는 전역에서 쓸 수 있기 때문에. 여기 담은 다음에 권한 체크 할 때도 여기서 빼서 쓸 수 있기 때문에.
			//로그인 한 유저가 누구인지 비지니스 로직에서 알아야 할 순간도 있을 텐데 ( 로그인한 유저가 ~주인이거나 할 때)

			Authentication authentication =
					new UsernamePasswordAuthenticationToken(principalDetails,null,principalDetails.getAuthorities());

			//강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장 -> 로그인한 정보를 확인해야 할 필요가 있기 때문에.
			SecurityContextHolder.getContext().setAuthentication(authentication);


		}
		chain.doFilter(request,response);
	}
}
