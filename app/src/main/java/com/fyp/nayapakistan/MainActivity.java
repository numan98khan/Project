package com.fyp.nayapakistan;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout ;
    ActionBarDrawerToggle actionBarDrawerToggle ;
    NavigationView navigationView ;
    MaterialSearchView materialSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
