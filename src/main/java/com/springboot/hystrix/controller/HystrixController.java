package com.springboot.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/hystrix/v1")
public class HystrixController {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(groupKey = "boot app", commandKey = "boot app", fallbackMethod = "fallBackFailed")
	@GetMapping("/getData")
	public String getName() {
		String responseFromRestServicesApp = restTemplate.getForObject("http://localhost:8888/api/v1/sayHi",
				String.class);
		String responseFromReactFullStackApp = restTemplate.getForObject("http://localhost:9999/api/v1/getData",
				String.class);
		return responseFromRestServicesApp + "\n" + responseFromReactFullStackApp;
	}

	@GetMapping("/getInfo")
	public String getData() {
		String responseFromRestServicesApp = restTemplate.getForObject("http://localhost:8888/api/v1/sayHi",
				String.class);
		String responseFromReactFullStackApp = restTemplate.getForObject("http://localhost:9999/api/v1/getData",
				String.class);
		return responseFromRestServicesApp + "\n" + responseFromReactFullStackApp;
	}

	public String fallBackFailed() {
		return "Fall Back Failed";
	}

}
