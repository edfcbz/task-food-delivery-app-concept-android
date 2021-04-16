package bean;

import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class HttpService extends AsyncTask<Void, Void, Boolean>{

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (Boolean.TRUE.equals(aBoolean)){
            System.out.println("HTTP Funfou"+aBoolean);
        }else{
            System.out.println("HTTP Não funfou"+aBoolean);
        }
    }


    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            URL url = new URL("http://192.168.0.120:8080/services/store/json/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(60000);
            connection.connect();

            System.out.println("");
            return Boolean.TRUE;

        } catch (Throwable t) {
            t.printStackTrace();
            return Boolean.FALSE;
        }
    }
}