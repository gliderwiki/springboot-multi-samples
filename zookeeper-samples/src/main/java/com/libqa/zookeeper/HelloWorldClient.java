package com.libqa.zookeeper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
@EnableDiscoveryClient
public class HelloWorldClient {
	
	@Autowired
	private HelloWorldFeignClient feign;
	
	public String helloWorld() {
		return feign.helloWorld();
	}
	
	public String name() {
		return feign.name();
	}
	
	public String product() {
		return feign.product();
	}

}
