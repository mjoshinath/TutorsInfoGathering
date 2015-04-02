package helper;

/**
 * Created by ehc on 1/4/15.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import tutorsweb.ehc.com.tutorsinfogathering.R;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 16/8/14
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class WebserviceHelper {
    public static AsyncHttpClient client = new AsyncHttpClient();
    RequestParams requestParams = new RequestParams();
    Context context;
    SharedPreferences preferences;

    public WebserviceHelper(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("session", Context.MODE_MULTI_PROCESS);
    }

    public void getData(final WebServiceCallBack callBack) {
        SharedPreferences categorySharedPref = context.getSharedPreferences("categories", Context.MODE_MULTI_PROCESS);
        final SharedPreferences.Editor categoryEditor = categorySharedPref.edit();
        client.addHeader("If-None-Match", categorySharedPref.getString("etag", ""));
        client.get("http://192.168.1.124:5000/api/v1/categories", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = new String(bytes);
                Log.d("test18", "response" + response);
                callBack.populateData(response);
                for (Header header : headers) {
                    if (header.getName().equalsIgnoreCase("ETag")) {
                        categoryEditor.putString("etag", header.getValue()).commit();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("test18", "fail");
                callBack.hideProgressBarOnFailure();
            }
        });
    }

    private void setParamsForCall() {
        /*type = hashMapParams.get(context.getString(R.string.type));
        switch (type) {

            *//*case BRANDS:
                requestParams.add(context.getString(R.string.list_type), hashMapParams.get(context.getString(R.string.list_type)));
                requestParams.add(context.getString(R.string.list_type_value), hashMapParams.get(context.getString(R.string.list_type_value)));
                requestParams.add(context.getString(R.string.page), hashMapParams.get(context.getString(R.string.page)));
                break;*//*
        }*/
    }

    public void postData(final WebServiceCallBack callBack, StringEntity entity, final long id) {

        client.post(context, "http://192.168.1.124:5000/api/v1/tutors?tutor", entity, "application/json",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String response = new String(bytes);
                        Log.d("test18", "success:" + response);
                        callBack.populateData("" + id);
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Log.d("test18", "fail");
                        callBack.hideProgressBarOnFailure();
                    }
                });
    }
}