package com.client.vote.common;

import android.os.StrictMode;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SimpleHttpClient {
    /**
     * The time it takes for our client to timeout
     */
    public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds
    private static String serverURL = "http://52.76.83.72:8080/vote";

    /**
     * Single instance of our HttpClient
     */
    private static HttpClient mHttpClient;

    /**
     * Get our single instance of our HttpClient object.
     *
     * @return an HttpClient object with connection parameters set
     */
    private static HttpClient getHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
            final HttpParams params = mHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return mHttpClient;
    }

    /**
     * Performs an HTTP Post request to the specified url with the
     * specified parameters.
     *
     * @param submitRequest
     * @param postParameters The parameters to send via the request
     * @return The result of the request
     * @throws Exception
     */
    public static String executeHttpPost(String submitRequest, ArrayList<NameValuePair> postParameters) throws Exception {
        Log.i("SimpleHttpClient", "inside");
        String url = serverURL + submitRequest;
        Log.i("url::", url);
        Log.i("postParameters::", postParameters.toString());

        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            Log.i("client::", client.toString());
            Log.i("SimpleHttpClient::2", client.toString());
            HttpPost request = new HttpPost(url);
            Log.i("SimpleHttpClient::3", request.toString());

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            Log.i("SimpleHttpClient::4", formEntity.toString());
            Log.i("request::", request.toString());
            Log.i("formEntity::", formEntity.toString());
            request.setEntity(formEntity);
            Log.i("request:2:", request.toString());
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Log.i("request:3:", "Strict Mode assigned");
            HttpResponse response = client.execute(request);
            Log.i("response::", response.toString());
            Log.i("SimpleHttpClient::5", response.toString());
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            Log.i("SimpleHttpClient::6", in.toString());

            StringBuffer sb = new StringBuffer("");
            Log.i("SimpleHttpClient::7", sb.toString());

            String line = "";
            String NL = System.getProperty("line.separator");
            Log.i("SimpleHttpClient::8 ", NL.toString());

            while ((line = in.readLine()) != null) {
                Log.i("SimpleHttpClient::9 ", "");
                sb.append(line + NL);
            }
            in.close();
            String result = sb.toString();
            Log.i("SimpleHttpClient ::", result);
            return result;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("SimpleHttpClient", String.valueOf(e));
                }
            }
        }
    }
}