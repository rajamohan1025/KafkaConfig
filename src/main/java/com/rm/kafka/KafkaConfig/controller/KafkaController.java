package com.rm.kafka.KafkaConfig.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

	@GetMapping("/healthCheck")
	public String healthCheck() {
		return "OK";
	}
}
