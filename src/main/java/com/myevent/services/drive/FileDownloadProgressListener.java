package com.myevent.services.drive;

import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener;

public class FileDownloadProgressListener implements MediaHttpDownloaderProgressListener {

  @Override
  public void progressChanged(MediaHttpDownloader downloader) {
    switch (downloader.getDownloadState()) {
      case MEDIA_IN_PROGRESS:
        DriveView.header2("Download is in progress: " + downloader.getProgress());
        break;
      case MEDIA_COMPLETE:
        DriveView.header2("Download is Complete!");
        break;
    }
  }
}
