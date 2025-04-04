package money_changer.money_changer_service.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import money_changer.money_changer_service.model.MetaResponse;
import money_changer.money_changer_service.model.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<String>> constraitViolationException(ConstraintViolationException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(WebResponse.<String>builder()
                        .meta(MetaResponse.builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .messages(exception.getMessage())
                                .build()
                        )
                        .build());

    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> apiException(ResponseStatusException exception){
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(WebResponse.<String>builder()
                        .meta(
                                MetaResponse.builder()
                                        .code(exception.getStatusCode().value())
                                        .messages(exception.getReason())
                                        .responseDate(LocalDateTime.now())
                                        .build())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<String>> handleException(ErrorResponse response, Exception exception) {
        return ResponseEntity
                .status(response.getStatusCode())
                .body(
                        WebResponse.<String>builder()
                                .meta(MetaResponse.builder()
                                        .code(response.getStatusCode().value())
                                        .messages(exception.getMessage())
                                        .responseDate(LocalDateTime.now())
                                        .build())
                                .build()
                );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<WebResponse<String>> handleAuthenticationException(AuthenticationException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(WebResponse.<String>builder()
                        .meta(MetaResponse.builder()
                                .code(HttpStatus.UNAUTHORIZED.value())
                                .messages(exception.getMessage())
                                .responseDate(LocalDateTime.now())
                                .build())
                        .build());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<WebResponse<String>> handleIOException(IOException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(WebResponse.<String>builder()
                        .meta(MetaResponse.builder()
                                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .messages(exception.getMessage())
                                .responseDate(LocalDateTime.now())
                                .build())
                        .build());
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<WebResponse<String>> handleServletException(ServletException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(WebResponse.<String>builder()
                        .meta(MetaResponse.builder()
                                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .messages(exception.getMessage())
                                .responseDate(LocalDateTime.now())
                                .build())
                        .build());
    }

    // Menangani JWT Expired
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<WebResponse<String>> handleExpiredJwtException(ExpiredJwtException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(WebResponse.<String>builder()
                        .meta(MetaResponse.builder()
                                .code(HttpStatus.UNAUTHORIZED.value())
                                .messages("JWT token has expired")
                                .build()
                        )
                        .build());
    }

    // Menangani JWT yang tidak valid atau salah format
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<WebResponse<String>> handleJwtException(JwtException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(WebResponse.<String>builder()
                        .meta(MetaResponse.builder()
                                .code(HttpStatus.UNAUTHORIZED.value())
                                .messages("Invalid JWT token")
                                .build()
                        )
                        .build());
    }

}
