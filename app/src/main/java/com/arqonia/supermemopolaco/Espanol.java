package com.arqonia.supermemopolaco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Przemek on 04.03.2017.
 */

public class Espanol extends Activity {

    static String ID;
    public TextView textViewNumero;
    public DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.espanol);
        //myDb = new DatabaseHelper(this);
        textViewNumero = (TextView) findViewById(R.id.textViewNumero);
        //textViewNumero.setText(myDb.getEspanolNumero());
    }

    public void onButtonClick(View v)
    {
        if(v.getId() == R.id.buttonUno)
        {
            Intent i = new Intent(Espanol.this, Espanol2.class);
            startActivity(i);
            ID = "Uno";
        }

        if(v.getId() == R.id.buttonDos)
        {
            Intent i = new Intent(Espanol.this, Espanol2.class);
            startActivity(i);
            ID = "Dos";
        }

        if(v.getId() == R.id.buttonTres)
        {
            Intent i = new Intent(Espanol.this, Espanol2.class);
            startActivity(i);
            ID = "Tres";
        }

        if(v.getId() == R.id.buttonCuatro)
        {
            Intent i = new Intent(Espanol.this, Espanol2.class);
            startActivity(i);
            ID = "Cuatro";
        }

        if(v.getId() == R.id.buttonCinco)
        {
            Intent i = new Intent(Espanol.this, Espanol2.class);
            startActivity(i);
            ID = "Cinco";
        }

        if(v.getId() == R.id.buttonSeis)
        {
            Intent i = new Intent(Espanol.this, Espanol2.class);
            startActivity(i);
            ID = "Seis";
        }
    }

    public String getIdValue()
    {
        return ID;
    }

}
