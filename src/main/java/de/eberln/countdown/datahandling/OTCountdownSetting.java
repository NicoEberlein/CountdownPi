package de.eberln.countdown.datahandling;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OTCountdownSetting extends Setting {

	private Date date;
	

	@JsonCreator
	public OTCountdownSetting(
		@JsonProperty("backgroundMode") BackgroundMode backgroundMode,
		@JsonProperty("image") String image,
		@JsonProperty("heading") String heading,
		@JsonProperty("color") String color,
		@JsonProperty("datetime") String datetime) {
			
		super(backgroundMode, image, heading, color);
		setDate(datetime);
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
	
}
