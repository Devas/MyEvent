package com.myevent.services;

public class Credentials {

    public static final String APP_NAME = "GAPI";
    public static final String API_KEY = "AIzaSyARk50yaN-Vb9mFEhK47WGvZkCUCiECO1I";

    public static final String CLIENT_SECRET_JSON = "/client_secret_installed.json";
//    public static final String CLIENT_SECRET_JSON = "/client_secret_web.json";

    //    public static final String CREDENTIALS_DIR2 = System.getProperty("user.home") + CREDENTIALS_DIR;
    private static final String PARENT_CREDENTIALS_DIR = ".credentials/"; // root directory for stored credentials
    public static final String CALENDAR_CREDENTIALS_DIR = PARENT_CREDENTIALS_DIR + "calendar";
    public static final String DRIVE_CREDENTIALS_DIR = PARENT_CREDENTIALS_DIR + "drive";
    public static final String OAUTH2_CREDENTIALS_DIR = PARENT_CREDENTIALS_DIR + "oauth2";
    public static final String PLUS_CREDENTIALS_DIR = PARENT_CREDENTIALS_DIR + "plus";
    public static final String SHEETS_CREDENTIALS_DIR = PARENT_CREDENTIALS_DIR + "sheets";

    static {
        check();
    }

    private static void check() {
        if (APP_NAME.equals("")) {
            System.err.println("APP_NAME not specified");
        }
        if (API_KEY.equals("")) {
            System.err.println("API_KEY not specified");
        }
        if (CLIENT_SECRET_JSON.equals("")) {
            System.err.println("CLIENT_SECRET_JSON not specified");
        }

        if (PARENT_CREDENTIALS_DIR.equals("")) {
            System.err.println("PARENT_CREDENTIALS_DIR not specified");
        }
        if (CALENDAR_CREDENTIALS_DIR.equals("")) {
            System.err.println("CALENDAR_CREDENTIALS_DIR not specified");
        }
        if (DRIVE_CREDENTIALS_DIR.equals("")) {
            System.err.println("DRIVE_CREDENTIALS_DIR not specified");
        }
        if (SHEETS_CREDENTIALS_DIR.equals("")) {
            System.err.println("SHEETS_CREDENTIALS_DIR not specified");
        }
    }

}
