package com.libqa.zookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceConsumer {

	public static void main(String[] args) {
		SpringApplication.run(ServiceConsumer.class, args);
	}
}