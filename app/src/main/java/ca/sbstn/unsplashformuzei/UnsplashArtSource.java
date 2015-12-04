package ca.sbstn.unsplashformuzei;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by tills13 on 2015-12-03.
 */
public class UnsplashArtSource extends RemoteMuzeiArtSource {
    public static final String SOURCE_NAME = "UnsplashArtSource";

    public UnsplashService.UnsplashAPI unsplash;
    public SharedPreferences sharedPreferences;

    public UnsplashArtSource() throws IOException {
        super(SOURCE_NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setUserCommands(BUILTIN_COMMAND_ID_NEXT_ARTWORK);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://splashapi.sbstn.ca/api/v1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.unsplash = retrofit.create(UnsplashService.UnsplashAPI.class);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onTryUpdate(int reason) throws RetryException {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        // only download over WiFi
        if (activeNetwork.getType() != ConnectivityManager.TYPE_WIFI) {
            return;
        }

        Call<List<UnsplashService.Photo>> call = this.unsplash.random();

        call.enqueue(new Callback<List<UnsplashService.Photo>>() {
            @Override
            public void onResponse(Response<List<UnsplashService.Photo>> response, Retrofit retrofit) {
                List<UnsplashService.Photo> photos = response.body();

                UnsplashService.Photo photo = photos.get(0);

                publishArtwork(
                    new Artwork.Builder()
                        .title(photo.author.name)
                        .byline(photo.author.url)
                        .imageUri(Uri.parse(photo.url))
                        .build()
                );
            }

            @Override
            public void onFailure(Throwable t) {}
        });
    }
}
