package br.com.kualit.stopgas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;

import br.com.kualit.stopgas.R;
import br.com.kualit.stopgas.model.Produto;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private List<Produto> produtos;

    public ProductAdapter(List<Produto> produtos) {
        this.produtos = produtos;
    }

    //InnerClass
    public static class ProductHolder extends RecyclerView.ViewHolder {
        public TextView valor;
        public TextView descricao;
        public ImageView foto;
        public Button btn_addCar;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            valor = itemView.findViewById(R.id.txt_valor);
            descricao = itemView.findViewById(R.id.txt_desc);
            foto = itemView.findViewById(R.id.img_product);
            btn_addCar = itemView.findViewById(R.id.btn_add_car);


        }
    }//Fim da Inner Class


    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        ProductHolder holder = new ProductHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Produto produtoCorrente = produtos.get(position);

        NumberFormat nf = NumberFormat.getInstance();
        String precoFormatado = NumberFormat.getCurrencyInstance().format(produtoCorrente.getPreco());
        holder.foto.setImageResource(produtoCorrente.getImagem());
        holder.descricao.setText(produtoCorrente.getDescricao());
        holder.valor.setText("Preço: "+precoFormatado);
        holder.btn_addCar.setId(position);

    }



    @Override
    public int getItemCount() {
        return produtos.size();
    }
}
