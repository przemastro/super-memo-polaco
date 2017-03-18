package com.arqonia.supermemopolaco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Przemek on 04.03.2017.
 */

public class Polski extends Activity {

    static String ID;
    static String ID2;
    public TextView textViewNumer;
    public DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polski);
        myDb = new DatabaseHelper(this);
        textViewNumer = (TextView) findViewById(R.id.textViewNumer);
        textViewNumer.setText(myDb.getEspanolNumero());

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
        if(v.getId() == R.id.buttonRaz)
        {
            Intent i = new Intent(Polski.this, Polski2.class);
            startActivity(i);
            ID = "Raz";
            this.ID2 = "Pierwszy";
        }

        if(v.getId() == R.id.buttonDwa)
        {
            Intent i = new Intent(Polski.this, Polski2.class);
            startActivity(i);
            ID = "Dwa";
            this.ID2 = "Drugi";
        }

        if(v.getId() == R.id.buttonTrzy)
        {
            Intent i = new Intent(Polski.this, Polski2.class);
            startActivity(i);
            ID = "Trzy";
            this.ID2 = "Trzeci";
        }

        if(v.getId() == R.id.buttonCztery)
        {
            Intent i = new Intent(Polski.this, Polski2.class);
            startActivity(i);
            ID = "Cztery";
            this.ID2 = "Czwarty";
        }

        if(v.getId() == R.id.buttonPiec)
        {
            Intent i = new Intent(Polski.this, Polski2.class);
            startActivity(i);
            ID = "Piec";
            this.ID2 = "Piąty";
        }

        if(v.getId() == R.id.buttonSzesc)
        {
            Intent i = new Intent(Polski.this, Polski2.class);
            startActivity(i);
            ID = "Szesc";
            this.ID2 = "Szósty";
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

