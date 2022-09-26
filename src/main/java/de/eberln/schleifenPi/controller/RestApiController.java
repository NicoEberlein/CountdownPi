package de.eberln.schleifenPi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.eberln.schleifenPi.datahandling.Setting;
import de.eberln.schleifenPi.datahandling.Setting.BackgroundMode;
import de.eberln.schleifenPi.datahandling.Setting.OperationType;
import de.eberln.schleifenPi.datahandling.SettingsRepository;
import de.eberln.schleifenPi.datahandling.OTCountdownSetting;
import de.eberln.schleifenPi.datahandling.OTMessageSetting;

@RestController
@RequestMapping("/rest")
public class RestApiController {

	@Autowired
	private SettingsRepository settingsRepository;

	@Value("${application.imagePath}")
	private String imagePath;
	

	@CrossOrigin
	@GetMapping("/getAvailableLogos")
	public ResponseEntity<ArrayList<String>> getAvailableLogos() {

		ArrayList<String> files = new ArrayList<String>();

		try {
			
			for (File fileEntry : new File(imagePath).listFiles()) {
				files.add(fileEntry.getName());
			}
			
		}catch(NullPointerException e) {
			
			return ResponseEntity.noContent().build();
			
		}
		
		return ResponseEntity.ok(files);

	}

	@CrossOrigin
	@GetMapping("/getImage/{image}")
	@ResponseBody
	public ResponseEntity<InputStreamResource> getImageWithMediaType(@PathVariable String image)  {
		
		HttpHeaders headers = new HttpHeaders();
		
		if(image.endsWith(".jpg")) {
			headers.setContentType(MediaType.IMAGE_JPEG);
		}else if(image.endsWith(".png")) {
			headers.setContentType(MediaType.IMAGE_PNG);
		}else {
			
			return ResponseEntity.badRequest().build();
			
		}
		
		InputStream in = null;
		
		try {
			in = new FileInputStream(new File(imagePath + image));
		} catch (FileNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}

	@CrossOrigin
	@GetMapping("/countdownData")
	public ResponseEntity<Setting> countdownData() {

		Setting settings = null;
		try {
			settings = settingsRepository.readCountdownSettings();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
		
		return ResponseEntity.ok(settings);

	}
	
	@PostMapping(value = "/countdownData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
				return ResponseEntity.badRequest().headers(new HttpHeaders()).body("Error parsing provided date");
			}
			
			try {
				settingsRepository.saveCountdownSettings(new OTCountdownSetting(operationType, backgroundMode, image, heading, color, parsedDate));
			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().build();
			}
			
		}else if(operationType == OperationType.MESSAGE) {
		
			try {
				settingsRepository.saveCountdownSettings(new OTMessageSetting(operationType, backgroundMode, image, heading, color, message));
			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().build();
			}
			
		}
		
		return ResponseEntity.ok().build();

		

	}

}
