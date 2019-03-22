package com.libqa.zookeeper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	
	@Autowired
	private HelloWorldClient helloWorldClient;
	
	@GetMapping("/get-greeting")
	public String greetings() {
		return helloWorldClient.helloWorld();
	}
	
	@GetMapping("/name")
	public String name() {
		return helloWorldClient.name();
	}
	
	@GetMapping("/product")
	public String product() {
		return helloWorldClient.product();
	}

}