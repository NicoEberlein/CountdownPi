package de.eberln.countdown.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CountdownController {

	@RequestMapping("/countdown")
	public String countdown() {
		return "countdown";
	}
	
	@GetMapping("/setCountdownSettings")
	public String countdownSettings() {
		return "set-countdown-settings";
	}
	
}
