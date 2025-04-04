package money_changer.money_changer_service.controller;

import money_changer.money_changer_service.model.MetaResponse;
import money_changer.money_changer_service.model.WebResponse;
import money_changer.money_changer_service.model.response.user.UserResponse;
import money_changer.money_changer_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @PostMapping(
            path = "/user/me",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> getDataUser(){
        UserResponse dataUser = userService.getDataUser();
        return WebResponse.<UserResponse>builder()
                .data(dataUser)
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

}
