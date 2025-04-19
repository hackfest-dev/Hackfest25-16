package hf25_16.debugging_chickens.mental_health_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
public class MentalHealthBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentalHealthBackendApplication.class, args);
	}

}
