package de.eberln.schleifenPi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.eberln.schleifenPi.datahandling.Settings;
import de.eberln.schleifenPi.datahandling.Settings.BackgroundMode;
import de.eberln.schleifenPi.datahandling.Settings.OperationType;
import de.eberln.schleifenPi.datahandling.SettingsRepository;
import de.eberln.schleifenPi.datahandling.OTCountdownSettings;
import de.eberln.schleifenPi.datahandling.OTMessageSettings;

@RestController
@RequestMapping("/rest")
public class RestApiController {

	@Autowired
	private SettingsRepository countdownDataRepository;

	@Value("${application.imagePath}")
	private String imagePath;

	@GetMapping("/countdownData/get")
	public ResponseEntity<Settings> countdownData() {

		return countdownDataRepository.readCountdownData();

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

	@GetMapping(value = "/getImage/{image}")
	@ResponseBody
	public ResponseEntity<InputStreamResource> getImageWithMediaType(@PathVariable String image)  {
		
		HttpHeaders headers = new HttpHeaders();
		
		if(image.endsWith(".jpg")) {
			headers.setContentType(MediaType.IMAGE_JPEG);
		}else if(image.endsWith(".png")) {
			headers.setContentType(MediaType.IMAGE_PNG);
		}else {
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		
		InputStream in = null;
		
		try {
			in = new FileInputStream(new File(imagePath + image));
		} catch (FileNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<InputStreamResource>(new InputStreamResource(in), headers, HttpStatus.OK);
	}

	@PostMapping(value = "/countdownData/set", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> setCountdownData(@RequestParam("operationType") OperationType operationType,
			@RequestParam("backgroundMode") BackgroundMode backgroundMode, @RequestParam("image") String image,
			@RequestParam("heading") String heading, @RequestParam("datetime") String datetime,
			@RequestParam("message") String message, @RequestParam("color") String color) {
		
		if(operationType == OperationType.COUNTDOWN) {
		
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh:mm");
			Date parsedDate = null;
			try {
				parsedDate = format.parse(datetime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			return countdownDataRepository.saveCountdownData(new OTCountdownSettings(operationType, backgroundMode, image, heading, color, parsedDate));
			
		}else if(operationType == OperationType.MESSAGE) {
		
			return countdownDataRepository.saveCountdownData(new OTMessageSettings(operationType, backgroundMode, image, heading, color, message));
			
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		

	}

}
