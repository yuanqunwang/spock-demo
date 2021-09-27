package com.liubike.spockdemo;

import com.liubike.customer.core.spring.boot.autoconfigure.K8sOpenFeignContextAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {K8sOpenFeignContextAutoConfiguration.class})
public class SpockDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpockDemoApplication.class, args);
	}

}
