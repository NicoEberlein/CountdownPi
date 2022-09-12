package de.eberln.schleifenPi.datahandling;

public abstract class Settings {

	public enum OperationType {
		COUNTDOWN,
		MESSAGE
	}
	
	public enum BackgroundMode {
		ONECOLOR,
		BLURREDIMAGE
	}
	
	private OperationType operationType;
	
	//OT COUNTDOWN,MESSAGE
	private BackgroundMode backgroundMode;
	
	//OT COUNTDOWN,MESSAGE
	private String image;
	
	//OT COUNTDOWN,MESSAGE
	private String heading;
	
	//null when BackgroundMode = BLURREDIMAGE
	private String color;
	
	public Settings(OperationType operationType, BackgroundMode backgroundMode, String image, String heading, String color) {
		
		this.operationType = operationType;
		this.backgroundMode = backgroundMode;
		this.image = image;
		this.heading = heading;
		this.color = color;

	}
	
	public Settings( ) {
		
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public BackgroundMode getBackgroundMode() {
		return backgroundMode;
	}

	public void setBackgroundMode(BackgroundMode backgroundMode) {
		this.backgroundMode = backgroundMode;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}