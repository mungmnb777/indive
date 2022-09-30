package com.ssafy.indive.utils.security.factory;

import com.ssafy.indive.domain.MockEntityFactory;
import com.ssafy.indive.security.config.auth.PrincipalDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class WithMockSecurityContextFactory {

    public static void createSecurityContext() {
        SecurityContext context = SecurityContextHolder.getContext();

        PrincipalDetails principal = new PrincipalDetails(MockEntityFactory.member());

        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        context.setAuthentication(authentication);
    }
}
