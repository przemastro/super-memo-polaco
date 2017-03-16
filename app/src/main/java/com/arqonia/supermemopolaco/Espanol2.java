package com.arqonia.supermemopolaco;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Przemek on 04.03.2017.
 */

public class Espanol2 extends Activity {

    Button mybtnSortear;
    Button mybtnComprobar;
    TextView textViewSortear;
    TextView textViewComprobar;
    EditText editTextEntrada;
    DatabaseHelper myDb;
    Espanol esp;
    static int row;
    static String wordPolski;
    static String word;
    public TextView textViewNumero;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle InstanceState) {
        super.onCreate(InstanceState);
        setContentView(R.layout.espanol2);
        myDb = new DatabaseHelper(this);

        mybtnSortear = (Button) findViewById(R.id.buttonSortear);
        textViewSortear = (TextView) findViewById(R.id.textViewSortear);

        mybtnComprobar = (Button) findViewById(R.id.buttonComprobar);
        textViewComprobar = (TextView) findViewById(R.id.textViewComprobar);
        editTextEntrada = (EditText) findViewById(R.id.editTextEntrada);

        textViewNumero = (TextView) findViewById(R.id.textViewNumero);
        textViewNumero.setText(myDb.getEspanolNumero());

        esp = new Espanol();

        mybtnSortear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mybtnComprobar.setEnabled(true);
                textViewSortear.setText(drawEspanol(esp.getIdValue()));
            }
        });

        mybtnComprobar.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                mybtnComprobar.setEnabled(false);
                textViewComprobar.setText(drawPolski(esp.getIdValue()));
                String name = editTextEntrada.getText().toString();
                if (name.trim().equals(wordPolski.trim())) {
                    int value = Integer.parseInt(myDb.getEspanolPoints(textViewComprobar.getText().toString()));
                    if(value < 4) {
                        value = value + 1;
                    }
                    myDb.updateEspanolData(textViewComprobar.getText().toString(),Integer.toString(value));
                    value = Integer.parseInt(myDb.getEspanolPoints(textViewComprobar.getText().toString()));
                    Toast myToast = Toast.makeText(getApplicationContext(), "Correctamente! "+Integer.toString(value)+"p", Toast.LENGTH_LONG);
                    myToast.show();
                } else {
                    int value = Integer.parseInt(myDb.getEspanolPoints(textViewComprobar.getText().toString()));
                    if(value > 0) {
                        value = value - 1;
                    }
                    myDb.updateEspanolData(textViewComprobar.getText().toString(),Integer.toString(value));
                    value = Integer.parseInt(myDb.getEspanolPoints(textViewComprobar.getText().toString()));
                    Toast myToast = Toast.makeText(getApplicationContext(), "Non correctamente! "+Integer.toString(value)+"p", Toast.LENGTH_LONG);
                    myToast.show();
                }
            }
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public String drawEspanol(String value) {
        word = "";
        ArrayList<String> arrlist = new ArrayList<String>();

        int i;
        for(i=1;i<=5;i++) {
            if ((myDb.getEspanolToPolskiWord(randomIdSets(value, 0))) != "0") {
                arrlist.add(myDb.getEspanolToPolskiWord(randomIdSets(value, 0)));
            }
        }
        for(i=1;i<=4;i++) {
            if ((myDb.getEspanolToPolskiWord(randomIdSets(value, 1))) != "0") {
                arrlist.add(myDb.getEspanolToPolskiWord(randomIdSets(value,1)));
            }
        }
        for(i=1;i<=3;i++) {
            if ((myDb.getEspanolToPolskiWord(randomIdSets(value, 2))) != "0") {
                arrlist.add(myDb.getEspanolToPolskiWord(randomIdSets(value,2)));
            }
        }
        for(i=1;i<=2;i++) {
            if ((myDb.getEspanolToPolskiWord(randomIdSets(value, 3))) != "0") {
                arrlist.add(myDb.getEspanolToPolskiWord(randomIdSets(value,3)));
            }
        }
        for(i=1;i<=1;i++) {
            if ((myDb.getEspanolToPolskiWord(randomIdSets(value, 4))) != "0") {
                arrlist.add(myDb.getEspanolToPolskiWord(randomIdSets(value,4)));
            }
        }
        word = lastRandomValue(arrlist);
        return word;
    }

    public String drawPolski(String value) {
        wordPolski = "";
        wordPolski = myDb.getPolskiToEspanolWord(word);

        return wordPolski;
    }

    public int randomIdSets(String value, int points) {
        //1. Select all ids for selected value - level
        ArrayList<String> idSet = new ArrayList<String>();

        if (value == "Uno") {
            idSet = myDb.getIdSet(1,points);
        } else if (value == "Dos") {
            idSet = myDb.getIdSet(2,points);
        } else if (value == "Tres") {
            idSet = myDb.getIdSet(3,points);
        } else if (value == "Cuatro") {
            idSet = myDb.getIdSet(4,points);
        } else if (value == "Cinco") {
            idSet = myDb.getIdSet(5,points);
        } else if (value == "Seis") {
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
