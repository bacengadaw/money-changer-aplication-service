package money_changer.money_changer_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MetaResponse {
    private Integer code;

    private String status;

    private String messages;

    private List<Object> validations;

    private LocalDateTime responseDate;
}
