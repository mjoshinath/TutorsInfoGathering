package helper;

/**
 * Created by ehc on 1/4/15.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

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

public class WebserviceHelper {
    public static AsyncHttpClient client = new AsyncHttpClient();
    RequestParams requestParams = new RequestParams();
    Context context;
    SharedPreferences preferences;

    public WebserviceHelper(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("session", Context.MODE_MULTI_PROCESS);
    }

    public void getData(final WebServiceCallBack callBack, String type) {
        SharedPreferences categorySharedPref = context.getSharedPreferences("categories", Context.MODE_MULTI_PROCESS);
        final SharedPreferences.Editor categoryEditor = categorySharedPref.edit();
        if (type.equalsIgnoreCase("categories"))
            client.addHeader("If-None-Match", categorySharedPref.getString("etag", ""));
        client.get("http://192.168.1.115:5000/api/v1/" + type, new AsyncHttpResponseHandler() {
            //            192.168.1.132:5000/api/v1/staff_targets/staff/id
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
                callBack.hideProgressBarOnFailure("");
            }
        });
    }

    public void postData(final WebServiceCallBack callBack, StringEntity entity, final long id, String requestType) {

        client.post(context, "http://192.168.1.115:5000/api/v1/" + requestType, entity, "application/json",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String response = new String(bytes);
                        Log.d("test18", "success:" + response);
                        if (response.contains("Email exists")) {
                            Toast.makeText(context, "Email Already Exists!", Toast.LENGTH_SHORT).show();
                            callBack.populateData("" + id);
                        } else if (response.contains("Successfully created")) {
                            Toast.makeText(context, "Registration Successful!", Toast.LENGTH_SHORT).show();
//                            callBack.populateData("" + id);
                        } else {
                            Toast.makeText(context, "Empty Record!", Toast.LENGTH_SHORT).show();
                            callBack.populateData("" + id);
                        }

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Log.d("test18", "fail");
                        callBack.hideProgressBarOnFailure("" + id);
                    }
                });
    }
}
