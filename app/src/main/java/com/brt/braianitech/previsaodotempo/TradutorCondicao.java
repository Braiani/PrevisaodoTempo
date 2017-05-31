package com.brt.braianitech.previsaodotempo;

/**
 * Created by Braiani on 29/05/2017.
 */

public class TradutorCondicao {
    private final String[] condicaoTraduzido = {
            "tornado", //tornado
            "tempestade tropical", //tropical storm
            "furacão", // hurricane
            "tempestade severa", // severe thunderstorms
            "trovoadas", // thunderstorms
            "chuva e neve", // mixed rain and snow
            "chuva e granizo fino", // mixed rain and sleet
            "neve e granizo fino", // mixed snow and sleet
            "garoa gélida", // freezing drizzle
            "garoa", // drizzle
            "chuva gélida", // freezing rain
            "chuvisco",  // showers
            "chuva", // showers
            "neve em flocos finos", // snow flurries
            "leve precipitação de neve", // light snow showers
            "ventos com neve",  // blowing snow
            "neve",             // snow
            "chuva de granizo", // hail
            "pouco granizo",    // sleet
            "pó em suspensão",  // dust
            "neblina",          // foggy
            "névoa seca",       // haze
            "enfumaçado",       // smoky
            "vendaval",         // blustery
            "ventando",         // windy
            "frio",             // cold
            "nublado",          // cloudy
            "muitas nuvens (noite)",         // mostly cloudy (night)
            "muitas nuvens (dia)",           // mostly cloudy (day)
            "parcialmente nublado (noite)",  // partly cloudy (night)
            "parcialmente nublado (dia)",    // partly cloudy (day)
            "céu limpo (noite)",   // clear (night)
            "ensolarado",          // sunny
            "tempo bom (noite)",   // fair (night)
            "tempo bom (dia)",     // fair (day)
            "chuva e granizo",     // mixed rain and hail
            "quente",              // hot
            "tempestades isoladas", // isolated thunderstorms
            "tempestades esparsas", // scattered thunderstorms
            "tempestades esparsas", // scattered thunderstorms
            "chuvas esparsas",      // scattered showers
            "nevasca",              // heavy snow
            "tempestades de neve esparsas",  // scattered snow showers
            "nevasca",               // heavy snow
            "parcialmente nublado",  // partly cloudy
            "chuva com trovoadas",   // thundershowers
            "tempestade de neve",    // snow showers
            "relâmpagos e chuvas isoladas",  // isolated thundershowers
    };

    public String traduzirCondicao(int index){
        if (index > condicaoTraduzido.length){
            return "Não disponível";
        }else {
            return condicaoTraduzido[index];
        }
    }
}
