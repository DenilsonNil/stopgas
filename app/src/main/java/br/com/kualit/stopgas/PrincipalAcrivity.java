package br.com.kualit.stopgas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class PrincipalAcrivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Produto> listaDeProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);
        Util.animarTela(this);

        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.layout_drawer_principal);

        listaDeProdutos = new ArrayList<>();
        listaDeProdutos.add(new Produto(R.drawable.pgas13ultragas, "Gás", 80.00));
        listaDeProdutos.add(new Produto(R.drawable.pagua20, "Água grande", 13.00));
        listaDeProdutos.add(new Produto(R.drawable.pagua15, "Água pequena", 2.00));


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView;
        navigationView = findViewById(R.id.navigation_principal);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.rview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ProductAdapter(listaDeProdutos);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    public void adicionarItemNoCarrinho(View view) {
        int itemPosition = view.getId();
        Carrinho.listaDeProdutos.add(listaDeProdutos.get(itemPosition));
        Log.i("gas", "Valor total => " + Double.toString(Carrinho.getValorTotal()));

        Toast.makeText(this, "Valor total => " + Double.toString(Carrinho.getValorTotal()), Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.opt_menu_car) {

            if (Carrinho.listaDeProdutos.isEmpty()) {
                Toast.makeText(this, "O carrinho está vazio", Toast.LENGTH_LONG).show();
                return false;
            }else{
                Intent intent = new Intent(this, CarrinhoActivity.class);
                startActivity(intent);
                return true;
            }

        }


        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.opt_menu_quemsomos:
                break;
            case R.id.opt_menu_galeria:
                Intent intentGalery = new Intent(this, GaleriaActivity.class);
                startActivity(intentGalery);
                break;
            case R.id.opt_menu_localizacao:
                Intent intentLocale = new Intent(this, MapsActivity.class);
                startActivity(intentLocale);
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
