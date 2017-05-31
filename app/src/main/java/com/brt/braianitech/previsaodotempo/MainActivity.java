package com.brt.braianitech.previsaodotempo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    TextView titulo, conteudo;
    ImageView banner;
    Button btnPesquisar;
    EditText cidadePesquisa;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        encontrarViews();
        btnPesquisar.setOnClickListener(ouvinte);
    }

    View.OnClickListener ouvinte = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //fazer as ações
            /*testes!
            cardView.setVisibility(View.VISIBLE);
            titulo.setText("Campo Grande, MS, Brasil");
            conteudo.setText("Parcialmente nublado, temperatura de 22º C");
            cidadePesquisa.setText("");
            banner.setImageResource(R.mipmap.nublado); */
            Previsao buscar = new Previsao();
            buscar.execute(cidadePesquisa.getText().toString());
        }
    };

    void encontrarViews() {
        titulo = (TextView) findViewById(R.id.titulo);
        conteudo = (TextView) findViewById(R.id.conteudo);
        banner = (ImageView) findViewById(R.id.banner);
        btnPesquisar = (Button) findViewById(R.id.btnPesquisar);
        cidadePesquisa = (EditText) findViewById(R.id.pesquisarCidade);
        cardView = (CardView) findViewById(R.id.card_view);
    }

    public class Previsao extends AsyncTask<String, Void, Forecast> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(MainActivity.this, "Aguarde...", "Procurando previsão para essa cidade...");
        }

        @Override
        protected void onPostExecute(Forecast previsao) {
            try {
                TradutorCondicao tradutor = new TradutorCondicao();
                String sTitulo = "";
                String sConteudo = "";
                sTitulo = previsao.getCidade() + ", " + previsao.getEstado() + ", " + previsao.getPais();
                sConteudo = "Temperatura de "+previsao.getTemperatura()+" ªC, "+tradutor.traduzirCondicao(previsao.getCondicao());
                cardView.setVisibility(View.VISIBLE);
                titulo.setText(sTitulo);
                conteudo.setText(sConteudo);
                banner.setImageResource(R.mipmap.nublado);
            } catch (Exception e) {
                Log.e("PostExecute", e.getMessage());
                Toast.makeText(getApplicationContext(), "Ocorreu um erro ao recuperar os dados", Toast.LENGTH_SHORT).show();
            } finally {
                dialog.dismiss();
            }
        }

        @Override
        protected Forecast doInBackground(String... params) {
            try {
                String url = "https://query.yahooapis.com/v1/public/yql?q=";
                String query = URLEncoder.encode("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text='" + params[0] + "') and u = 'c'", "utf-8");
                String endQuery = "&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
                Utils util = new Utils();

                url = url + query + endQuery ;
                return util.getInformacoes(url);
            } catch (Exception e) {
                Log.e("DoInBacground", e.getMessage());
            }
            return null;

        }
    }
}
