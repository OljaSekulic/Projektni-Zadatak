package xwsagent.wallservice;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WallServiceApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(WallServiceApplication.class, args);
		System.out.println("Agent monolith is running");
	}

}
