package com.brt.braianitech.previsaodotempo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Braiani on 27/05/2017.
 */

public class NetworkUtils {
    public static String getJSONfromApi(String url){
        String retorno = "";
        try{
            URL apiEnd = new URL(url);
            int codigoResposta;
            HttpURLConnection conexao;
            InputStream is;

            conexao = (HttpURLConnection) apiEnd.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setConnectTimeout(15000);
            conexao.setReadTimeout(15000);
            conexao.connect();

            codigoResposta = conexao.getResponseCode();

            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                is = conexao.getInputStream();
            }else {
                is = conexao.getErrorStream();
            }
            retorno = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();

        } catch (IOException ioe){
            Log.e("NetworkUtils", ioe.getMessage(), ioe);
        }

        return retorno;
    }

    private static String converterInputStreamToString(InputStream is){
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br;
            String linha;

            br = new BufferedReader(new InputStreamReader(is));
            while ((linha = br.readLine()) != null){
                buffer.append(linha);
            }

            br.close();
        }catch (IOException ioe){
            Log.e("ConverterIS", ioe.getMessage(), ioe);
        }

        return buffer.toString();
    }
}
