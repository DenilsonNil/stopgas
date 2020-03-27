package br.com.kualit.stopgas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;

public class CarrinhoActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnFinalizarPedido;


    private String[] items = {"Cartão", "Dinheiro"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_carrinho);
        NumberFormat nf = NumberFormat.getInstance();
        String precoFormatado = NumberFormat.getCurrencyInstance().format(Carrinho.getValorTotal());
        TextView txtValorTotal = findViewById(R.id.txtValorcarrinho);
        txtValorTotal.setText("Valor total do pedido: " + precoFormatado);

        recyclerView = findViewById(R.id.rview_carrinho);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CarrinhoAdapter(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        btnFinalizarPedido = findViewById(R.id.btn_finaliza_pedido);
        btnFinalizarPedido.setOnClickListener(this);


    }


    private void abrirDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forma de pagamento");
        builder.setSingleChoiceItems(items, -1, null);
        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    @Override
    public void onClick(View v) {


        abrirDialog();

    }
}
