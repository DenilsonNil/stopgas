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
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.kualit.stopgas.adapter.ProductAdapter;
import br.com.kualit.stopgas.db.AddressDAO;
import br.com.kualit.stopgas.db.PedidoDAO;
import br.com.kualit.stopgas.dialog.DialogContato;
import br.com.kualit.stopgas.model.Address;
import br.com.kualit.stopgas.model.Carrinho;
import br.com.kualit.stopgas.model.FirstAccess;
import br.com.kualit.stopgas.model.Pedido;
import br.com.kualit.stopgas.model.Produto;
import br.com.kualit.stopgas.util.Util;

public class PrincipalAcrivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Produto> listaDeProdutos;

    private List<FirstAccess> firstAccesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.layout_principal );
        Util.animarTela( this );

        Toolbar toolbar;
        toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        drawerLayout = findViewById( R.id.layout_drawer_principal );

        listaDeProdutos = new ArrayList<>();
        listaDeProdutos.add( new Produto( R.drawable.pgas13ultragas, "Gás - 13 Kg", 80.00 ) );
        listaDeProdutos.add( new Produto( R.drawable.pagua20, "Água grande", 13.00 ) );
        listaDeProdutos.add( new Produto( R.drawable.pagua15, "Água pequena", 2.00 ) );


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer );
        drawerLayout.addDrawerListener( toggle );
        toggle.syncState();
        NavigationView navigationView;
        navigationView = findViewById( R.id.navigation_principal );
        navigationView.setNavigationItemSelectedListener( this );


        recyclerView = findViewById( R.id.rview );
        recyclerView.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        adapter = new ProductAdapter( listaDeProdutos );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setAdapter( adapter );


    }


    public void adicionarItemNoCarrinho(View view) {
        int itemPosition = view.getId();
        Carrinho.listaDeProdutos.add( listaDeProdutos.get( itemPosition ) );

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen( GravityCompat.START )) {
            drawerLayout.closeDrawer( GravityCompat.START );
        } else {


            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_toolbar, menu );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.opt_menu_car) {

            if (Carrinho.listaDeProdutos.isEmpty()) {
                Toast.makeText( this, "O carrinho está vazio", Toast.LENGTH_LONG ).show();
                return false;
            } else {
                Intent intent = new Intent( this, CarrinhoActivity.class );
                startActivity( intent );
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
                Intent intentGalery = new Intent( this, GaleriaActivity.class );
                startActivity( intentGalery );
                break;
            case R.id.opt_menu_localizacao:
                Intent intentLocale = new Intent( this, MapsActivity.class );
                startActivity( intentLocale );
                break;
            case R.id.opt_menu_contato:
                DialogContato dialogContato = new DialogContato();
                dialogContato.show( getSupportFragmentManager(), "1" );
                break;
            case R.id.opt_menu_meus_pedidos:
                listarPedidos();
                break;
            case R.id.opt_menu_aterar_endereco:
                verificarSeUsuarioPossuiEndereco();
                break;
            case R.id.opt_menu_sair:
                System.exit( 0 );
                break;
        }


        drawerLayout.closeDrawer( GravityCompat.START );

        return true;
    }

    private void listarPedidos(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        PedidoDAO dao = PedidoDAO.getInstance( this );
        List<Pedido> pedidos = dao.list( auth.getCurrentUser().getEmail()  );
        Iterator<Pedido> iterator = pedidos.iterator();
        while(iterator.hasNext()){
            //Para cada pedido tenho que adicionar sua lista de produtos.
            Pedido pedido = iterator.next();



        }





    }




    private void verificarSeUsuarioPossuiEndereco() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        AddressDAO dao = AddressDAO.getInstance( this );
        List<Address> addresses = dao.list();
        Iterator<Address> iterator = addresses.iterator();
        boolean controle = false;
        while (iterator.hasNext()) {
            Address address = iterator.next();
            controle = address.getUser().equals( auth.getCurrentUser().getEmail() );
            if (controle) {

                Intent intentAlterar = new Intent( this, AlteraAddressActivity.class );
                startActivity( intentAlterar );
                break;

            }

        }

        if (!controle) {

            Intent intentAlterar = new Intent( this, CadAddressActivity.class );
            startActivity( intentAlterar );

        }
    }
}
