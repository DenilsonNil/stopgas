package br.com.kualit.stopgas;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class PrincipalAcrivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.layout_drawer_principal);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView;
        navigationView = findViewById(R.id.navigation_principal);
        navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.opt_menu_quemsomos:
                break;
            case R.id.opt_menu_galeria:
                break;
            case R.id.opt_menu_localizacao:
                break;
            case R.id.opt_menu_contato:
                DialogContato dialogContato = new DialogContato();
                dialogContato.show(getSupportFragmentManager(), "1");
            break;
            case R.id.opt_menu_sair:
                System.exit(0);
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
