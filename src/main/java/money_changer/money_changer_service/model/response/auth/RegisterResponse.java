package money_changer.money_changer_service.model.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterResponse {

    private String email;

    private String phone;

    @JsonProperty("referral_code")
    private String referralCode;

    @JsonProperty("local_date_time")
    private LocalDateTime expiredOtp;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
