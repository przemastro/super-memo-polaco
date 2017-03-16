package com.arqonia.supermemopolaco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Przemek on 04.03.2017.
 */

public class Polski extends Activity {

    static String ID;
    public TextView textViewNumer;
    public DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polski);
        //myDb = new DatabaseHelper(this);
        textViewNumer = (TextView) findViewById(R.id.textViewNumer);
        //textViewNumer.setText(myDb.getPolskiNumer());
    }

    public void onButtonClick(View v)
    {
        if(v.getId() == R.id.buttonRaz)
        {
            Intent i = new Intent(Polski.this, Polski2.class);
            startActivity(i);
            ID = "Raz";
        }

        if(v.getId() == R.id.buttonDwa)
        {
            Intent i = new Intent(Polski.this, Polski2.class);
            startActivity(i);
            ID = "Dwa";
        }

        if(v.getId() == R.id.buttonTrzy)
        {
            Intent i = new Intent(Polski.this, Polski2.class);
            startActivity(i);
            ID = "Trzy";
        }

        if(v.getId() == R.id.buttonCztery)
        {
            Intent i = new Intent(Polski.this, Polski2.class);
            startActivity(i);
            ID = "Cztery";
        }

        if(v.getId() == R.id.buttonPiec)
        {
            Intent i = new Intent(Polski.this, Polski2.class);
            startActivity(i);
            ID = "Piec";
        }

        if(v.getId() == R.id.buttonSzesc)
        {
            Intent i = new Intent(Polski.this, Polski2.class);
            startActivity(i);
            ID = "Szesc";
        }
    }

    public String getIdValue()
    {
        return ID;
    }
}

