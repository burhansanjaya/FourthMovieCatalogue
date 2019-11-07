package com.dicoding.fourthmoviecatalogue;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        /*
            * Beriisi sekumpulan id yang ada didalam menu BottomNavigation
            * Khususnya bagi yang hendak dikonfigurasi App-Barnya supaya terbentuk menu.
            * Bila id tidak ada, maka bisa menambahkan di sini, maka menampilkan tanda panah kembali
         */
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_movie, R.id.navigation_tv, R.id.navigation_favorite_movie, R.id.navigation_favorite_tv)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // Digunakan mengatur judul AppBar agar sesuai dengan fragment yang ditampilkan
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Supaya ButtomNavigation menampilkan Fragment yang sesaui dipilih
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
