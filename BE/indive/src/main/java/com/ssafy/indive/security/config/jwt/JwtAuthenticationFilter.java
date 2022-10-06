package com.ssafy.indive.security.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ssafy.indive.domain.member.entity.Member;
import com.ssafy.indive.security.config.auth.PrincipalDetails;
import com.ssafy.indive.security.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	@Override
	@Bean
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		ObjectMapper om = new ObjectMapper();
		try {
			LoginRequestDto loginRequestDto = om.readValue(request.getInputStream(),LoginRequestDto.class);

			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword());

			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

			log.info("로그인 완료됨 : " + principalDetails.getMember().getNickname());

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

		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

		String jwtToken = JWT.create()
				.withSubject("INDIVE")
				.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24*14))
				.withClaim("seq",principalDetails.getMember().getSeq())
				.withClaim("username", principalDetails.getMember().getEmail())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));


		String refreshToken = JWT.create()
				.withSubject("INDIVE")
				.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24*14))
				.withClaim("seq",principalDetails.getMember().getSeq())
				.withClaim("username", principalDetails.getMember().getEmail())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));

		response.addHeader("Authorization","Bearer "+jwtToken);
		response.getWriter().write("true");
		Cookie cookie = new Cookie("refreshToken",refreshToken);
		response.addCookie(cookie);
	}
}
