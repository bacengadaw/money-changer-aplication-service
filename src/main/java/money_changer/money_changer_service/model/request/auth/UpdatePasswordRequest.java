package money_changer.money_changer_service.model.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import money_changer.money_changer_service.helpers.FieldMatch;
import money_changer.money_changer_service.helpers.ValidEnum;
import money_changer.money_changer_service.model.constant.TypeUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordConfirmation", message = "The password fields must match")
})
public class UpdatePasswordRequest {

    @NotBlank
    @Size(max = 100)
    private String user;

    @ValidEnum(enumClass = TypeUser.class, message = "Type harus phone atau email")
    @Size(max = 100)
    private String type;

    @NotBlank
    @Size(max = 100)
    private String password;

    @NotBlank
    @Size(max = 100)
    @JsonProperty("password_confirmation")
    private String passwordConfirmation;

}
