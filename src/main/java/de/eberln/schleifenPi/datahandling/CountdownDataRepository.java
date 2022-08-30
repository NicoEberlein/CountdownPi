package de.eberln.schleifenPi.datahandling;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class CountdownDataRepository {

	@Value("${application.pathToCountdownData}")
	private String pathToCountdownData;
	
	@Autowired
	private ObjectMapper mapper;
	
	public ResponseEntity<Object> saveCountdownData(CountdownData countdownData) {
		
		try {
			mapper.writeValue(new File(pathToCountdownData), countdownData);
		}catch(IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	public ResponseEntity<CountdownData> readCountdownData() {
		
		try {
			
			CountdownData countdownData = mapper.readValue(new File(pathToCountdownData), CountdownData.class);
			return new ResponseEntity<CountdownData>(countdownData, HttpStatus.OK);
			
		}catch(IOException e) {
			
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		}
		
	}
	
}
