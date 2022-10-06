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


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private MemberRepository memberRepository;
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
		super(authenticationManager);
		this.memberRepository = memberRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

		String jwtHeader = request.getHeader("Authorization");


		if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
			chain.doFilter(request,response);
			return;
		}

		String jwtToken = request.getHeader("Authorization").replace("Bearer ","");

		String username = null;
		try {
			username = JWT.require(Algorithm.HMAC512("INDIVE")).build().verify(jwtToken).getClaim("username").asString();
		} catch (TokenExpiredException e) {

			Cookie[] cookies = request.getCookies();

			for (Cookie cookie : cookies) {
				if ("refreshToken".equals(cookie.getName())) {
					String refreshToken = cookie.getValue().replace("Bearer ","");
					try {
						Date date = JWT.require(Algorithm.HMAC512("INDIVE")).build().verify(refreshToken).getExpiresAt();
					}  catch (TokenExpiredException ex) {
						return;
					}

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
		}

		if(username!=null){
			Member member  = memberRepository.findByEmail(username).orElseThrow(IllegalArgumentException::new);

			PrincipalDetails principalDetails = new PrincipalDetails(member);
			Authentication authentication =
					new UsernamePasswordAuthenticationToken(principalDetails,null,principalDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request,response);
	}
}
