package ca.sbstn.unsplashformuzei.command;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.google.android.apps.muzei.api.Artwork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadArtworkCommand {
	public static final int COMMAND_ID_DOWNLOAD_ARTWORK = 1010;

	public static void downloadArtwork(Context context, Artwork artwork) {
		Uri uri = artwork.getImageUri();
		String url = uri.toString(); // ?
		String path = uri.getPath();
		String filename = path.substring(Math.max(path.lastIndexOf("/"), 0) + 1);

		Pattern pattern = Pattern.compile("([^.]+)(?:\\.(jpg|jpeg|png|gif))?", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(filename);


		String extension = matcher.matches() ? matcher.group(2) : "jpg";
        filename = (matcher.matches() ? matcher.group(1) : "unsplash") + "." + extension;

		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		request.setDescription(artwork.getTitle());
		request.setTitle(filename);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			request.allowScanningByMediaScanner();
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		}

		request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

		// get download service and enqueue file
		DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		manager.enqueue(request);
	}
}