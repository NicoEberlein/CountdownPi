package de.eberln.countdown.datahandling;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OTMessageSetting extends Setting {

	private String message;
	
	@JsonCreator
	public OTMessageSetting(
		@JsonProperty("backgroundMode") BackgroundMode backgroundMode,
		@JsonProperty("image") String image,
		@JsonProperty("heading") String heading,
		@JsonProperty("color") String color,
		@JsonProperty("message") String message) {

		super(backgroundMode, image, heading, color);
		this.message = message;
	}

	public OTMessageSetting() {
		super();
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
}
