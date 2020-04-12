package br.com.kualit.stopgas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import br.com.kualit.stopgas.adapter.CarrinhoAdapter;
import br.com.kualit.stopgas.db.AddressDAO;
import br.com.kualit.stopgas.db.PedidoDAO;
import br.com.kualit.stopgas.model.Address;
import br.com.kualit.stopgas.model.Carrinho;
import br.com.kualit.stopgas.model.Pedido;
import br.com.kualit.stopgas.util.Util;

public class CarrinhoActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnFinalizarPedido;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String userMail;


    private String[] items = {"Cartão", "Dinheiro"};
    private String opcao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.layout_carrinho );
        NumberFormat nf = NumberFormat.getInstance();
        String precoFormatado = NumberFormat.getCurrencyInstance().format( Carrinho.getValorTotal() );
        TextView txtValorTotal = findViewById( R.id.txtValorcarrinho );
        txtValorTotal.setText( "Valor total do pedido: " + precoFormatado );

        recyclerView = findViewById( R.id.rview_carrinho );
        recyclerView.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        adapter = new CarrinhoAdapter( this );

        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setAdapter( adapter );

        btnFinalizarPedido = findViewById( R.id.btn_finaliza_pedido );
        btnFinalizarPedido.setOnClickListener( this );
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userMail = user.getEmail();

        opcao = "Cartão";



    }


    private void gravarPedidoNoSQLite() {

        String identificador = getIdentificadorPedido();
        String data = getDataAtual();
        AddressDAO adao = AddressDAO.getInstance( this );
        Address endereco = adao.getOneAddress( userMail );

        Pedido pedido = new Pedido(identificador, userMail, endereco.toString(), data, opcao );

        PedidoDAO dao = PedidoDAO.getInstance( this );
        dao.save( pedido, endereco );//Grava internamente no SQLite

        //Chamando o método para gravar no Firebase.
        gravarPedidoNoFirebase( identificador, userMail, endereco.toString(), data );
    }

    private void gravarPedidoNoFirebase(String identificador, String usuario, String endereco, String data){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Pedido pedido = new Pedido( usuario, endereco, data, opcao, Carrinho.listaDeProdutos) ;
        DatabaseReference reference = database.getReference().child( "BD" ).child( "Pedidos" );
        reference.push().setValue( pedido ).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            Log.i("gas", "Gravado no Firebase");

            }
        } );

    }



    private String getDataAtual() {

        Calendar calendar = Calendar.getInstance();
        Date data = calendar.getTime();
        String dataEHoraPedido;
        SimpleDateFormat stf = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
        dataEHoraPedido = stf.format( data );
        return dataEHoraPedido;
    }

    private String getUserAddress() {


        String address = null;
        AddressDAO dao = AddressDAO.getInstance( this );
        List<Address> addresses = dao.list();
        Iterator<Address> iterator = addresses.iterator();

        while (iterator.hasNext()) {
            Address add = iterator.next();
            if (add.getUser().equals( user )) {

                address = add.toString();
                break;
            }
        }

        return address;
    }


    private void abrirDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "Forma de pagamento" );
        builder.setSingleChoiceItems( items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                opcao = items[which];
            }
        } );
        builder.setPositiveButton( "CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               Util.iniciarDialog( CarrinhoActivity.this, "100" );
               gravarPedidoNoSQLite();
               Util.pararDialog();
               Carrinho.listaDeProdutos.clear();
               finish();


            }
        } );

        AlertDialog dialog = builder.create();
        dialog.show();


    }


    public String getIdentificadorPedido() {

        String firebaseUID = user.getUid();
        Calendar calendar = Calendar.getInstance();
        long timeMilis = calendar.getTimeInMillis();
        String numPedido = Long.toString( timeMilis ) + "-" + firebaseUID;
        Log.i( "gas", numPedido );
        return numPedido;
    }

    //Finalizar o pedido de compras.
    @Override
    public void onClick(View v) {

        abrirDialog();


    }
}
