package com.rm.kafka.KafkaConfig.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.rm.kafka.KafkaConfig.bean.FileAlert;

@EnableKafka
@Configuration
public class KafkaConfiguration {

	@Autowired
	private Environment env;
	
	@Value("$kafka.bootstrap-servers")
	private String bootstrapServers;
	
	@Bean
	public KafkaTemplate<String, FileAlert> kafkaTemplate(){
	    return new KafkaTemplate<>(producerFactory());
	}
	
	@Bean
	public ProducerFactory<String, FileAlert> producerFactory(){
	    Map<String, Object> config = new HashMap<>();

	    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.bootstrap-servers"));
	    config.put(ProducerConfig.ACKS_CONFIG, "all");
	    config.put(ProducerConfig.RETRIES_CONFIG, 0);
	    config.put(ProducerConfig.BATCH_SIZE_CONFIG, 1000);
	    config.put(ProducerConfig.LINGER_MS_CONFIG, 1);
	    config.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
	    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

	    return new DefaultKafkaProducerFactory<String, FileAlert>(config);
	}
	
	
	@Bean
	public ConsumerFactory<String, FileAlert> fileAlertKafkaConsumerFactory(){
		HashMap<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("kafka.bootstrap-servers"));
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "FileAlert");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
		config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, FileAlert.class);
		
		
		return new DefaultKafkaConsumerFactory<String, FileAlert>(config);
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, FileAlert> fileAlertKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, FileAlert> factory = new ConcurrentKafkaListenerContainerFactory<String, FileAlert>();
		factory.setConsumerFactory(fileAlertKafkaConsumerFactory());
		return factory;
	}
	
}
