package com.task.composelistviewwithroomdb.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.composelistviewwithroomdb.data.entity.DataEntity
import com.task.composelistviewwithroomdb.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository, @ApplicationContext private val mContext: Context,
) : ViewModel() {

    private val _dataDetailsList = MutableStateFlow(emptyList<DataEntity>())
    val dataDetailsList = _dataDetailsList.asStateFlow()

    private var inactivityJob: Job? = null
    private val _shouldExitApp = MutableLiveData<Boolean>()
    val shouldExitApp: LiveData<Boolean> get() = _shouldExitApp

    // Timeout duration in milliseconds
    private val inactivityTimeout = 30000L // Example: 30 seconds timeout

    private val viewModelScope = CoroutineScope(Dispatchers.Main + Job())

    fun resetInactivityTimer() {
        // Cancel any existing inactivity job
        inactivityJob?.cancel()
        println("bhavani resetInactivityTimer")

        // Start a new coroutine to handle the inactivity timeout
        inactivityJob = viewModelScope.launch {
            println("bhavani launch $inactivityTimeout")

            delay(inactivityTimeout)
            // After the delay, trigger the app exit action via LiveData
            _shouldExitApp.postValue(true)
        }
    }

    public override fun onCleared() {
        super.onCleared()
        // Cancel any ongoing jobs when ViewModel is cleared
        inactivityJob?.cancel()
    }

    fun getDataDetails() {
        viewModelScope.launch(IO) {
            repository.getAllRecord().collectLatest {
                _dataDetailsList.tryEmit(it)
            }
        }
    }

    fun updateStudent(studentEntity: DataEntity) {
        viewModelScope.launch(IO) {
            repository.update(studentEntity)
        }
    }
    fun getContext():Context{
        return mContext
    }
    fun insertStudent(studentEntity: DataEntity) {
        viewModelScope.launch(IO) {
            try {
                repository.insert(studentEntity)

            }catch (e: Exception) {
                // Handling exceptions for database insertion
                Log.e("UserViewModel", "Error inserting user: ${e.localizedMessage}")
            }
        }
    }


    private val _userData = MutableStateFlow("")
    val userData = _userData.asStateFlow()
    fun setUserData(roll: String) {
        _userData.tryEmit(roll)
    }



    private val _isChecked = MutableStateFlow(false)
    val isChecked = _isChecked.asStateFlow()
    fun setChecked(bool: Boolean) {
        _isChecked.tryEmit(bool)
    }

}