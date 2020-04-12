package br.com.kualit.stopgas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.kualit.stopgas.model.Address;

public class AddressDAO {


    private static AddressDAO instance;

    private SQLiteDatabase database;

    private AddressDAO(Context context) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        database = dbHelper.getWritableDatabase();
    }

    public static AddressDAO getInstance(Context context) {
        if (instance == null) {
            instance = new AddressDAO(context.getApplicationContext());
        }
        return instance;
    }

    public List<Address> list() {
        String[] columns = {
                AddressContract.Columns._ID,
                AddressContract.Columns.USER,
                AddressContract.Columns.NAME,
                AddressContract.Columns.ADDRESS,
                AddressContract.Columns.TEL,
                AddressContract.Columns.BAIRRO,
                AddressContract.Columns.CITY
        };


        List<Address> addressList = new ArrayList<>();


        try (Cursor c = database.query(AddressContract.TABLE_NAME, columns,
                null, null, null, null, AddressContract.Columns.USER)) {
            if (c.moveToFirst()) {
                do {
                    Address address = AddressDAO.fromCursor(c);
                    addressList.add(address);
                } while (c.moveToNext());
            }
            return addressList;
        }
    }


    private static Address fromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(AddressContract.Columns._ID));
        String user = c.getString(c.getColumnIndex(AddressContract.Columns.USER));
        String name = c.getString(c.getColumnIndex(AddressContract.Columns.NAME));
        String address = c.getString(c.getColumnIndex(AddressContract.Columns.ADDRESS));
        String tel = c.getString(c.getColumnIndex(AddressContract.Columns.TEL));
        String bairro = c.getString(c.getColumnIndex(AddressContract.Columns.BAIRRO));
        String city = c.getString(c.getColumnIndex(AddressContract.Columns.CITY));


        return new Address(user, name, address, tel, bairro, city);
    }



    public Address getOneAddress(String user){
        Address add = null;
        List<Address> addresses = list();
        Iterator<Address> iterator = addresses.iterator();
        while (iterator.hasNext()){
            Address address = iterator.next();
            if(user.equals( address.getUser() )){
                add = address;
                break;
            }
        }
        return add;
    }//




    public void save(Address address) {
        ContentValues values = new ContentValues();
        values.put(AddressContract.Columns.USER, address.getUser());
        values.put(AddressContract.Columns.NAME, address.getName());
        values.put(AddressContract.Columns.ADDRESS, address.getAddress());
        values.put(AddressContract.Columns.TEL, address.getTel());
        values.put(AddressContract.Columns.BAIRRO, address.getBairro());
        values.put(AddressContract.Columns.CITY, address.getCity());

        long id = database.insert(AddressContract.TABLE_NAME, null, values);
        address.setId((int) id);
    }


    public void update(Address address) {
        ContentValues values = new ContentValues();
        values.put(AddressContract.Columns.USER, address.getUser());
        values.put(AddressContract.Columns.NAME, address.getName());
        values.put(AddressContract.Columns.ADDRESS, address.getAddress());
        values.put(AddressContract.Columns.TEL, address.getTel());
        values.put(AddressContract.Columns.BAIRRO, address.getBairro());
        values.put(AddressContract.Columns.CITY, address.getCity());
        database.update(AddressContract.TABLE_NAME, values, AddressContract.Columns.USER+ " = ?", new String[]{String.valueOf(address.getUser())});

    }

    public void delete(Address address) {
        database.delete(AddressContract.TABLE_NAME, AddressContract.Columns._ID + " = ?", new String[]{String.valueOf(address.getId())});

    }

}
