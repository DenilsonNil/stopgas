package br.com.kualit.stopgas;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.CarrinhoHolder> {

    private AppCompatActivity activity;

    public CarrinhoAdapter(AppCompatActivity activity) {
        this.activity = activity;

    }

    @NonNull
    @Override
    public CarrinhoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item_carrinho, parent, false);
        CarrinhoHolder holder = new CarrinhoHolder(view);


        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final CarrinhoHolder holder, final int position) {


        Produto produtoCorrente = Carrinho.listaDeProdutos.get(position);
        NumberFormat nf = NumberFormat.getInstance();
        String precoFormatado = NumberFormat.getCurrencyInstance().format(produtoCorrente.getPreco());


        holder.imagemProduto.setImageResource(produtoCorrente.getImagem());
        holder.descricao.setText(produtoCorrente.getDescricao());
        holder.valor.setText(precoFormatado);
        holder.botaoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Carrinho.listaDeProdutos.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());



                TextView textView = activity.findViewById(R.id.txtValorcarrinho);
                NumberFormat nf = NumberFormat.getInstance();
                String precoFormatado = NumberFormat.getCurrencyInstance().format(Carrinho.getValorTotal());
                textView.setText("Valor total do pedido: " + precoFormatado);
                if(Carrinho.listaDeProdutos.isEmpty()){
                    activity.finish();
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        return Carrinho.listaDeProdutos.size();
    }


    public static class CarrinhoHolder extends RecyclerView.ViewHolder {

        public ImageView imagemProduto;
        public Button botaoDelete;
        public TextView descricao;
        public TextView valor;

        public CarrinhoHolder(@NonNull View itemView) {
            super(itemView);
            imagemProduto = itemView.findViewById(R.id.img_product_carrinho);
            botaoDelete = itemView.findViewById(R.id.btn_delete_car);
            descricao = itemView.findViewById(R.id.txt_desc_carrinho);
            valor = itemView.findViewById(R.id.txt_valor_carrinho);


        }


    }


}
