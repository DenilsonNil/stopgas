package br.com.kualit.stopgas.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.transition.Fade;
import android.transition.Slide;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.kualit.stopgas.dialog.DialogProgress;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Util {

    private static DialogProgress dialog;



    public static boolean verificarInternet(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static void animarTela(Activity activity){
        Fade fade = new Fade();
        fade.setDuration(1000);
        activity.getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        activity.getWindow().setExitTransition(slide);
    }


    public static void iniciarDialog(AppCompatActivity activity, String tag) {

        dialog = new DialogProgress();
        dialog.show(activity.getSupportFragmentManager(), tag);

    }

    public static void pararDialog() {

        if (dialog != null)
            dialog.dismiss();
    }


    public static void opcoesErro(Context context, String resposta) {

        if (resposta.contains("least 6 characters")) {

            Toast.makeText(context, "Sua senha deve conter mais de 5 caracteres", Toast.LENGTH_LONG).show();

        } else if (resposta.contains("address is badly")) {

            Toast.makeText(context, "E-mail inválido", Toast.LENGTH_LONG).show();

        } else if (resposta.contains("interrupted connection")) {

            Toast.makeText(context, "Sem conexão com o Firebase", Toast.LENGTH_LONG).show();

        } else if (resposta.contains("password is invalid")) {

            Toast.makeText(context, "Senha invalida", Toast.LENGTH_LONG).show();

        } else if (resposta.contains("There is no user")) {

            Toast.makeText(context, "Este e-mail não está cadastrado", Toast.LENGTH_LONG).show();

        } else if (resposta.contains("address is already")) {

            Toast.makeText(context, "E-mail já existe cadastrado", Toast.LENGTH_LONG).show();

        } else if (resposta.contains("INVALID_EMAIL")) {

            Toast.makeText(context, "E-mail inválido", Toast.LENGTH_LONG).show();

        } else if (resposta.contains("EMAIL_NOT_FOUND")) {

            Toast.makeText(context, "E-mail não cadastrado ainda", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();


        }

    }


}
