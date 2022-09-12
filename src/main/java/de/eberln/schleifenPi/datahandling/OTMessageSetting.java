package de.eberln.schleifenPi.datahandling;

public class OTMessageSettings extends Settings {

	private String message;
	
	public OTMessageSettings(OperationType operationType, BackgroundMode backgroundMode, String image,
			String heading, String color, String message) {
		super(operationType, backgroundMode, image, heading, color);
		this.message = message;
	}

	public OTMessageSettings() {
		super();
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
}
