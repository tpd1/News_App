package com.example.newsapp.model

import android.util.Log
import androidx.constraintlayout.widget.ConstraintSet.Transform
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Constants
import com.example.newsapp.data.DataStoreRepo
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class TopicViewModel(private val dataRepo: DataStoreRepo) : ViewModel() {

    //Store which topics are selected for use with tab layout and count
    private var topicsList: HashMap<String, Boolean> = hashMapOf(
        Constants.BUSINESS to false,
        Constants.ENTERTAINMENT to false,
        Constants.ENVIRONMENT to false,
        Constants.FOOD to false,
        Constants.HEALTH to false,
        Constants.POLITICS to false,
        Constants.SCIENCE to false,
        Constants.SPORTS to false,
        Constants.TECHNOLOGY to false,
    )


    // Linked to
    val businessEnabled: LiveData<Boolean> = dataRepo.businessEnabled.asLiveData()
    val entertainmentEnabled: LiveData<Boolean> = dataRepo.entertainmentEnabled.asLiveData()
    val environmentEnabled: LiveData<Boolean> = dataRepo.environmentEnabled.asLiveData()
    val foodEnabled: LiveData<Boolean> = dataRepo.foodEnabled.asLiveData()
    val healthEnabled: LiveData<Boolean> = dataRepo.healthEnabled.asLiveData()
    val politicsEnabled: LiveData<Boolean> = dataRepo.politicsEnabled.asLiveData()
    val scienceEnabled: LiveData<Boolean> = dataRepo.scienceEnabled.asLiveData()
    val sportsEnabled: LiveData<Boolean> = dataRepo.sportsEnabled.asLiveData()
    val technologyEnabled: LiveData<Boolean> = dataRepo.technologyEnabled.asLiveData()


    fun setBusinessEnabled(value: Boolean) {
        topicsList[Constants.BUSINESS] = value
        viewModelScope.launch {
            dataRepo.setBusinessEnabled(value)
        }
    }

    fun setEntertainmentEnabled(value: Boolean) {
        topicsList[Constants.ENTERTAINMENT] = value
        viewModelScope.launch {
            dataRepo.setEntertainmentEnabled(value)
        }
    }

    fun setEnvironmentEnabled(value: Boolean) {
        topicsList[Constants.ENVIRONMENT] = value
        viewModelScope.launch {
            dataRepo.setEnvironmentEnabled(value)
        }
    }

    fun setFoodEnabled(value: Boolean) {
        topicsList[Constants.FOOD] = value
        viewModelScope.launch {
            dataRepo.setFoodEnabled(value)
        }
    }

    fun setHealthEnabled(value: Boolean) {
        topicsList[Constants.HEALTH] = value
        viewModelScope.launch {
            dataRepo.setHealthEnabled(value)
        }
    }

    fun setPoliticsEnabled(value: Boolean) {
        topicsList[Constants.POLITICS] = value
        viewModelScope.launch {
            dataRepo.setPoliticsEnabled(value)
        }
    }

    fun setScienceEnabled(value: Boolean) {
        topicsList[Constants.SCIENCE] = value
        viewModelScope.launch {
            dataRepo.setScienceEnabled(value)
        }
    }

    fun setSportsEnabled(value: Boolean) {
        topicsList[Constants.SPORTS] = value
        viewModelScope.launch {
            dataRepo.setSportsEnabled(value)
        }
    }

    fun setTechnologyEnabled(value: Boolean) {
        topicsList[Constants.TECHNOLOGY] = value
        viewModelScope.launch {
            dataRepo.setTechnologyEnabled(value)
        }
    }

    // Use for setting all topics on or off, Not implemented in XML yet.
    fun setAllTopics(value: Boolean) {
        topicsList.replaceAll { _, _ -> value }
        viewModelScope.launch {
            dataRepo.setAllTopics(value)
        }
    }

    fun countTopics(): Int {
        return Collections.frequency(topicsList.values, true)
    }


}