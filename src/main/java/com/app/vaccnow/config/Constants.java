package com.app.vaccnow.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String ANONYMOUS_USER = "anonymoususer";
    
    public static final String DEFAULT_TIMESLOT_STATUS = "Active";
    public static final String APPROVED_TIMESLOT_STATUS = "Approved";
    
    public static final String START_WORKING_HOURS_KEY = "startWorkingHours";
    public static final String END_WORKING_HOURS_KEY = "endWorkingHours";
    public static final String TIMESLOT_UNIT_KEY = "timeslotUnit";
    public static final String TIMESLOT_UNIT_HOUR = "H";
    public static final String TIMESLOT_UNIT_MIN = "M";
    public static final String TIMESLOT_DURATION = "timeslotDuration";

    private Constants() {
    }
}
