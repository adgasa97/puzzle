package com.example.puzzle20;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;

public class MusicaActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnPlay;
    private Button btnPause;
    private Button btnStop;
    private MediaPlayer mediaplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musica);

        //inicializar la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializamos la clase MediaPlayer asociandole el fichero de Audio
        mediaplayer = MediaPlayer.create(this, R.raw.pokemon);

        //Obtenemos los tres botones de la interfaz
        btnPlay = (Button) findViewById(R.id.buttonPlay);
        btnStop = (Button) findViewById(R.id.buttonStop);
        btnPause = (Button) findViewById(R.id.buttonPause);

        //Y les asignamos el controlador de eventos
        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnPause.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                abrirInicio();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void abrirInicio() {
        Intent intent = new Intent(this, IniciActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        //Comprobamos el identificador del boton que ha llamado al evento para ver
        //cual de los botones es y ejecutar la acción correspondiente
        switch (v.getId()) {
            case R.id.buttonPlay:
                //Iniciamos el audio
                mediaplayer.start();
                break;
            case R.id.buttonPause:
                //Pausamos el audio
                mediaplayer.pause();
                break;
            case R.id.buttonStop:
                //Paramos el audio y volvemos a inicializar
                try {
                    mediaplayer.stop();
                    mediaplayer.prepare();
                    mediaplayer.seekTo(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;

        }
    }
}
