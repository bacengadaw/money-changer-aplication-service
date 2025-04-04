package money_changer.money_changer_service.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String jsonResponse = String.format("""
        {
            "meta": {
                "code": 401,
                "status": "unauthorized",
                "messages": "%s",
                "validations": null,
                "responseDate": "%s"
            },
            "data": null
        }
        """, authException.getMessage(), LocalDateTime.now());
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}