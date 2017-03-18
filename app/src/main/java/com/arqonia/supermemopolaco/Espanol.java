package com.arqonia.supermemopolaco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Przemek on 04.03.2017.
 */

public class Espanol extends Activity {

    static String ID;
    static String ID2;
    public TextView textViewNumero;
    public DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.espanol);
        myDb = new DatabaseHelper(this);
        textViewNumero = (TextView) findViewById(R.id.textViewNumero);
        textViewNumero.setText(myDb.getEspanolNumero());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void onButtonClick(View v)
    {
        if(v.getId() == R.id.buttonUno)
        {
            Intent i = new Intent(Espanol.this, Espanol2.class);
            startActivity(i);
            ID = "Uno";
            this.ID2 = "Primero";
        }

        if(v.getId() == R.id.buttonDos)
        {
            Intent i = new Intent(Espanol.this, Espanol2.class);
            startActivity(i);
            ID = "Dos";
            this.ID2 = "Segundo";
        }

        if(v.getId() == R.id.buttonTres)
        {
            Intent i = new Intent(Espanol.this, Espanol2.class);
            startActivity(i);
            ID = "Tres";
            this.ID2 = "Tercero";
        }

        if(v.getId() == R.id.buttonCuatro)
        {
            Intent i = new Intent(Espanol.this, Espanol2.class);
            startActivity(i);
            ID = "Cuatro";
            this.ID2 = "Cuatro";
        }

        if(v.getId() == R.id.buttonCinco)
        {
            Intent i = new Intent(Espanol.this, Espanol2.class);
            startActivity(i);
            ID = "Cinco";
            this.ID2 = "Quinto";
        }

        if(v.getId() == R.id.buttonSeis)
        {
            Intent i = new Intent(Espanol.this, Espanol2.class);
            startActivity(i);
            ID = "Seis";
            this.ID2 = "Seito";
        }
    }

    public String getIdValue()
    {
        return ID;
    }

    public String getId2Value()
    {
        return ID2;
    }

}
