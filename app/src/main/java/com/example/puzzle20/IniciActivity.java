package com.example.puzzle20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class IniciActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPlay;
    private TelephonyManager mTelephonyMgr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inici);

        //inicializar la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView textView = (TextView) findViewById(R.id.hola);
        textView.setText("Hello there!"); //set text for text view
        //Obtenemos los tres botones de la interfaz
        btnPlay = (Button) findViewById(R.id.play);
        Animation animacion = AnimationUtils.loadAnimation(this,
                R.anim.animacion);
        btnPlay.startAnimation(animacion);

        //Y les asignamos el controlador de eventos
        btnPlay.setOnClickListener(this);

        mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyMgr.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
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
            case R.id.item3:
                musicOn();
                return true;
            case R.id.item4:
                musicOff();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        //Comprobamos el identificador del boton que ha llamado al evento para ver
        //cual de los botones es y ejecutar la acción correspondiente
        switch (v.getId()) {
            case R.id.play:
                abrirJuego();
                break;
        }
    }

    public void abrirJuego() {
        Intent intent = new Intent(this, JocActivity.class);
        startActivity(intent);
    }

    public void musicOn() {
        Intent i = new Intent(this, AudioService.class);
        i.putExtra("action", AudioService.START);
        startService(i);
    }

    public void musicOff() {
        Intent i = new Intent(this, AudioService.class);
        i.putExtra("action", AudioService.PAUSE);
        startService(i);
    }

    @Override
    public void onPause() {
        super.onPause();
       // pausar();
        Intent i = new Intent(this, AudioService.class);
        i.putExtra("action", AudioService.PAUSE);
        startService(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent i = new Intent(this, AudioService.class);
        i.putExtra("action", AudioService.START);
        startService(i);
    }

    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            // Test for incoming call, dialing call, active or on hold
            if (state== TelephonyManager.CALL_STATE_RINGING || state==TelephonyManager.CALL_STATE_OFFHOOK)
            {
                onPause();  // Put here the code to stop your music
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    };

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();

        mTelephonyMgr.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
    }
}
