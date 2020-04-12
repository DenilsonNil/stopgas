package br.com.kualit.stopgas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.kualit.stopgas.model.MailPass;

public class UserMailPassDAO {


    private static UserMailPassDAO instance;

    private SQLiteDatabase database;

    private UserMailPassDAO(Context context) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        database = dbHelper.getWritableDatabase();
    }

    public static UserMailPassDAO getInstance(Context context) {
        if (instance == null) {
            instance = new UserMailPassDAO(context.getApplicationContext());
        }
        return instance;
    }

    public List<MailPass> list() {
        String[] columns = {UserMailPassContract.Columns._ID,
                UserMailPassContract.Columns.MAIL,
                UserMailPassContract.Columns.PASS
        };


        List<MailPass> mailPassList = new ArrayList<>();
        try (Cursor c = database.query(UserMailPassContract.TABLE_NAME, columns,
                null, null, null, null, UserMailPassContract.Columns.MAIL)) {
            if (c.moveToFirst()) {
                do {
                    MailPass mailPass = UserMailPassDAO.fromCursor(c);
                    mailPassList.add(mailPass);
                } while (c.moveToNext());
            }
            return mailPassList;
        }
    }


    private static MailPass fromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(UserMailPassContract.Columns._ID));
        String mail = c.getString(c.getColumnIndex(UserMailPassContract.Columns.MAIL));
        String pass = c.getString(c.getColumnIndex(UserMailPassContract.Columns.PASS));
        return new MailPass(mail, pass);
    }

    public void save(MailPass mailPass) {
        ContentValues values = new ContentValues();
        values.put(UserMailPassContract.Columns.MAIL, mailPass.getMail());
        values.put(UserMailPassContract.Columns.PASS, mailPass.getPass());
        long id = database.insert(UserMailPassContract.TABLE_NAME, null, values);
        Log.i("gas", "Your ID NOW"+id);
        mailPass.setId((int) id);
//        mailPass.setId(0);

    }


    public void update(MailPass mailPass) {
        ContentValues values = new ContentValues();
        values.put(UserMailPassContract.Columns.MAIL, mailPass.getMail());
        values.put(UserMailPassContract.Columns.PASS, mailPass.getPass());
        database.update(UserMailPassContract.TABLE_NAME, values, UserMailPassContract.Columns._ID + " = ?", new String[]{String.valueOf(mailPass.getId())});
//        database.update(UserMailPassContract.TABLE_NAME, values, UserMailPassContract.Columns._ID + " = ?", new String[]{"0"});

    }

    public void delete(MailPass mailPass) {
        database.delete(UserMailPassContract.TABLE_NAME, UserMailPassContract.Columns._ID + " = ?", new String[]{String.valueOf(mailPass.getId())});
//        database.delete(UserMailPassContract.TABLE_NAME, UserMailPassContract.Columns.MAIL + " = ?", new String[]{mailPass.getMail()});

    }

}
