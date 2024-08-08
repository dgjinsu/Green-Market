package green_market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GreenMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenMarketApplication.class, args);
	}

}
