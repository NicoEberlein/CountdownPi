package de.eberln.schleifenPi.datahandling;

public class Settings {

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
	
}