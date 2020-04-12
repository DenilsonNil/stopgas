package br.com.kualit.stopgas;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
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

import java.util.Iterator;
import java.util.List;

import br.com.kualit.stopgas.board.OnBoardingActivity;
import br.com.kualit.stopgas.db.UserMailPassDAO;
import br.com.kualit.stopgas.model.MailPass;
import br.com.kualit.stopgas.util.Util;

public class LoginMailPassActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private EditText edtLoginMail, edtLogiSenha;
    private UserMailPassDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.layout_login_blue );
        Util.animarTela( this );

        TextView txt_queroMeCadastrar = findViewById( R.id.txt_toCad );
        TextView txt_esqueceu = findViewById( R.id.txt_esqueceu );

        txt_queroMeCadastrar.setOnClickListener( this );
        txt_esqueceu.setOnClickListener( this );

        Button botaoLogar = findViewById( R.id.btn_logar );
        botaoLogar.setOnClickListener( this );

        dao = UserMailPassDAO.getInstance( this );
        auth = FirebaseAuth.getInstance();
        colocarDadosNasViews();

    }

    private void colocarDadosNasViews() {
        edtLoginMail = findViewById( R.id.edt_login_mail );
        edtLogiSenha = findViewById( R.id.edt_login_senha );

        List<MailPass> mailPasses = dao.list();

        if (!mailPasses.isEmpty()) {
            int lastItem = mailPasses.size() - 1;
            edtLoginMail.setText( mailPasses.get( lastItem ).getMail().toString() );
            edtLogiSenha.setText( mailPasses.get( lastItem ).getPass().toString() );
        }


    }


    private void animateRevealShow(View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max( viewRoot.getWidth(), viewRoot.getHeight() );

        Animator anim = ViewAnimationUtils.createCircularReveal( viewRoot, cx, cy, 0, finalRadius );
        viewRoot.setVisibility( View.VISIBLE );
        anim.setDuration( 1000 );
        anim.setInterpolator( new AccelerateInterpolator() );
        anim.start();
    }


    private void irParaTelaCadastro() {

        Intent intent = new Intent( this, CadastroEmailSenhaActivity.class );
        startActivity( intent );

    }

    private void irParaTelaRecuperarSenha() {
        Intent intent = new Intent( this, RecuperaSenhaActivity.class );
        startActivity( intent );

    }

    private void verificarTextosEmBranco() {
        EditText edt_email = findViewById( R.id.edt_login_mail );
        EditText edt_senha = findViewById( R.id.edt_login_senha );
        String email = edt_email.getText().toString().trim();
        String senha = edt_senha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty())
            Toast.makeText( this, "Preencha todos os campos", Toast.LENGTH_LONG ).show();
        else
            logar( email, senha );

    }

    private void logar(String email, String senha) {

        Util.iniciarDialog( this, "" );
        auth.signInWithEmailAndPassword( email, senha ).addOnSuccessListener( new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                /**Pode ser que o usuário acabou de fazer o cadastro no Firebase porém não acessou ainda o app.
                 * Portanto, não tem ainda o cadastro realizado no SQLite precisamos verificar se ele tem cadastro nos dois lugares pois dependendo
                 * se for o primeiro acesso precisamos direciona-lo para a tela de Boas vindas.*/

                String email = edtLoginMail.getText().toString();
                String senha = edtLogiSenha.getText().toString();
                MailPass mailPass = new MailPass( email, senha );

                List<MailPass> listaEmailESenha = dao.list();


                if (listaEmailESenha.isEmpty()) {

                    gravarDadosNoBanco( mailPass );
                    Intent intent = new Intent( getBaseContext(), OnBoardingActivity.class );
                    startActivity( intent );
                    Util.pararDialog();
                } else {
                    Iterator<MailPass> iterator = listaEmailESenha.iterator();
                    boolean controle = false;



                    while (iterator.hasNext()) {
                        //Lista não está vazia e eu achei o email do usuário. - Já acessou anteriormente.
                        MailPass mailpass1 = iterator.next();

                        controle = edtLoginMail.getText().toString().equals( mailpass1.getMail() );
                        if (controle) {
                            Intent intent = new Intent( LoginMailPassActivity.this, PrincipalAcrivity.class );
                            startActivity( intent );
                            Util.pararDialog();
                            break;
                        }

                    }


                    //Primeiro acesso.
                    if(!controle){
                        gravarDadosNoBanco( mailPass );
                        Intent intent = new Intent( LoginMailPassActivity.this, OnBoardingActivity.class );
                        startActivity( intent );
                        finish();
                    }


                }

                    finish();
            }


        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Util.pararDialog();
                String mensagem = e.toString();
                Util.opcoesErro( getBaseContext(), mensagem );
            }
        } );

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


    private void gravarDadosNoBanco(MailPass mailPass) {
        //Grava os dados de usuário e senha no banco de dados


        dao.save( mailPass );
    }

    private List<MailPass> preencherListaDeEmailESenha() {

        List<MailPass> lista = dao.list();
        return lista;

    }
}
