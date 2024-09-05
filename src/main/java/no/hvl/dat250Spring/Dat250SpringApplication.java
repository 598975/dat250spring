package no.hvl.dat250Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan(basePackages = {"no.hvl.dat250Spring.controller", "no.hvl.dat250Spring.model"})
public class Dat250SpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(Dat250SpringApplication.class, args);
	}
}
