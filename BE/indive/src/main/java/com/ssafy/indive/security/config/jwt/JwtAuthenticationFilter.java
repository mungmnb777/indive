package com.ssafy.indive.security.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.security.config.auth.PrincipalDetails;
import com.ssafy.indive.security.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


	private final AuthenticationManager authenticationManager;

	@Override
	@Bean
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter 로그인 시도중");
		//1. username, password 받아서
		//2. 정상적인지 로그인 시도를 해 봄(authenticationManager 로 로그인 시도를 하면! PrincipalDetailsService가 호출됨
		//loadUserByUsername() 함수 실행됨.
		//3.Principaldetails 를 세션에 담고 -> 세션에 안 담으면 권한 관리가 안 됨
		//4.jwt 토큰을 만ㄷ르어서 응답해주면 됨


		//파싱해줌
		ObjectMapper om = new ObjectMapper();
		try {
			//*로그인으로 패스워드나 비밀번호를 보냈을 때 여기에 정보가 들어 있음.
			LoginRequestDto loginRequestDto = om.readValue(request.getInputStream(),LoginRequestDto.class);
			System.out.println("jwt loginRequestDto : " +loginRequestDto);

			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword());

			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

			System.out.println("로그인 완료됨 : " + principalDetails.getMember().getNickname());

			return authentication;

		} catch (Exception e) {

			e.printStackTrace();
			try {
				response.getWriter().write("false");
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}

		}
		return null;
	}


	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		//*그리고 성공하면 여기로 오게 됨
		System.out.println("successfulAuthentication 실행됨 : 인증이 완료됨");

		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

		//RSA 아니고 Hash 방식임
		String jwtToken = JWT.create()
				.withSubject("INDIVE")
				.withExpiresAt(new Date(System.currentTimeMillis()+120000*10))
				.withClaim("seq",principalDetails.getMember().getSeq())
				.withClaim("username", principalDetails.getMember().getEmail())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 서버만 알고 있는 secret 값

		response.addHeader("Authorization","Bearer "+jwtToken);
		response.getWriter().write("true");


	}
}
