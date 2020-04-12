package br.com.kualit.stopgas.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "stopgasdatabase";
    private static final int DB_VERSION = 1;

    private static final String SQL_DROP_MAIL_PASS = "DROP TABLE IF EXISTS " + UserMailPassContract.TABLE_NAME;
    private static final String SQL_CREATE_MAIL_PASS = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL, %s TEXT NOT NULL)",
            UserMailPassContract.TABLE_NAME,
            UserMailPassContract.Columns._ID,
            UserMailPassContract.Columns.MAIL,
            UserMailPassContract.Columns.PASS);



    private static final String SQL_DROP_ADDRESS = "DROP TABLE IF EXISTS " + AddressContract.TABLE_NAME;
    private static final String SQL_CREATE_ADDRESS = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL)",
            AddressContract.TABLE_NAME,
            AddressContract.Columns._ID,
            AddressContract.Columns.USER,
            AddressContract.Columns.NAME,
            AddressContract.Columns.ADDRESS,
            AddressContract.Columns.TEL,
            AddressContract.Columns.BAIRRO,
            AddressContract.Columns.CITY);

    private static final String SQL_CREATE_PEDIDO = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL)",
            PedidoContract.TABLE_NAME,
            PedidoContract.Columns._ID,
            PedidoContract.Columns.IDENT,
            PedidoContract.Columns.USER,
            PedidoContract.Columns.ADDRESS,
            PedidoContract.Columns.DATA,
            PedidoContract.Columns.TIPO_PAGAMENTO);


    private static final String SQL_CREATE_PEDIDO_PRODUTO = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL)",
            PedidoProdutoContract.TABLE_NAME,
            PedidoProdutoContract.Columns._ID,
            PedidoProdutoContract.Columns.PEDIDO_ID,
            PedidoProdutoContract.Columns.PRODUTO);

    private static DBHelper instance;

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }
    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE_MAIL_PASS);
        db.execSQL(SQL_CREATE_ADDRESS);
        db.execSQL( SQL_CREATE_PEDIDO );
        db.execSQL( SQL_CREATE_PEDIDO_PRODUTO );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
