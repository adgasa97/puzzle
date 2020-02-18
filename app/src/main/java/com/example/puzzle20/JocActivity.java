package com.example.puzzle20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class JocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joc);

        //inicializar la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            case R.id.item2:
                abrirMusica();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void abrirInicio() {
        Intent intent = new Intent(this, IniciActivity.class);
        startActivity(intent);
    }

    public void abrirMusica() {
        Intent intent = new Intent(this, MusicaActivity.class);
        startActivity(intent);
    }
}
