package ca.sbstn.unsplashformuzei;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;

public class UnsplashForMuzei extends AppCompatActivity {
    public UnsplashService.UnsplashAPI unsplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_unsplash_for_muzei);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction fmt = this.getSupportFragmentManager().beginTransaction();
        fmt.add(R.id.context_fragment, UnsplashForMuzeiPreferencesFragment.newInstance());
        fmt.commit();
    }

    public static class UnsplashForMuzeiPreferencesFragment extends PreferenceFragmentCompat {
        public UnsplashForMuzeiPreferencesFragment() {}

        public static UnsplashForMuzeiPreferencesFragment newInstance() {
            UnsplashForMuzeiPreferencesFragment fragment = new UnsplashForMuzeiPreferencesFragment();
            return fragment;
        }

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            this.addPreferencesFromResource(R.xml.preferences);
        }
    }
}
