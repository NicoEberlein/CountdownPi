package de.eberln.schleifenPi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CountdownController {

	@RequestMapping("/countdown")
	public String countdown() {
		return "countdown";
	}
	
	@RequestMapping("/setCountdownSettings")
	public String countdownSettings() {
		return "set-countdown-settings";
	}
	
}
