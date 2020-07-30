package com.rm.kafka.KafkaConfig.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rm.kafka.KafkaConfig.bean.FileAlert;

@RestController
public class KafkaController {

	@Autowired
	private KafkaTemplate<String, FileAlert> kafkaTemplate;
	
	@GetMapping("/healthCheck")
	public String healthCheck() {
		return "OK";
	}
	
	@PostMapping("/produceMessage")
	public FileAlert produceMessage(@RequestBody FileAlert fileAlert) {
		System.out.println("Message Produced: "+fileAlert);
		kafkaTemplate.send("file_alert",fileAlert);
		return fileAlert;
	}
}
