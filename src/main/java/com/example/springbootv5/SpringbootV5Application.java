package com.example.springbootv5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author fangtaozhu
 */
@ServletComponentScan
@SpringBootApplication
public class SpringbootV5Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootV5Application.class, args);
	}

}
