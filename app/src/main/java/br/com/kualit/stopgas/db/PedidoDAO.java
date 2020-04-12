package br.com.kualit.stopgas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.kualit.stopgas.model.Address;
import br.com.kualit.stopgas.model.Carrinho;
import br.com.kualit.stopgas.model.Pedido;
import br.com.kualit.stopgas.model.Produto;

public class PedidoDAO {


    private static PedidoDAO instance;

    private SQLiteDatabase database;

    private PedidoDAO(Context context) {
        DBHelper dbHelper = DBHelper.getInstance( context );
        database = dbHelper.getWritableDatabase();
    }

    public static PedidoDAO getInstance(Context context) {
        if (instance == null) {
            instance = new PedidoDAO( context.getApplicationContext() );
        }
        return instance;
    }


    //Lista todos os pedidos de um usuário específico. Veja a Where-clause.
    public List<Pedido> list(String user) {
        String[] columns = {
                PedidoContract.Columns._ID,
                PedidoContract.Columns.IDENT,
                PedidoContract.Columns.USER,
                PedidoContract.Columns.ADDRESS,
                PedidoContract.Columns.DATA,
                PedidoContract.Columns.TIPO_PAGAMENTO
        };


        List<Pedido> pedidoList = new ArrayList<>();

        String whereClause = "user = '" + user + "'";
        try (Cursor c = database.query( PedidoContract.TABLE_NAME, columns,
                whereClause, null, null, null, null )) {
            if (c.moveToFirst()) {
                do {
                    Pedido pedido = PedidoDAO.fromCursor( c );
                    pedidoList.add( pedido );
                } while (c.moveToNext());
            }
            return pedidoList;
        }
    }


    private static Pedido fromCursor(Cursor c) {
        int id = c.getInt( c.getColumnIndex( PedidoContract.Columns._ID ) );
        String ident = c.getString( c.getColumnIndex( PedidoContract.Columns.IDENT ) );
        String user = c.getString( c.getColumnIndex( PedidoContract.Columns.USER ) );
        String address = c.getString( c.getColumnIndex( PedidoContract.Columns.ADDRESS ) );
        String data = c.getString( c.getColumnIndex( PedidoContract.Columns.DATA ) );
        String tipoPagto = c.getString( c.getColumnIndex( PedidoContract.Columns.TIPO_PAGAMENTO ) );


        return new Pedido( id, ident, user, address, data, tipoPagto );
    }


    //lISTAGEM DE PRODUTOS POR PEDIDO.

    public List<Produto> listProdutoPedido(int idPedido) {
        String[] columns = {
                PedidoProdutoContract.Columns._ID,
                PedidoProdutoContract.Columns.PEDIDO_ID,
                PedidoProdutoContract.Columns.PRODUTO

        };


        List<Produto> produtoList = new ArrayList<>();

        String whereClause = PedidoProdutoContract.Columns.PEDIDO_ID + " = " +  idPedido;


        try (Cursor c = database.query( PedidoProdutoContract.TABLE_NAME, columns,
                whereClause, null, null, null, null )) {
            if (c.moveToFirst()) {
                do {
                    Produto produto = PedidoDAO.pedidoFromCursor( c );
                    produtoList.add( produto );
                } while (c.moveToNext());
            }
            return produtoList;
        }
    }


    private static Produto pedidoFromCursor(Cursor c) {

        String idPedido = c.getString( c.getColumnIndex( PedidoProdutoContract.Columns.PEDIDO_ID ) );
        String  nomeProduto = c.getString( c.getColumnIndex( PedidoProdutoContract.Columns.PRODUTO ) );



        return new Produto( idPedido, nomeProduto );
    }


    public void save(Pedido pedido, Address address) {
        ContentValues values = new ContentValues();
        values.put( PedidoContract.Columns.IDENT, pedido.getIdentificador() );
        values.put( PedidoContract.Columns.USER, pedido.getUserMail() );
        values.put( PedidoContract.Columns.ADDRESS, address.toString() );
        values.put( PedidoContract.Columns.DATA, pedido.getData() );
        values.put( PedidoContract.Columns.TIPO_PAGAMENTO, pedido.getTipoPagamento() );
        long id = database.insert( PedidoContract.TABLE_NAME, null, values );
        address.setId( (int) id );
        savePedidoProduct( id );
    }

    private void savePedidoProduct(long pedidoId) {
        ContentValues values = new ContentValues();
        Iterator<Produto> iterator = Carrinho.listaDeProdutos.iterator();
        while (iterator.hasNext()) {
            Produto produtoCorrente = iterator.next();
            values.put( "id_pedido", pedidoId );
            values.put( "produto", produtoCorrente.getDescricao() );


            database.insert( "pedido_produto", null, values );
            Log.i( "gas", "Gravado Produto-PEDIDO" );


        }


    }

}
