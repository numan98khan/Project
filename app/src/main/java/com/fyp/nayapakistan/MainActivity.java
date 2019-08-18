package com.fyp.nayapakistan;

import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout ;
    ActionBarDrawerToggle actionBarDrawerToggle ;
    NavigationView navigationView ;
    MaterialSearchView materialSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_main);
        final Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        drawerLayout = findViewById(R.id.main);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this , drawerLayout , R.string.open , R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black);
         materialSearchView = findViewById(R.id.searchview);
         materialSearchView.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MapFragment())
                .commit();
        navigationView = findViewById(R.id.nevview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.Stock:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new StockFragment())
                                .commit();
                        myToolbar.setTitle("Store");
                        myToolbar.setBackgroundColor(getResources().getColor(R.color.blue));
                        myToolbar.setTitleTextColor(getResources().getColor(R.color.white));
                        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
                        materialSearchView.setVisibility(View.VISIBLE);

                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true ;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu , menu);
        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);
        return  true ;
    }
}
