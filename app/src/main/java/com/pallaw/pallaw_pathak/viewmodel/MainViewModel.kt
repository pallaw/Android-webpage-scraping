package com.pallaw.pallaw_pathak.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pallaw.pallaw_pathak.data.ApiFactory
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.nodes.Document

/**
 * Created by Pallaw Pathak on 2020-03-13. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
class MainViewModel : ViewModel() {

    val truecaller10thCharacterRequest = MutableLiveData<String>()
    val truecallerEvery10thCharacterRequest = MutableLiveData<String>()
    val truecallerWordCounterRequest = MutableLiveData<String>()

    fun startRequests() {

        //fetch data from webpage and show 10th character from the response
        request10thCharacter()

        //fetch data from webpage and show every 10th (10th, 20th, 30th ....) character from the response
        requestEvery10thCharacter()

        //fetch data from webpage and show every unique word with its occurrence counter
        requestWordOccuranceWithCounter()
    }

    private fun requestWordOccuranceWithCounter() {
        ApiFactory.TRUE_CALLER_API.getBlogData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { doc: Document -> doc.text().split("\\s+".toRegex()) }
            .subscribe(object : SingleObserver<List<String>> {
                override fun onSuccess(responseList: List<String>) {
                    val frequenciesByFirstChar = responseList.groupingBy { it }.eachCount()

                    val wordCounterMap = StringBuilder()
                    for (entry in frequenciesByFirstChar.entries) {
                        wordCounterMap.append(entry)
                        wordCounterMap.append("\n")
                    }
                    truecallerWordCounterRequest.value = wordCounterMap.toString()
                }

                override fun onSubscribe(d: Disposable) {
                    truecallerWordCounterRequest.value = "Loading..."
                }

                override fun onError(e: Throwable) {
                }

            })
    }

    private fun request10thCharacter() {
        ApiFactory.TRUE_CALLER_API.getBlogData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { doc: Document -> doc.text().substring(9, 10) }
            .subscribe(object : SingleObserver<String> {
                override fun onSuccess(t: String) {
                    truecaller10thCharacterRequest.value = t
                }

                override fun onSubscribe(d: Disposable) {
                    truecaller10thCharacterRequest.value = "Loading..."
                }

                override fun onError(e: Throwable) {
                }

            })
    }

    private fun requestEvery10thCharacter() {
        ApiFactory.TRUE_CALLER_API.getBlogData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { doc: Document -> doc.text() }
            .subscribe(object : SingleObserver<String> {
                override fun onSuccess(response: String) {
                    val arrayBuilder = StringBuilder()
                    response.toCharArray().forEachIndexed { index, char ->
                        if (index == 10) {
                            arrayBuilder.append(char)
                            truecallerEvery10thCharacterRequest.value = arrayBuilder.toString()
                            return@forEachIndexed
                        }
                        if (index % 10 == 0) {
                            arrayBuilder.append(", ")
                            arrayBuilder.append(char)
                            truecallerEvery10thCharacterRequest.value = arrayBuilder.toString()
                        }
                    }
                }

                override fun onSubscribe(d: Disposable) {
                    truecallerEvery10thCharacterRequest.value = "Loading..."
                }

                override fun onError(e: Throwable) {
                }

            })
    }
}