package de.eberln.countdown.datahandling;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class SettingsRepositoryFilesystemImpl implements SettingsRepository{

	@Value("${application.pathToCountdownSettings:countdown-settings.txt}")
	private String pathToCountdownSettings;
	
	@Autowired
	private ObjectMapper mapper;
	
	public void saveCountdownSettings(Setting countdownSettings) throws IOException {
		
		mapper.writeValue(new File(pathToCountdownSettings), countdownSettings);
		
	}
	
	public Setting readCountdownSettings() throws IOException{
		
		return mapper.readValue(new File(pathToCountdownSettings), Setting.class);
		
	}
	
}
