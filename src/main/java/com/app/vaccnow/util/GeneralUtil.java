package com.app.vaccnow.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class GeneralUtil {
	
	public static ZonedDateTime toZonedDateTime(LocalDateTime ldt) {		 
		ZoneId zoneId = ZoneId.systemDefault();
		return ldt.atZone( zoneId );
	}

}
