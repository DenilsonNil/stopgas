package br.com.kualit.stopgas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperaSenhaActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth auth;
    private EditText campoEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.layout_recupera_senha);
        super.onCreate(savedInstanceState);
        Button botaoRecuperarSenha;
        botaoRecuperarSenha = findViewById(R.id.btn_envia_email);
        botaoRecuperarSenha.setOnClickListener(this);
        campoEmail = findViewById(R.id.edt_login_senha);

        auth = FirebaseAuth.getInstance();
    }


    private void sendMail(String email) {

        Log.i("gas", "Método Send chamado");

        Util.iniciarDialog(this, "");

        Log.i("gas", "Email digitado: " + email);

        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getBaseContext(), "Enviamos uma MSG para o seu email com um link para você redefinir a sua senha",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), LoginMailPassActivity.class);
                startActivity(intent);
                Util.pararDialog();


            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                String erro = e.toString();
                Util.pararDialog();
                Util.opcoesErro(getBaseContext(), erro);


            }
        });


    }

    @Override
    public void onClick(View v) {
        String email = campoEmail.getText().toString().trim();
        if (email.trim().equals("")
                || email.isEmpty()) {
            Toast.makeText(getBaseContext(), "Preencha o email", Toast.LENGTH_LONG).show();

        } else {
            sendMail(email);

        }


    }
}
