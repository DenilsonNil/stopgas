package br.com.kualit.stopgas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import br.com.kualit.stopgas.db.AddressDAO;
import br.com.kualit.stopgas.fragments.FragmentAddress;
import br.com.kualit.stopgas.fragments.FragmentCep;
import br.com.kualit.stopgas.model.Address;
import br.com.kualit.stopgas.util.Util;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class CadAddressActivity extends AppCompatActivity {



    private EditText cadAddress, cadAddressPhone, cadAddressName, cadAddressBairro, cadAdressCity;
    private AddressDAO dao;
    private FirebaseAuth auth;
    private FragmentTransaction fragmentTransaction;
    Button btnCadAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.layout_cad_endereco );

        cadAddressName = findViewById( R.id.cadAddressName );
        cadAddressPhone = findViewById( R.id.cadAddressPhone );
        cadAddressBairro = findViewById( R.id.cadAddressBairro );
        cadAdressCity = findViewById( R.id.cadAddressCity );



        carregarCepNoPlaceHolder();
    }




    private FragmentTransaction returnFragmentTransaction() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager.beginTransaction();

    }

    private void carregarCepNoPlaceHolder() {
        FragmentCep fragmentCep = new FragmentCep();
        fragmentTransaction = returnFragmentTransaction();
        fragmentTransaction.add( R.id.place_holder, fragmentCep, "fc" );
        fragmentTransaction.commit();



    }

    private void carregarEnderecoNoPlaceHolder() {
        FragmentAddress fragmentAddress = new FragmentAddress();
        fragmentTransaction = returnFragmentTransaction();
        fragmentTransaction.replace( R.id.place_holder, fragmentAddress );
        fragmentTransaction.commit();

    }


    public void verificarCepEmBranco(View view) {
        EditText edtCep = findViewById( R.id.edt_cadCep );
        String cepDigitado = edtCep.getText().toString();

        if (cepDigitado.length() < 8 || cepDigitado.trim().isEmpty()) {
            Toast.makeText( this, "CEP incompleto", Toast.LENGTH_LONG ).show();
        } else {
            carregarEnderecoNoPlaceHolder();
        }

    }


    private boolean verificarCamposEmBranco() {

        if (cadAddress.getText().toString().trim().isEmpty() ||
                cadAddressName.getText().toString().trim().isEmpty() ||
                cadAddressPhone.getText().toString().trim().isEmpty() ||
                cadAddressBairro.getText().toString().trim().isEmpty() ||
                cadAdressCity.getText().toString().trim().isEmpty() ||
                cadAddressPhone.getText().toString().trim().length() < 15) {
            Toast.makeText( this, "Preencha todos os campos", Toast.LENGTH_LONG ).show();
            Log.i( "gas", "Retornou Falso" );
            return false;
        }
        Log.i( "gas", "Retornou true" );
        Log.i( "gas", cadAddressPhone.getText().toString() );

        return true;

    }


    private void finalizarCadastro() {

        if (verificarCamposEmBranco()) {
            //Se retorno verdadeiro está tudo preenchido.
            Log.i( "gas", "Onclick" );


            Util.iniciarDialog( this, "15" );
            auth = FirebaseAuth.getInstance();
            dao = AddressDAO.getInstance( this );

            String user = auth.getCurrentUser().getEmail();
            String name = cadAddressName.getText().toString();
            String address = cadAddress.getText().toString();
            String cellPhone = cadAddressPhone.getText().toString();
            String bairro = cadAddressBairro.getText().toString();
            String city = cadAdressCity.getText().toString();
            Address myAddress = new Address( user, name, address, cellPhone, bairro, city );

            try {
                dao.save( myAddress );
                Util.pararDialog();
                Intent intent = new Intent( CadAddressActivity.this, PrincipalAcrivity.class );
                startActivity( intent );
                finish();
            } catch (Exception e) {
                Log.i( "gas", e.getMessage() );
            }

        }

    }



}
