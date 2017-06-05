package com.brt.braianitech.previsaodotempo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView titulo, conteudo;
    ImageView banner;
    Button btnPesquisar;
    EditText cidadePesquisa;
    CardView cardView;


    List<Forecast> previsaoList = new ArrayList<>();
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        encontrarViews();
        btnPesquisar.setOnClickListener(ouvinte);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setClickable(false);

    }

    View.OnClickListener ouvinte = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
        rv = (RecyclerView) findViewById(R.id.rv);
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
                cidadePesquisa.setText("");
                previsaoList.add(previsao);
                RVAdapter adapter = new RVAdapter(previsaoList);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();


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

    public static class RVAdapter extends RecyclerView.Adapter<RVAdapter.ForecastViewHolder>{

        public class ForecastViewHolder extends RecyclerView.ViewHolder{

            CardView cardView;
            TextView txtTitulo, txtConteudo;
            ImageView banner;

            public ForecastViewHolder(View itemView) {
                super(itemView);
                txtTitulo = (TextView) itemView.findViewById(R.id.titulo);
                txtConteudo = (TextView) itemView.findViewById(R.id.conteudo);
                banner = (ImageView) itemView.findViewById(R.id.banner);
                cardView = (CardView) itemView.findViewById(R.id.card_view);
            }
        }

        List<Forecast> previsao;

        public RVAdapter(List<Forecast> previsao) {
            this.previsao = previsao;
        }

        public RVAdapter.ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            ForecastViewHolder fvh = new ForecastViewHolder(v);
            return fvh;
        }

        @Override
        public void onBindViewHolder(RVAdapter.ForecastViewHolder holder, int position) {
            TradutorCondicao tradutor = new TradutorCondicao();
            String sTitulo = "";
            String sConteudo = "";
            sTitulo = previsao.get(position).getCidade() + ", " + previsao.get(position).getEstado() + ", " + previsao.get(position).getPais();
            sConteudo = "Temperatura de "+previsao.get(position).getTemperatura()+" ªC, "+tradutor.traduzirCondicao(previsao.get(position).getCondicao());
            holder.cardView.setVisibility(View.VISIBLE);
            holder.txtTitulo.setText(sTitulo);
            holder.txtConteudo.setText(sConteudo);
            holder.banner.setImageResource(R.drawable.nublado);
        }

        @Override
        public int getItemCount() {
            return previsao.size();
        }
    }
}
