package de.eberln.schleifenPi.datahandling;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class SettingsRepositoryFilesystemImpl implements SettingsRepository{

	@Value("${application.pathToCountdownSettings}")
	private String pathToCountdownSettings;
	
	@Autowired
	private ObjectMapper mapper;
	
	public void saveCountdownSettings(Setting countdownSettings) throws IOException {
		
		mapper.writeValue(new File(pathToCountdownSettings), countdownSettings);

		
	}
	
	public Setting readCountdownSettings() throws IOException{
		
		Setting settings = mapper.readValue(new File(pathToCountdownSettings), Setting.class);
		return settings;
		
	}
	
}
