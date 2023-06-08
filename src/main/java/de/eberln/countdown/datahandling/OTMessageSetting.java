package de.eberln.countdown.datahandling;

public class OTMessageSetting extends Setting {

	private String message;
	
	public OTMessageSetting(BackgroundMode backgroundMode, String image,
			String heading, String color, String message) {
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
