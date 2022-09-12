package de.eberln.schleifenPi.datahandling;

import java.util.Date;

public class OTCountdownSetting extends Setting {

	private Date date;
	
	public OTCountdownSetting(OperationType operationType, BackgroundMode backgroundMode, String image,
			String heading, String color, Date date) {
		super(operationType, backgroundMode, image, heading, color);
		this.date = date;
	}
	
	public OTCountdownSetting() {
		super();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
