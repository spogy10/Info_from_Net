package com.jr.poliv.infofromnet;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity {
    TextView textView;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (TextView) findViewById(R.id.text);

        new ReadFromWebsite().execute();






    }

    public String getInfoFromWebsite() throws IOException {
        InputStream is = null;
        try {
            int num = 0;
            URL oracle = new URL("http://www.boj.org.jm/foreign_exchange/fx_trading_summary.php");
            HttpURLConnection in = (HttpURLConnection) oracle.openConnection();
            in.setReadTimeout(10000 /* milliseconds */);
            in.setConnectTimeout(15000 /* milliseconds */);
            in.setRequestMethod("GET");
            in.setDoInput(true);
            in.connect();
            Log.d("Paul", "The response is: " + in.getResponseCode());
            is = in.getInputStream();
            String inputStream = readIt(is, 1);
//            while (!(inputStream.equals("O"))) {
//                inputStream = readIt(is, 1);
//                num += 1;
//                Log.d("Paul", String.valueOf(num));
//            }
            if (inputStream != null)
                Log.d("Paul", "It worked");
            return inputStream;
        }finally{
            if (is !=null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader;
        reader = new InputStreamReader(stream, "UTF-8");
//        reader.skip(1000000000);
//        reader.skip(1000000000);
//        reader.skip(1000000000);
//        reader.skip(10000000);
//        reader.skip(1);
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }


   public class ReadFromWebsite extends AsyncTask<Object, Void, String> {


        @Override
        protected String doInBackground(Object[] params) {
            try {
                return getInfoFromWebsite();

            } catch (IOException e) {
                Log.d("Paul", "nah it didnt work");
                return "It didnt work";
            }

        }


       protected void onPostExecute(String result){
           textView.setText(result);
       }
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
}
