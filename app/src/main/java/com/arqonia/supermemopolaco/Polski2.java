package com.arqonia.supermemopolaco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Przemek on 04.03.2017.
 */

public class Polski2 extends Activity {

    Button mybtnWylosuj;
    Button mybtnPorownaj;
    TextView textViewWylosuj;
    TextView textViewPorownaj;
    TextView textViewPoziom;
    EditText editTextWprowadz;
    DatabaseHelper myDb;
    Polski pol;
    static int row;
    static String wordEspanol;
    static String word;
    public TextView textViewNumer;

    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polski2);
        myDb = new DatabaseHelper(this);

        mybtnWylosuj = (Button) findViewById(R.id.buttonWylosuj);
        textViewWylosuj = (TextView) findViewById(R.id.textViewWylosuj);

        mybtnPorownaj = (Button) findViewById(R.id.buttonPorownaj);
        textViewPorownaj = (TextView) findViewById(R.id.textViewPorownaj);
        editTextWprowadz = (EditText) findViewById(R.id.editTextWprowadz);

        textViewPoziom = (TextView) findViewById(R.id.textViewPoziom);
        textViewPoziom.setText(pol.ID2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pol = new Polski();

        mybtnWylosuj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mybtnPorownaj.setEnabled(true);
                textViewWylosuj.setText(drawPolski(pol.getIdValue()));
                mybtnWylosuj.setVisibility(View.GONE);
                mybtnPorownaj.setVisibility(View.VISIBLE);
            }
        });

        mybtnPorownaj.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                mybtnWylosuj.setVisibility(View.VISIBLE);
                mybtnPorownaj.setVisibility(View.GONE);
                mybtnPorownaj.setEnabled(false);
                textViewPorownaj.setText(drawEspanol(pol.getIdValue()));
                String name = editTextWprowadz.getText().toString();
                if (name.trim().toLowerCase().equals(wordEspanol.trim().toLowerCase())) {
                    int value = Integer.parseInt(myDb.getPolskiPoints(textViewPorownaj.getText().toString()));
                    if(value < 4) {
                        value = value + 1;
                    }
                    myDb.updatePolskiData(textViewPorownaj.getText().toString(),Integer.toString(value));
                    value = Integer.parseInt(myDb.getPolskiPoints(textViewPorownaj.getText().toString()));
                    Toast myToast = Toast.makeText(getApplicationContext(), "Poprawnie! "+Integer.toString(value)+"p", Toast.LENGTH_LONG);
                    myToast.show();
                } else {
                    int value = Integer.parseInt(myDb.getPolskiPoints(textViewPorownaj.getText().toString()));
                    if(value > 0) {
                        value = value - 1;
                    }
                    myDb.updatePolskiData(textViewPorownaj.getText().toString(),Integer.toString(value));
                    value = Integer.parseInt(myDb.getPolskiPoints(textViewPorownaj.getText().toString()));
                    Toast myToast = Toast.makeText(getApplicationContext(), "Niepoprawnie! "+Integer.toString(value)+"p", Toast.LENGTH_LONG);
                    myToast.show();
                }
            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    public String drawPolski(String value) {
        word = "";
        ArrayList<String> arrlist = new ArrayList<String>();

        int i;
        for(i=1;i<=5;i++) {
            if ((myDb.getPolskiToEspanolWord2(randomIdSets(value, 0))) != "0") {
                arrlist.add(myDb.getPolskiToEspanolWord2(randomIdSets(value, 0)));
            }
        }
        for(i=1;i<=4;i++) {
            if ((myDb.getPolskiToEspanolWord2(randomIdSets(value, 1))) != "0") {
                arrlist.add(myDb.getPolskiToEspanolWord2(randomIdSets(value,1)));
            }
        }
        for(i=1;i<=3;i++) {
            if ((myDb.getPolskiToEspanolWord2(randomIdSets(value, 2))) != "0") {
                arrlist.add(myDb.getPolskiToEspanolWord2(randomIdSets(value,2)));
            }
        }
        for(i=1;i<=2;i++) {
            if ((myDb.getPolskiToEspanolWord2(randomIdSets(value, 3))) != "0") {
                arrlist.add(myDb.getPolskiToEspanolWord2(randomIdSets(value,3)));
            }
        }
        for(i=1;i<=1;i++) {
            if ((myDb.getPolskiToEspanolWord2(randomIdSets(value, 4))) != "0") {
                arrlist.add(myDb.getPolskiToEspanolWord2(randomIdSets(value,4)));
            }
        }
        word = lastRandomValue(arrlist);
        return word;
    }

    public String drawEspanol(String value) {
        wordEspanol = "";
        wordEspanol = myDb.getEspanolToPolskiWord2(word);

        return wordEspanol;
    }

    public int randomIdSets(String value, int points) {
        //1. Select all ids for selected value - level
        ArrayList<String> idSet = new ArrayList<String>();

        if (value == "Raz") {
            idSet = myDb.getIdSet(1,points);
        } else if (value == "Dwa") {
            idSet = myDb.getIdSet(2,points);
        } else if (value == "Trzy") {
            idSet = myDb.getIdSet(3,points);
        } else if (value == "Cztery") {
            idSet = myDb.getIdSet(4,points);
        } else if (value == "Piec") {
            idSet = myDb.getIdSet(5,points);
        } else if (value == "Szesc") {
            idSet = myDb.getIdSet(6,points);
        }

        int ran = randomValue(idSet);
        return ran;
    }

    public int randomValue(ArrayList<String> idSet) {
        Random ran = new Random();
        int randomIndex = ran.nextInt(idSet.size());
        int row = Integer.parseInt(idSet.get(randomIndex));
        return row;
    }

    public String lastRandomValue(ArrayList<String> arrlist) {
        //2. Select random word from list of 15 random words
        Random ran = new Random();
        int randomIndex = ran.nextInt(arrlist.size());
        String word = arrlist.get(randomIndex).toString();
        return word;
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Espanol2 Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}