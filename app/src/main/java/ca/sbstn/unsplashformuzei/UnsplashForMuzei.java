package ca.sbstn.unsplashformuzei;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;

public class UnsplashForMuzei extends AppCompatActivity {
    public static final int PERMISSION_REQUEST = 1;

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
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            int permissionReadExternal = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
            int permissionWriteExternal = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permissionReadExternal != PackageManager.PERMISSION_GRANTED || permissionWriteExternal != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[] { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        PERMISSION_REQUEST
                );

                NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.cancel(0);
            }
        }

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            this.addPreferencesFromResource(R.xml.preferences);
        }
    }
}
