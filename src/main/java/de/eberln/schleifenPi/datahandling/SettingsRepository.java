package de.eberln.schleifenPi.datahandling;

import org.springframework.http.ResponseEntity;

public interface CountdownDataRepository {

	public ResponseEntity<Object> saveCountdownData(CountdownData countdownData);
	
	public ResponseEntity<CountdownData> readCountdownData();
	
}
