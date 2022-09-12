package de.eberln.schleifenPi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

@SpringBootApplication
public class SchleifenPiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchleifenPiApplication.class, args);
	}
	
	private PolymorphicTypeValidator polymorphicTypeValidator() {
		
		return BasicPolymorphicTypeValidator
				.builder()
				.allowIfSubType("de.eberln.schleifenPi.datahandling")
				.allowIfSubType("java.util.Date")
				.build();
		
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.activateDefaultTyping(polymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
		return mapper;
	}

}
