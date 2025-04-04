package money_changer.money_changer_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class BaseController {

    protected <T> T getLogController(T data) {
        log.info("DEBUGDATAAPAINIKAWAN: {}", data);
        return data;
    }
}
