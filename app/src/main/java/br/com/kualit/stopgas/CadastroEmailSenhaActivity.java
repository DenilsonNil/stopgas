package br.com.kualit.stopgas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroEmailSenhaActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton botaoCadastrar;
    private AppCompatEditText campoEmail, campoSenha, campoRepetirSenha;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_mail_pass);

        campoEmail = (AppCompatEditText) findViewById(R.id.cadMail);
        campoSenha = (AppCompatEditText) findViewById(R.id.cadSenha);
        campoRepetirSenha = (AppCompatEditText) findViewById(R.id.cadRepetir);



        botaoCadastrar = (AppCompatButton) findViewById(R.id.btn_cadastrar_proximo);

        botaoCadastrar.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {


        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();
        String repeteSenha = campoRepetirSenha.getText().toString();

        if(email.trim().equals("")
                ||email.isEmpty()||
                senha.trim().equals("")
                ||senha.isEmpty()||
                repeteSenha.trim().equals("")
                ||repeteSenha.isEmpty()){

            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
        }else if(!senha.equals(repeteSenha)){
            Toast.makeText(this, "As senhas tem que ser iguais", Toast.LENGTH_LONG).show();
        }else{
                criarUsuario(email, senha);
        }


    }









    private void criarUsuario(String email, String senha) {

       Util.iniciarDialog(this, "");


        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                boolean resultado = task.isSuccessful();

                if (resultado) {
                    Util.pararDialog();
                    startActivity(new Intent(getBaseContext(), LoginMailPassActivity.class));
                    CadastroEmailSenhaActivity.this.finish();
                    Toast.makeText(CadastroEmailSenhaActivity.this, "Sucesso ao Cadastrar", Toast.LENGTH_SHORT).show();

                } else {
                    Util.pararDialog();
                    String resposta = task.getException().toString();
                    Util.opcoesErro(getBaseContext(), resposta);

                }

            }
        });


    }


}
