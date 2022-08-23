package de.eberln.schleifenPi.controller;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.eberln.schleifenPi.datahandling.CountdownData;
import de.eberln.schleifenPi.datahandling.CountdownDataRepository;

@RestController
@RequestMapping("/rest")
public class RestApiController {
	
	@Autowired
	private CountdownDataRepository countdownDataRepository;

	@Value("${application.imagePath}")
	private String imagePath;
	
	@GetMapping("/getCountdownData")
	public ResponseEntity<CountdownData> countdownData() {
		
		return countdownDataRepository.getCountdownData();
		
	}
	
	@GetMapping("/getAvailableLogos")
	public ResponseEntity<ArrayList<String>>getAvailableLogos() {
	
		ArrayList<String> files = new ArrayList<String>();
		
		for(File fileEntry : new File(imagePath).listFiles()) {
			files.add(fileEntry.getName());
		}
		
		if(files.size() > 0) {
			return new ResponseEntity<>(files, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@PostMapping("/setCountdownData")
	public ResponseEntity<Object> setCountdownData(@RequestBody CountdownData countdownData) {
		
		return countdownDataRepository.setCountdownData(countdownData);
		
	}
	
	
	
}
