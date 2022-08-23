package de.eberln.schleifenPi.datahandling;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CountdownData {

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
	@NotBlank
	@NotNull
	private String image;
	
	//OT COUNTDOWN,MESSAGE
	@NotBlank
	@NotNull
	private String heading;
	
	//OT COUNTDOWN
	private Date date;
	
	//OT MESSAGE
	private String message;

	public CountdownData() {
		
	}
	
	public CountdownData(OperationType operationType, BackgroundMode backgroundMode, String image, String heading,
			Date date, String message) {
		super();
		this.operationType = operationType;
		this.backgroundMode = backgroundMode;
		this.image = image;
		this.heading = heading;
		this.date = date;
		this.message = message;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
