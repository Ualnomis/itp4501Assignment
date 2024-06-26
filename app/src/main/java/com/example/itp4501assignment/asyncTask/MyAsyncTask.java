package com.example.itp4501assignment.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/*
 * Download ten IQ Test questions from a server
 */
public class MyAsyncTask extends AsyncTask<String, Integer, String> {
    private OnDownloadFinishListener listener;
    private final String TAG =" MyAsyncTask";

    public MyAsyncTask() {

    }

    public MyAsyncTask(OnDownloadFinishListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        InputStream inputStream = null;
        String result = "";
        try {
            URL url = new URL("https://ajtdbwbzhh.execute-api.us-east-1.amazonaws.com/default/201920ITP4501Assignment");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            inputStream = con.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while ((line = buffer.readLine()) != null) {
                result += line;
            }

            Log.d(TAG, "Load json Success");
            inputStream.close();
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.updateDownloadResult(s);
    }
}
