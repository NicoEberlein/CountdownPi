package de.eberln.schleifenPi.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.eberln.schleifenPi.datahandling.CountdownData;
import de.eberln.schleifenPi.datahandling.CountdownData.BackgroundMode;
import de.eberln.schleifenPi.datahandling.CountdownData.OperationType;
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
	public ResponseEntity<ArrayList<String>> getAvailableLogos() {

		ArrayList<String> files = new ArrayList<String>();

		for (File fileEntry : new File(imagePath).listFiles()) {
			files.add(fileEntry.getName());
		}

		if (files.size() > 0) {
			return new ResponseEntity<>(files, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}
	
	/*
	public ResponseEntity<Object> setCountdownData(@RequestBody CountdownData countdownData) {

		return countdownDataRepository.setCountdownData(countdownData);

	}*/

	
	@PostMapping(value = "/setCountdownSettings", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> setCountdownData(@RequestParam("operationType") OperationType operationType,
			@RequestParam("backgroundMode") BackgroundMode backgroundMode,
			@RequestParam("image") String image,
			@RequestParam("heading") String heading,
			@RequestParam("datetime") String datetime,
			@RequestParam("message") String message,
			@RequestParam("color") String color) {
		
		
		System.out.println(color);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh:mm");
		Date parsedDate = null;
		try {
			parsedDate = format.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(parsedDate);
		
		return countdownDataRepository.setCountdownData(new CountdownData(operationType, backgroundMode, image, heading, parsedDate, message));
		
	}

}
