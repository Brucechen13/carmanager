package com.cc.carmanager.net;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import com.cc.carmanager.MyApplication;
import com.cc.carmanager.util.LoginHelper;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dllo on 16/9/9.
 * Volley使用单例
 */
public class VolleyInstance {
    /**
     * 双重校验法写单例
     */

    private static VolleyInstance volleyInstance;

    private RequestQueue queue;

    //私有化构造方法,外部不能随意的创建对象
    private VolleyInstance() {
        queue = Volley.newRequestQueue(MyApplication.getContext());
    }

    //对外提供获取对象的静态方法
    public static VolleyInstance getVolleyInstance() {
        if (volleyInstance == null) {
            synchronized (VolleyInstance.class) {
                if (volleyInstance == null) {
                    volleyInstance = new VolleyInstance();
                }
            }
        }
        return volleyInstance;
    }

    /**
     * 对外提供请求方法
     *
     * @param url    输入的请求网址
     * @param result
     */
    public void startRequest(String url, final VolleyResult result) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result.success(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result.failure();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap localHashMap = new HashMap();
                localHashMap.put("Cookie", LoginHelper.instance.getSettingNote());//向请求头部添加Cookie
                return localHashMap;
            }
        };
        queue.add(stringRequest);
    }

    public void starImageRequest(String url, int maxWidth, int maxHeight, ImageView.ScaleType scaleType, Bitmap.Config config, final VolleyImageResult result) {
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                result.success(response);
            }
        }, maxWidth, maxHeight, scaleType, config, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result.failure();
            }
        });
        queue.add(imageRequest);
    }

    /**
     * 对外提供请求的方法
     * 利用json进行解析
     */
    public void startJsonObjectRequest(String url, final VolleyResult result) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                result.success(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result.failure();
            }
        });
        queue.add(jsonObjectRequest);
    }

    /**
     * 对外提供Post的方法
     * 利用json进行解析
     */
    public void startJsonObjectPost(String url, final Map<String, String> obj, final VolleyResult result) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result.success(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result.failure();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return obj;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Map<String, String> responseHeaders = response.headers;
                    String rawCookies = responseHeaders.get("Set-Cookie");
                    LoginHelper.instance.saveSettingNote(rawCookies);//保存Cookie
                    String dataString = new String(response.data, "UTF-8");
                    return Response.success(dataString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap localHashMap = new HashMap();
                localHashMap.put("Cookie", LoginHelper.instance.getSettingNote());//向请求头部添加Cookie
                return localHashMap;
            }
        };
        queue.add(stringRequest);
    }


}
