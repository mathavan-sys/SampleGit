package com.fedex.geopolitical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({ "com.fedex.framework", "com.fedex.geopolitical" })

public class GeoPoliticalApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoPoliticalApplication.class, args);
	}


}
