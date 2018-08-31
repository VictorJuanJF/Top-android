package com.example.jimenez.top;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.containerMain)
    CoordinatorLayout containerMain;

    private ArtistaAdapter adapter;

    public static final Artista sArtista=new Artista();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        configToolbar();
        configAdapter();
        configRecyclerView();
        generateArtist();


    }

    private void generateArtist() {
        String[] nombres = {"Jason", "Victor El capo"};
        String[] apellidos = {"Statham", "Statham"};
        long[] nacimientos = {-28339200000L, -28339200000L};
        String[] lugares = {"Shirebrook", "Shirebrook"};
        short[] estaturas = {178, 178};
        String[] notas = {"Jason Statham was born in Shirebrook, Derbyshire, to Eileen (Yates), a dancer, and Barry Statham, a street merchant and lounge singer. Statham has done quite a lot in a short time. He has been a Diver on the British National Diving Team and finished 12th in the World Championships in 1992. He has also been a fashion model, black market salesman and finally of course, actor. He got the audition for his debut role as Bacon in Lock & Stock (1998) through French Connection, for whom he was modeling. They became a major investor in the film and introduced Jason to Guy Ritchie, who invited him to audition for a part in the film by challenging him to impersonate an illegal street vendor and convince him to purchase fake jewelry. Jason must have been doing something right because after the success of Lock & Stock (1998) he teamed up again with Guy Ritchie for Snatch: Cerdos y diamantes (2000), with co-stars including Brad Pitt, Dennis Farina and Benicio Del Toro. After Snatch: Cerdos y diamantes (2000) came Turn It Up (2000) with US music star Ja Rule, followed by a supporting actor role in the Sci-Fi film Fantasmas de Marte de John Carpenter (2001), Jet Li's El único (2001) and another screen partnership with Vinnie Jones in Mean Machine: Jugar duro (2001) under Guy Ritchie's and Matthew Vaughn's SKA Films. Finally in 2002 he was cast as the lead role of Frank Martin in Transporter (2002). Jason is also in the summer 2003 blockbuster remake of Un trabajo en Italia (1969), The Italian Job (2003), playing Handsome Rob.\n" +
                "- IMDb Mini Biography By: Mikedavies86", "ferg"};
        String[] fotos = {"data:image/jpeg;base64,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", null};
        for (int i = 0; i < 2; i++) {
            Artista artista = new Artista(i + 1, nombres[i], apellidos[i], nacimientos[i],
                    lugares[i], estaturas[i], notas[i], i + 1, fotos[i]);
            adapter.add(artista);
        }
    }

    private void configRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);

    }

    private void configAdapter() {
        adapter = new ArtistaAdapter(new ArrayList<Artista>(), this);

    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /************
     * Metodos implementados por la interfaz OnItemClickListener*************/
    @Override
    public void onItemClick(Artista artista) {
        sArtista.setId(artista.getId());
        sArtista.setNombre(artista.getNombre());
        sArtista.setApellidos(artista.getApellidos());
        sArtista.setFechaNacimiento(artista.getFechaNacimiento());
        sArtista.setEstatura(artista.getEstatura());
        sArtista.setLugarNacimiento(artista.getLugarNacimiento());
        sArtista.setOrden(artista.getOrden());
        sArtista.setNotas(artista.getNotas());
        sArtista.setFotoUrl(artista.getFotoUrl());
        Intent intent=new Intent(MainActivity.this,DetalleActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(Artista artista) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK && requestCode==1){
            adapter.add(sArtista);
        }
    }


    @OnClick(R.id.fab)
    public void addArtist() {
        Intent intent = new Intent(MainActivity.this,AddArtistActivity.class);
        intent.putExtra(Artista.ORDEN,adapter.getItemCount()+1);
        startActivityForResult(intent,1);
    }
}
