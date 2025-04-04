package money_changer.money_changer_service.model.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import money_changer.money_changer_service.helpers.ValidEnum;
import money_changer.money_changer_service.model.constant.TypeUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpRequest {

    @NotBlank
    @Size(max = 15)
    private String username;

    @NotBlank
    @ValidEnum(enumClass = TypeUser.class, message = "Type harus phone atau email")
    private String type;

    @Size(max = 6)
    private String otp;

}
