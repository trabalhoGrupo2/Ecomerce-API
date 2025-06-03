package org.serratec.h2.grupo2.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
        response.setContentType("application/json");

        String json = """
        {
            "status": 403,
            "titulo": "Acesso proibido",
            "mensagem": "Você não tem permissão para acessar este recurso.",
            "dataHora": "%s"
        }
        """.formatted(LocalDateTime.now());

        response.getWriter().write(json);
    }
}