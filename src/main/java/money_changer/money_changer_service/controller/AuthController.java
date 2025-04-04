package money_changer.money_changer_service.controller;

import money_changer.money_changer_service.model.MetaResponse;
import money_changer.money_changer_service.model.WebResponse;
import money_changer.money_changer_service.model.request.auth.LoginRequest;
import money_changer.money_changer_service.model.request.auth.OtpRequest;
import money_changer.money_changer_service.model.request.auth.RegisterRequest;
import money_changer.money_changer_service.model.request.auth.UpdatePasswordRequest;
import money_changer.money_changer_service.model.response.auth.LoginResponse;
import money_changer.money_changer_service.model.response.auth.RegisterResponse;
import money_changer.money_changer_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.time.LocalDateTime;

@RestController
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

    @PostMapping(
            path = "/auth/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse data = authService.login(request);
        return WebResponse.<LoginResponse>builder()
                .data(data)
                .meta(
                        MetaResponse.builder()
                                .code(HttpStatus.OK.value())
                                .messages("Login Berhasil")
                                .validations(null)
                                .responseDate(LocalDateTime.now())
                                .status("success")
                                .build()
                ).build();
    }
    
    @PostMapping(
            path = "/auth/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse registerResponse = authService.register(request);
        this.getLogController(registerResponse);
        return WebResponse.<RegisterResponse>builder()
                .data(registerResponse)
                .meta(
                        MetaResponse.builder()
                                .code(HttpStatus.OK.value())
                                .status("Success")
                                .validations(null)
                                .responseDate(LocalDateTime.now())
                                .messages("Register Successfull")
                                .build()
                )
                .build();
    }

    @PostMapping(
            path = "/auth/otp/submit",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> submitOtp(@RequestBody OtpRequest request){
        authService.submitOtp(request);
        return WebResponse.<String>builder()
                .meta(MetaResponse.builder()
                        .code(HttpStatus.OK.value())
                        .messages("Submit Otp Berhasil")
                        .validations(null)
                        .responseDate(LocalDateTime.now())
                        .build())
                .build();
    }

    @PostMapping(
            path = "/auth/otp/request",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> requestOtp(@RequestBody OtpRequest request) {
        authService.otpRequest(request);
        return WebResponse.<String>builder().meta(
                MetaResponse
                        .builder()
                        .code(HttpStatus.OK.value())
                        .messages("Kode Verifikasi telah di kirim ke email anda")
                        .responseDate(LocalDateTime.now())
                        .build()
        ).build();
    }


    @PostMapping(
            path = "/auth/forgot-password/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> updatePassword(@RequestBody UpdatePasswordRequest request) {
        authService.updatePassword(request);
        return WebResponse.<String>builder()
                .meta(
                        MetaResponse.builder()
                                .code(HttpStatus.OK.value())
                                .messages("Kata sandi kamu berhasil diubah")
                                .responseDate(LocalDateTime.now())
                                .build()
                )
                .build();
    }

}
