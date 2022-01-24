package com.asifddlks.icinema.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asifddlks.icinema.constants.UrlConstants
import com.asifddlks.icinema.model.TitleModel
import com.asifddlks.icinema.network.ApiClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class ExploreSearchResultViewModel : ViewModel() {

    val TAG = this.javaClass.simpleName

    val movieList = MutableLiveData<List<TitleModel>>()
    val errorMessage = MutableLiveData<String>()

    fun searchMovie(query: String) {

        ApiClient().get(
            UrlConstants().getSearchUrl(query),
            true,
            object : ApiClient.OnApiCallbackEventListener {
                override fun onSuccess(response: String?) {

                    val jsonObj = JSONObject(response)
                    val results = jsonObj.getJSONArray("results")
                    val gson = Gson()
                    val itemType = object : TypeToken<List<TitleModel>>() {}.type
                    val itemList = gson.fromJson<List<TitleModel>>(results.toString(), itemType)
                    movieList.postValue(itemList)

                }

                override fun onFailure(error: String?) {
                    errorMessage.postValue(error.toString())
                }

            })


    }
}