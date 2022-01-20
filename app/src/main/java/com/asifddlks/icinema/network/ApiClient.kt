package com.asifddlks.icinema.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.asifddlks.icinema.AppPrefs
import com.asifddlks.icinema.R
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.*

//
// Created by Asif Ahmed on 20/1/22.
//
class ApiClient {
    private val VOLLEY_TIMEOUT_MS = 30000
    var HeaderKey = "Authorization"
    var SessionKey = "Session"
    private lateinit var mOnApiCallbackEventListener: OnApiCallbackEventListener

    // Gets StringObject
    operator fun get(
        context: Context,
        requestURL: String,
        useHeader: Boolean,
        onApiCallbackEventListener: OnApiCallbackEventListener
    ) {
        Log.d("ApiClient", "url: $requestURL")
        if (isInternetConnected(context, true)) {
            val stringRequest: StringRequest = object : StringRequest(
                Method.GET, requestURL,
                Response.Listener { response ->
                    Log.d("ApiClient", "response: $response")
                    onApiCallbackEventListener.onSuccess(response)
                },
                Response.ErrorListener { error ->
                    Log.e("ApiClient", "error: " + error.localizedMessage)
                    onApiCallbackEventListener.onFailure(error.localizedMessage)
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    if (useHeader) {
                        try {
                            params[HeaderKey] = "bearer " + AppPrefs(context).getAccessToken()
                        } catch (ex: Exception) {
                            //TODO: catch
                        }
                    }
                    return params
                }
            }
            stringRequest.retryPolicy = DefaultRetryPolicy(
                VOLLEY_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        } else {
            onApiCallbackEventListener.onFailure("No internet")
        }
    }

    // Posts StringObject
    fun post(
        context: Context,
        requestURL: String,
        parameters: Map<String, String>?,
        useHeader: Boolean,
        onApiCallbackEventListener: OnApiCallbackEventListener
    ) {
        Log.d("ApiClient", "url: $requestURL")
        if (isInternetConnected(context, true)) {
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, requestURL,
                Response.Listener { response ->
                    Log.d("ApiClient", "response: $response")
                    onApiCallbackEventListener.onSuccess(response)
                },
                Response.ErrorListener { error ->
                    Log.e("ApiClient", "error: " + error.localizedMessage)
                    try {
                        if (error.networkResponse.data != null) {
                            val body = String(error.networkResponse.data, StandardCharsets.UTF_8)
                            val jsonObject = JSONObject(body)
                            val errorMsg = jsonObject.getString("error")
                            onApiCallbackEventListener.onFailure(errorMsg)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    //onApiCallbackEventListener.onFailure(error.getLocalizedMessage());
                }
            ) {
                override fun getParams(): Map<String, String>? {
                    return parameters
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    if (useHeader) {
                        try {
                            params[HeaderKey] = "bearer " + AppPrefs(context).getAccessToken()
                        } catch (ex: Exception) {
                            //TODO: catch
                        }
                    }
                    return params
                }
            }
            stringRequest.retryPolicy = DefaultRetryPolicy(
                VOLLEY_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        } else {
            onApiCallbackEventListener.onFailure("No internet")
        }
    }


    // Posts StringObject
    fun postWithRawBody(
        context: Context,
        requestURL: String,
        jsonObject: JSONObject?,
        useHeader: Boolean,
        onApiCallbackEventListener: OnApiCallbackEventListener
    ) {
        Log.d("ApiClient", "url: $requestURL")
        if (isInternetConnected(context, true)) {
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, requestURL, jsonObject,
                { response ->
                    Log.d("ApiClient", "response: $response")
                    onApiCallbackEventListener.onSuccess(Gson().toJson(response))
                }
            ) { error ->
                Log.e("ApiClient", "error: " + error.localizedMessage)
                try {
                    if (error.networkResponse.data != null) {
                        val body = String(
                            error.networkResponse.data,
                            StandardCharsets.UTF_8
                        )
                        val jsonObject = JSONObject(body)
                        val errorMsg = jsonObject.getString("error")
                        onApiCallbackEventListener.onFailure(errorMsg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
                VOLLEY_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(jsonObjectRequest)
        } else {
            onApiCallbackEventListener.onFailure("No internet")
        }
    }


    fun isInternetConnected(context: Context, isNoInternetToast: Boolean): Boolean {
        if (isNetworkAvailable(context)) {
            return true
        } else {
            if (isNoInternetToast) {
                Toast.makeText(context, R.string.check_your_internet_connection, Toast.LENGTH_SHORT)
                    .show()
            }
            return false
        }
    }

    fun isNetworkAvailable(context: Context) =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }

    fun setNewApiCallbackEventListener(eventListener: OnApiCallbackEventListener) {
        mOnApiCallbackEventListener = eventListener
    }

    fun onSuccess(response: String?) {
        if (mOnApiCallbackEventListener != null) {
            mOnApiCallbackEventListener!!.onSuccess(response)
        }
    }

    fun onFailure(error: String?) {
        if (mOnApiCallbackEventListener != null) {
            mOnApiCallbackEventListener!!.onFailure(error)
        }
    }

    interface OnApiCallbackEventListener {
        fun onSuccess(response: String?)
        fun onFailure(error: String?)
    }
}