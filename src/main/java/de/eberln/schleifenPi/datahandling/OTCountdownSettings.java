package de.eberln.schleifenPi.datahandling;

import java.util.Date;

public class OTCountdownSettings extends Settings {

	private Date date;
	
	public OTCountdownSettings(OperationType operationType, BackgroundMode backgroundMode, String image,
			String heading, String color, Date date) {
		super(operationType, backgroundMode, image, heading, color);
		this.date = date;
	}
	
}
