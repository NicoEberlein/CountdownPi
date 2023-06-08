package de.eberln.countdown.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.eberln.countdown.datahandling.OTCountdownSetting;
import de.eberln.countdown.datahandling.OTMessageSetting;
import de.eberln.countdown.datahandling.Setting;
import de.eberln.countdown.datahandling.SettingsRepository;
import de.eberln.countdown.datahandling.Setting.BackgroundMode;
import de.eberln.countdown.datahandling.Setting.OperationType;

@RestController
@RequestMapping("/rest")
public class RestApiController {

	@Autowired
	private SettingsRepository settingsRepository;

	@Value("${application.imagePath:images/}")
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
			throw new NullPointerException("No image folder found on server");
		}

		
		return ResponseEntity.ok(files);

	}

	@CrossOrigin
	@GetMapping("/getImage/{image}")
	@ResponseBody
	public ResponseEntity<InputStreamResource> getImageWithMediaType(@PathVariable String image) throws IllegalArgumentException, FileNotFoundException  {
		
		HttpHeaders headers = new HttpHeaders();
		
		if(image.endsWith(".jpg") || image.endsWith(".jpeg")) {
			headers.setContentType(MediaType.IMAGE_JPEG);
		}else if(image.endsWith(".png")) {
			headers.setContentType(MediaType.IMAGE_PNG);
		}else {
			
			throw new IllegalArgumentException("No suitable file extension provided. Allowed filename suffixes are jpg, jpeg and png");
			
		}
		
		InputStream in = null;
		
		
		try {
			in = new FileInputStream(new File(imagePath + image));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FileNotFoundException("The image " + image + " was not found on the server");
		}
		
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}
	

	@CrossOrigin
	@GetMapping("/countdownData")
	public ResponseEntity<Setting> countdownData() throws IOException {

		Setting settings = null;
		try {
			settings = settingsRepository.readCountdownSettings();
		}catch (IOException e) {
			throw new IOException("An error occured while reading countdownSettings from repository");
		}
		
		return ResponseEntity.ok(settings);

	}
	
	@PostMapping(value = "/countdownData", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Setting> setCountdownData(@RequestBody Setting settings) throws IOException {

		System.out.println(settings);

		settingsRepository.saveCountdownSettings(settings);

		return ResponseEntity.ok(settings);

		

	}

}
