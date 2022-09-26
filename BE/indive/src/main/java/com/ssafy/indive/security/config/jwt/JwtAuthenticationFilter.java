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
			System.out.println("jwtA : " +loginRequestDto);

			//*manager 가 인증을 할 때 이 정보를 이용해서 인증을 하게 됨
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword());

			//PrincipalDetailsService 의 loadUserByUsername()함수가 실행됨
			//인증이되면 authentication 에 내 로그인한 정보가 담깁니다.

			//인증을 하게 되면 authentication 이라는 객체가 나오게 됨
			Authentication authentication = authenticationManager.authenticate(authenticationToken);

			//어쨋든 이걸 가져왔음. 이걸 securityconfig 에 넣음
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			// 이게 나왔다는건 인증이 정상적으로 되어서 session 영역에 저장된것
			System.out.println("로그인 완료됨 : " + principalDetails.getMember().getNickname());
			//얘가 session 에 저장
			//리턴의 이유는 권한 관리를 security 가 대신 해주기 때문에 편하려고 하는 것
			//굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 단지 권한 처리때문에 session 넣는것

			//넣기 직전에 JWT 토큰을 만들어 줌
			return authentication;

		} catch (Exception e) {

			e.printStackTrace();
		}

		System.out.println("=====================================");
		return null;
	}

	//attemptAuthentication 실행 후 인증이 정상적으로 되었다면 successfulAuthentication 함수가 실행됨
	//JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		//*그리고 성공하면 여기로 오게 됨
		System.out.println("successfulAuthentication 실행됨 : 인증이 완료됨");

		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

		//그리고 토큰을 만듬
		//RSA 아니고 Hash 방식임
		String jwtToken = JWT.create()
				.withSubject("INDIVE")
				//토큰만료시간
				.withExpiresAt(new Date(System.currentTimeMillis()+120000*10))
				//id 랑 username 같은건 내가 넣고싶은거 넣음됨
				.withClaim("seq",principalDetails.getMember().getSeq())
				.withClaim("username", principalDetails.getMember().getEmail())
				.sign(Algorithm.HMAC512("INDIVE")); // 서버만 알고 있는 secret 값

		//헤더에 넣겠다(Bearer 에 한칸 띄울것)
		response.addHeader("Authorization","Bearer "+jwtToken);
		response.getWriter().write("true");
	}
}
