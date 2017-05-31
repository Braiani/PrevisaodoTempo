package com.brt.braianitech.previsaodotempo;

/**
 * Created by Braiani on 27/05/2017.
 */

public class Forecast {
    private String cidade, estado, pais, temperatura;
    int condicao;

    String getCidade() {
        return cidade;
    }

    void setCidade(String cidade) {
        this.cidade = cidade;
    }

    String getEstado() {
        return estado;
    }

    void setEstado(String estado) {
        this.estado = estado;
    }

    String getPais() {
        return pais;
    }

    void setPais(String pais) {
        this.pais = pais;
    }

    String getTemperatura() {
        return temperatura;
    }

    void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    int getCondicao() {
        return condicao;
    }

    void setCondicao(int condicao) {
        this.condicao = condicao;
    }
}
