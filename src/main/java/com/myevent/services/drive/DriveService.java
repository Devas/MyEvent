package com.myevent.services.drive;

import com.myevent.services.AuthorizationService;
import com.myevent.services.Credentials;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DriveService extends AuthorizationService {

    private final String DRIVE_DIR = "storage";
    private final String FILE_TO_UPLOAD = "photo.jpg";
    //    private final java.io.File fileToUpload = new java.io.File(DRIVE_DIR + "/" + FILE_TO_UPLOAD);
    private final java.io.File fileToUpload = new java.io.File("src/main/resources/photo.jpg");
    private Drive drive;

    public void run() {

        Preconditions.checkArgument(
                !FILE_TO_UPLOAD.startsWith("Enter ") && !DRIVE_DIR.startsWith("Enter "),
                "Please enter the upload file path and download directory in %s", DriveService.class);

        try {
            dataStoreFactory = new FileDataStoreFactory(new java.io.File(Credentials.DRIVE_CREDENTIALS_DIR));

            Credential credential = authorize();

            // set up the global Drive instance
            drive = new Drive.Builder(httpTransport, jsonFactory, credential).
                    setApplicationName(Credentials.APP_NAME).
                    build();


            DriveView.header1("Starting Resumable Media Upload");
            File uploadedFile = uploadFile(false);

            DriveView.header1("Updating Uploaded File Name");
            File updatedFile = updateFileWithTestSuffix(uploadedFile.getId());

            DriveView.header1("Starting Resumable Media Download");
            downloadFile(false, updatedFile);

            DriveView.header1("Starting Simple Media Upload");
            uploadedFile = uploadFile(true);

            DriveView.header1("Starting Simple Media Download");
            downloadFile(true, uploadedFile);

            DriveView.header1("Success!");

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Uploads a file using either resumable or direct media upload.
     */
    private File uploadFile(boolean useDirectUpload) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setTitle(fileToUpload.getName());

        FileContent mediaContent = new FileContent("image/jpeg", fileToUpload);

        Drive.Files.Insert insert = drive.files().insert(fileMetadata, mediaContent);
        MediaHttpUploader uploader = insert.getMediaHttpUploader();
        uploader.setDirectUploadEnabled(useDirectUpload);
        uploader.setProgressListener(new FileUploadProgressListener());
        return insert.execute();
    }

    /**
     * Updates the name of the uploaded file to have a "drivetest-" prefix.
     */
    private File updateFileWithTestSuffix(String id) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setTitle("drivetest-" + fileToUpload.getName());

        Drive.Files.Update update = drive.files().update(id, fileMetadata);
        return update.execute();
    }

    /**
     * Downloads a file using either resumable or direct media download.
     */
    private void downloadFile(boolean useDirectDownload, File uploadedFile) throws IOException {
        // create parent directory (if necessary)
        java.io.File parentDir = new java.io.File(DRIVE_DIR);
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new IOException("Unable to create parent directory");
        }
        OutputStream out = new FileOutputStream(new java.io.File(parentDir, uploadedFile.getTitle()));

        MediaHttpDownloader downloader =
                new MediaHttpDownloader(httpTransport, drive.getRequestFactory().getInitializer());
        downloader.setDirectDownloadEnabled(useDirectDownload);
        downloader.setProgressListener(new FileDownloadProgressListener());
        downloader.download(new GenericUrl(uploadedFile.getDownloadUrl()), out);
    }
}
