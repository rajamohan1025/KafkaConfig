package com.rm.kafka.KafkaConfig.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.rm.kafka.KafkaConfig.bean.FileAlert;

@Service
public class KafkaConsumer {

	@KafkaListener(topics="file_alert",groupId="FileAlert",containerFactory="fileAlertKafkaListenerContainerFactory")
	public void consumeEvent(FileAlert fileAlert) {
		System.out.println("FileAlert Consumed: "+fileAlert);
	}
}
