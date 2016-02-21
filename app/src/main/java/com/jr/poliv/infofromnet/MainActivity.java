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
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.io.IOUtils;

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
    public String input;
    EditText editText, editText2;


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
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);

        new ReadFromWebsite().execute();










    }


    public String getInfoFromWebsite() throws IOException {
        InputStream is = null;
        try {
            URL url = new URL("http://www.boj.org.jm/foreign_exchange/fx_trading_summary.php");
            HttpURLConnection in = (HttpURLConnection) url.openConnection();
            in.setReadTimeout(10000 /* milliseconds */);
            in.setConnectTimeout(15000 /* milliseconds */);
            in.setRequestMethod("GET");
            in.setDoInput(true);
            in.connect();
            Log.d("Paul", "The response is: " + in.getResponseCode());
            is = in.getInputStream();
            String inputStream = extractExchangeRate(IOUtils.toString(is, "UTF-8"));

            if (inputStream != null)
                Log.d("Paul", "It worked");
            return inputStream;
        }finally{
            if (is !=null) {
                is.close();
            }
        }
    }

//    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
//        Reader reader;
//        reader = new InputStreamReader(stream, "UTF-8");
//        Log.d("Paul", String.valueOf(stream.markSupported()));
//        char[] buffer = new char[len];
//        reader.read(buffer);
//        return new String(buffer);
//    }

    public static String extractExchangeRate(String string){
        String intro = "<b>10-DAY MOVING AVERAGE RATE</b></td>\n" +
                "                          </tr>\n" +
                "                          <tr>\n" +
                "                            <td><b>CURRENCY</b></td>\n" +
                "                            <td align=\"center\" width=\"256px\"><b>PURCHASE</b></td>\n" +
                "                            <td align=\"center\" width=\"198px\"><b>SALES</b></td>\n" +
                "                          </tr>\n" +
                "                          \n" +
                "\t\t\t\t\t<tr><td >USD</td><td align=\"right\">";
        string = string.substring(string.indexOf(intro));
        return string.substring(intro.length(),intro.length()+8);
    }


   public class ReadFromWebsite extends AsyncTask<Object, Void, String> {


        @Override
        protected String doInBackground(Object[] params) {
            try {
                return getInfoFromWebsite();

            } catch (IOException e) {
                Log.d("Paul", "nah it didn't work");
                return "It didn't work";
            }

        }


       protected void onPostExecute(String result){
           editText.setText(result);//Log.d("Paul", result);
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
