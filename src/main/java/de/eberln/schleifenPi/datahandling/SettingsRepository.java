package de.eberln.schleifenPi.datahandling;

import org.springframework.http.ResponseEntity;

public interface SettingsRepository {

	public ResponseEntity<Object> saveCountdownData(Settings countdownData);
	
	public ResponseEntity<Settings> readCountdownData();
	
}
