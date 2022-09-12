package de.eberln.schleifenPi.datahandling;

public class OTMessageSetting extends Setting {

	private String message;
	
	public OTMessageSetting(OperationType operationType, BackgroundMode backgroundMode, String image,
			String heading, String color, String message) {
		super(operationType, backgroundMode, image, heading, color);
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
