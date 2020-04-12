package br.com.kualit.stopgas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.kualit.stopgas.db.AddressDAO;
import br.com.kualit.stopgas.model.Address;
import br.com.kualit.stopgas.util.Util;

public class AlteraAddressActivity extends AppCompatActivity implements View.OnClickListener {



    private EditText altAddress, altAddressPhone, altAddressName, altAddressBairro, altAdressCity;
    private AddressDAO dao;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.altera_address );
        Util.animarTela(this);
        Button btnAltAddress = findViewById( R.id.btn_altAddress );
        btnAltAddress.setOnClickListener( this );
        altAddress = findViewById( R.id.altAddressAddress );
        altAddressName = findViewById( R.id.altAddressName );
        altAddressPhone = findViewById( R.id.altAddressPhone );
        altAddressBairro = findViewById( R.id.altAddressBairro );
        altAdressCity = findViewById( R.id.altAddressCity );
        dao = AddressDAO.getInstance( this );
        auth = FirebaseAuth.getInstance();

        Util.iniciarDialog( this, "45" );
        MyTask task = new MyTask();
        task.execute();
        prefix();
    }


    private boolean verificarCamposEmBranco() {

        if (altAddress.getText().toString().trim().isEmpty() ||
                altAddressName.getText().toString().trim().isEmpty() ||
                altAddressPhone.getText().toString().trim().isEmpty() ||
                altAddressBairro.getText().toString().trim().isEmpty() ||
                altAdressCity.getText().toString().trim().isEmpty() ||
                altAddressPhone.getText().toString().trim().length() < 15) {
            Toast.makeText( this, "Preencha todos os campos", Toast.LENGTH_LONG ).show();
            return false;
        }


        return true;

    }


    private void prefix() {
        final EditText editText = findViewById( R.id.altAddressPhone );
        final List<String> affineFormats = new ArrayList<>();
        affineFormats.add( "8 ([000]) [000]-[00]-[00]" );

        final MaskedTextChangedListener listener = MaskedTextChangedListener.Companion.installOn(
                editText,
                "([00]) [00000]-[0000]",
                affineFormats,
                AffinityCalculationStrategy.PREFIX,
                new MaskedTextChangedListener.ValueListener() {
                    @Override
                    public void onTextChanged(boolean maskFilled, @NonNull final String extractedValue, @NonNull String formattedText) {
                        logValueListener( maskFilled, extractedValue, formattedText );
                        // checkBox.setChecked(maskFilled);
                    }
                }
        );

        editText.setHint( "Seu telefone" );
    }


    private void logValueListener(boolean maskFilled, @NonNull String extractedValue, @NonNull String formattedText) {
        final String className = AlteraAddressActivity.class.getSimpleName();
        Log.d( className, extractedValue );
        Log.d( className, String.valueOf( maskFilled ) );
        Log.d( className, formattedText );

    }


    private void getAndSetCurrentUserData() {
    }

    @Override
    public void onClick(View v) {
        if (verificarCamposEmBranco()) {
            //Se retorno verdadeiro está tudo preenchido.
            Util.iniciarDialog( this, "45" );


            String user = auth.getCurrentUser().getEmail();
            String name = altAddressName.getText().toString();
            String address = altAddress.getText().toString();
            String cellPhone = altAddressPhone.getText().toString();
            String bairro = altAddressBairro.getText().toString();
            String city = altAdressCity.getText().toString();
            Address myAddress = new Address( user, name, address, cellPhone, bairro, city );

            try {
                dao.update( myAddress );
                Util.pararDialog();
                Intent intent = new Intent( AlteraAddressActivity.this, PrincipalAcrivity.class );
                startActivity( intent );
                finish();
            } catch (Exception e) {
                Log.i( "gas", e.getMessage() );
            }

        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... aVoid) {

            List<Address> enderecos = dao.list();
            Iterator<Address> iterator = enderecos.iterator();


            String currentUser = auth.getCurrentUser().getEmail();
            while (iterator.hasNext()) {
                final Address address = iterator.next();
                if (currentUser.equals( address.getUser() )) {

                    runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            altAddressName.setText( address.getName() );
                            altAddress.setText( address.getAddress() );
                            altAddressBairro.setText( address.getBairro() );
                            altAdressCity.setText( address.getCity() );
                            altAddressPhone.setText( address.getTel() );
                        }
                    } );
                    break;

                } else {

                    Intent intent = new Intent( getApplicationContext(), CadAddressActivity.class );
                    startActivity( intent );
                    finish();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Util.pararDialog();

        }

    }

}