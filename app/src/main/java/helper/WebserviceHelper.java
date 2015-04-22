package helper;

/**
 * Created by ehc on 1/4/15.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
    private TextView toastTextView;

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
        final View layout = setToastLayout();
        client.post(context, "http://192.168.1.115:5000/api/v1/" + requestType, entity, "application/json",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String response = new String(bytes);
                        Log.d("test18", "success:" + response);
                        if (response.contains("Email exists")) {
                            toastTextView.setText("Email exists");
                            callBack.populateData("" + id);
                        } else if (response.contains("Successfully created")) {
                            toastTextView.setText("Registration Successful!");
                            callBack.populateData("" + id);
                        } else {
                            toastTextView.setText("Empty Record!");
                            callBack.populateData("" + id);
                        }
                        toastMessageProperties(layout);
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Log.d("test18", "fail" + i);
                        callBack.hideProgressBarOnFailure("" + id);
                    }
                });
    }

    private View setToastLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.toast_view, null);
        toastTextView = (TextView) layout.findViewById(R.id.toast_message_text_view);
        return layout;
    }

    private void toastMessageProperties(View layout) {
        Toast toast = new Toast(context);
        toast.setView(layout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 36);
        toast.show();
    }
}
