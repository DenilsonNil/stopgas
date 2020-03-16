package br.com.kualit.stopgas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginMailPassActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_blue);


        TextView txt_queroMeCadastrar;
        TextView txt_esqueceu;
        txt_queroMeCadastrar = findViewById(R.id.txt_toCad);
        txt_queroMeCadastrar.setOnClickListener(this);
        txt_esqueceu = findViewById(R.id.txt_esqueceu);
        txt_esqueceu.setOnClickListener(this);

        Button botaoLogar = findViewById(R.id.btn_logar);
        botaoLogar.setOnClickListener(this);


        auth = FirebaseAuth.getInstance();

    }


    private void irParaTelaCadastro() {

        Intent intent = new Intent(this, CadastroEmailSenhaActivity.class);
        startActivity(intent);

    }

    private void irParaTelaRecuperarSenha() {
        Intent intent = new Intent(this, RecuperaSenhaActivity.class);
        startActivity(intent);

    }

    private void verificarTextosEmBranco() {
        EditText edt_email = findViewById(R.id.edt_login_mail);
        EditText edt_senha = findViewById(R.id.edt_login_senha);
        String email = edt_email.getText().toString().trim();
        String senha = edt_senha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty())
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
        else
            logar(email, senha);

    }

    private void logar(String email, String senha) {

        Util.iniciarDialog(this, "");
        auth.signInWithEmailAndPassword(email, senha).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Intent intent = new Intent(getBaseContext(), PrincipalAcrivity.class);
                startActivity(intent);
                Util.pararDialog();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Util.pararDialog();
                String mensagem = e.toString();
                Log.i("gas", "Sua mensagem de erro => "+ mensagem);
                Util.opcoesErro(getBaseContext(), mensagem);
            }
        });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txt_toCad:
                irParaTelaCadastro();
                break;
            case R.id.txt_esqueceu:
                irParaTelaRecuperarSenha();
                break;
            case R.id.btn_logar:
                verificarTextosEmBranco();
                break;
        }

    }
}
