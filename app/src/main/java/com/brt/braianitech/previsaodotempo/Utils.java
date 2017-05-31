package com.brt.braianitech.previsaodotempo;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Braiani on 27/05/2017.
 */

public class Utils {

    public Forecast getInformacoes(String end){
        String json;
        Forecast retorno;

        json = NetworkUtils.getJSONfromApi(end);
        retorno = parseJson(json);
        return retorno;
    }

    @Nullable
    private Forecast parseJson(String json){
        try {
            Forecast previsao = new Forecast();

            JSONObject jsonObject = new JSONObject(json);
            JSONObject objQuery = jsonObject.getJSONObject("query");
            JSONObject objResult = objQuery.getJSONObject("results");
            JSONObject objChannel = objResult.getJSONObject("channel");

            JSONObject localizacao = objChannel.getJSONObject("location");
            previsao.setCidade(localizacao.getString("city"));
            previsao.setEstado(localizacao.getString("region"));
            previsao.setPais(localizacao.getString("country"));

            JSONObject objItem = objChannel.getJSONObject("item");
            JSONObject condicao = objItem.getJSONObject("condition");
            previsao.setTemperatura(condicao.getString("temp"));
            previsao.setCondicao(Integer.parseInt(condicao.getString("code")));

            return previsao;
        }catch (JSONException e){
            Log.e("ParseJSON", e.getMessage());
            return null;
        }
    }
}
