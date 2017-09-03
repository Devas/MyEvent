package com.myevent.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.oauth2.Oauth2Scopes;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorizationService {

    protected static HttpTransport httpTransport;
    protected static JsonFactory jsonFactory;
    protected static GoogleClientSecrets clientSecrets;
    protected static FileDataStoreFactory dataStoreFactory;

    private static final List<String> scopes = Arrays.asList(
            CalendarScopes.CALENDAR,
            DriveScopes.DRIVE, DriveScopes.DRIVE_FILE,
            PlusScopes.PLUS_ME,
            SheetsScopes.SPREADSHEETS,
            Oauth2Scopes.USERINFO_PROFILE, Oauth2Scopes.USERINFO_EMAIL
    );

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        jsonFactory = JacksonFactory.getDefaultInstance();
        disableWindowsWarnings();
    }

    /**
     * This method:
     * - loads client's secrets from JSON
     * - validates client_id and client_secret
     * - creates the flow for authorization
     * - creates an authorized Credential object and returns it
     */
    protected static Credential authorize() throws Exception {
        InputStreamReader clientSecretJson = new InputStreamReader(AuthorizationService.class.getResourceAsStream(Credentials.CLIENT_SECRET_JSON));
        clientSecrets = GoogleClientSecrets.load(jsonFactory, clientSecretJson);

        GoogleClientSecrets.Details details = clientSecrets.getDetails();
        if (details.getClientId().equals("")) {
            System.err.println("client_id not specified in client_secret.json");
        }
        if (details.getClientSecret().equals("")) {
            System.err.println("client_secret not specified in client_secret.json");
        }

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, scopes)
                .setDataStoreFactory(dataStoreFactory)
                .setAccessType("offline") // to refresh tokens if user is offline e.g. backups
                .build();

        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * setPermissionsToOwnerOnly() from FileDataStoreFactory works only on Unix without warnings
     * This is temporary workaround of the bug for windows to avoid warnings
     * https://github.com/google/google-http-java-client/issues/315
     */
    private static void disableWindowsWarnings() {
        final Logger buggyWindowsLogger = Logger.getLogger(FileDataStoreFactory.class.getName());
        buggyWindowsLogger.setLevel(Level.SEVERE);
    }

}
