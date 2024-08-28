package no.hvl.dat250Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestController
public class Dat250SpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(Dat250SpringApplication.class, args);
	}

	@GetMapping("/")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

}
