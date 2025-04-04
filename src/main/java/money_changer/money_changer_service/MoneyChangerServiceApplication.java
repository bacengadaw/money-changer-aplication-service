package money_changer.money_changer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MoneyChangerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyChangerServiceApplication.class, args);
	}

}
