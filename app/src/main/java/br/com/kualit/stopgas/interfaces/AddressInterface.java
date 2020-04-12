package br.com.kualit.stopgas.interfaces;

import br.com.kualit.stopgas.model.Endereco;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AddressInterface  {

    @GET("1/cep/{cep}/?")
    Call<Endereco> obterEnderecoPorCep(@Path( "cep" ) String cep, @Query( "app_key" ) String api_key, @Query( "app_secret" ) String app_secret);
}



