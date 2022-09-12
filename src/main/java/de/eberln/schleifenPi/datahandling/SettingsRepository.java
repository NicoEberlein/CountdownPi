package de.eberln.schleifenPi.datahandling;

import java.io.IOException;

public interface SettingsRepository {

	public void saveCountdownSettings(Setting countdownSettings) throws IOException;
	
	public Setting readCountdownSettings() throws IOException;
	
}
