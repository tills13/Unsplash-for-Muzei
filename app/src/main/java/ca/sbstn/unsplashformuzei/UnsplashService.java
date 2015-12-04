package ca.sbstn.unsplashformuzei;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by tills13 on 2015-12-03.
 */
public class UnsplashService {
    public static class Photo {
        public String url;
        public String smallUrl;
        public String color;
        public int id;
        public Author author;
        public int height;
        public int width;

        public Photo(int id, String url, String smallUrl, String color, Author author, int height, int width) {
            this.url = url;
            this.smallUrl = smallUrl;
            this.color = color;
            this.id = id;
            this.author = author;
            this.height = height;
            this.width = width;
        }
    }

    public static class Author {
        public String name;
        public String url;

        public Author(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    public interface UnsplashAPI {
        @GET("random")
        Call<List<Photo>> random();
    }
}
