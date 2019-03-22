package com.libqa.zookeeper;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "zookeeper-provider")
public interface HelloWorldFeignClient {
	
	@RequestMapping(path = "/helloworld", method = RequestMethod.GET)
    @ResponseBody
	String helloWorld();
	
	@RequestMapping(path = "/name", method = RequestMethod.GET)
    @ResponseBody
	String name();
	
	@RequestMapping(path = "/product", method = RequestMethod.GET)
    @ResponseBody
	String product();
}