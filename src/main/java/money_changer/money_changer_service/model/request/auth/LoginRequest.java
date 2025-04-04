package money_changer.money_changer_service.model.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    private String user;
    private String password;

    @JsonProperty("is_number_phone")
    private String isNumberPhone;

    @JsonProperty("must_register_pin")
    private String mustRegisterPin;
}
