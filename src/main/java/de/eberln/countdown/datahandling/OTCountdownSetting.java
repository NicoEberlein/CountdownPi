package de.eberln.countdown.datahandling;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OTCountdownSetting extends Setting {

	private Date date;
	
	public OTCountdownSetting(BackgroundMode backgroundMode, String image,
			String heading, String color, String date) {
		super(backgroundMode, image, heading, color);
		setDate(date);
	}
	
	public OTCountdownSetting() {
		super();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(String dateString) {
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh:mm");
			this.date = format.parse(dateString);
		}catch(ParseException e) {
			System.err.println("Error parsing provided date. Please provide string with format 'yyyy-MM-dd-hh:mm'");
		}
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
}
