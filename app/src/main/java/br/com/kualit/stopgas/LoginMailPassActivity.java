package br.com.kualit.stopgas;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoginMailPassActivity extends AppCompatActivity implements View.OnClickListener, CheckBox.OnCheckedChangeListener {

    private FirebaseAuth auth;
    private CheckBox cbRemember;
    private UserMailPassDAO dao;
    private EditText edtLoginMail, edtLogiSenha;
    private List<MailPass> lista;

    private MailPass usuarioBanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_blue);
        Util.animarTela(this);
        cbRemember = findViewById(R.id.check_remember);
        cbRemember.setOnCheckedChangeListener(this);

        TextView txt_queroMeCadastrar;
        TextView txt_esqueceu;
        txt_queroMeCadastrar = findViewById(R.id.txt_toCad);
        txt_queroMeCadastrar.setOnClickListener(this);
        txt_esqueceu = findViewById(R.id.txt_esqueceu);
        txt_esqueceu.setOnClickListener(this);

        Button botaoLogar = findViewById(R.id.btn_logar);
        botaoLogar.setOnClickListener(this);

        dao = UserMailPassDAO.getInstance(this);
        edtLoginMail = findViewById(R.id.edt_login_mail);
        edtLogiSenha = findViewById(R.id.edt_login_senha);
        lista = new ArrayList<>();
        lista = preencherListaDeEmailESenha();

        if (lista.isEmpty()) {
            edtLoginMail.setText(null);
            edtLogiSenha.setText(null);

        } else{
            usuarioBanco = lista.get(0);
            colocarDadosNasViews(usuarioBanco);
        }
        auth = FirebaseAuth.getInstance();

    }

    private void colocarDadosNasViews(MailPass mailPass) {


        String mail = mailPass.getMail();
        String pass = mailPass.getPass();
        edtLoginMail.setText(mail);
        edtLogiSenha.setText(pass);
    }

    private void printListagem(List list) {
        Iterator<MailPass> iterator = list.iterator();
        while (iterator.hasNext()) {
            MailPass mailPass = iterator.next();
            Log.i("gas", mailPass.getMail());
            Log.i("gas", mailPass.getPass());
        }
    }

    private void animateRevealShow(View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);
        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.start();
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
                String email = edtLoginMail.getText().toString();
                String senha = edtLogiSenha.getText().toString();
                MailPass mailPass = new MailPass(email, senha);

                if (cbRemember.isChecked()) {
                    gravarDadosNoBanco(mailPass);

                } else {

                    if(!lista.isEmpty()){
                        dao.delete(lista.get(0));

                    }



                }
                finish();

                Intent intent = new Intent(getBaseContext(), PrincipalAcrivity.class);
                startActivity(intent);
                Util.pararDialog();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Util.pararDialog();
                String mensagem = e.toString();
                Log.i("gas", "Sua mensagem de erro => " + mensagem);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


    }

    private void gravarDadosNoBanco(MailPass mailPass) {
        //Grava os dados de usuário e senha no banco de dados


        dao.save(mailPass);
    }

    private List<MailPass> preencherListaDeEmailESenha() {

        List<MailPass> lista = dao.list();
        return lista;

    }
}
