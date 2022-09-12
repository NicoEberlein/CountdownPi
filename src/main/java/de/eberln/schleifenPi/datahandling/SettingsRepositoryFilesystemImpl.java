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
public class SettingsRepositoryFilesystemImpl implements SettingsRepository{

	@Value("${application.pathToCountdownData}")
	private String pathToCountdownData;
	
	@Autowired
	private ObjectMapper mapper;
	
	public ResponseEntity<Object> saveCountdownData(Settings countdownData) {
		
		try {
			mapper.writeValue(new File(pathToCountdownData), countdownData);
		}catch(IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	public ResponseEntity<Settings> readCountdownData() {
		
		try {
			
			Settings countdownData = mapper.readValue(new File(pathToCountdownData), Settings.class);
			return new ResponseEntity<Settings>(countdownData, HttpStatus.OK);
			
		}catch(IOException e) {
			
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		}
		
	}
	
}
