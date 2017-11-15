package game.mightywarriors.configuration.jwt.security;

import game.mightywarriors.configuration.jwt.model.JwtAuthenticationToken;
import game.mightywarriors.configuration.system.SystemVariablesManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    public JwtAuthenticationTokenFilter() {
        super("/secure/**");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        String authenticationToken = httpServletRequest.getHeader(SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN);

        if (authenticationToken == null) {
            throw new RuntimeException("JWT Token is missing");
        }

        JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken);
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }
}
