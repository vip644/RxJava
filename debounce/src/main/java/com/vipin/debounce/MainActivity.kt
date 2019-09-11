package com.vipin.debounce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var TAG = MainActivity::class.java.name

    private lateinit var mSearchView: SearchView
    private lateinit var mDisposable: CompositeDisposable
    private var timeInMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timeInMillis = System.currentTimeMillis()
        mDisposable = CompositeDisposable()
        mSearchView = findViewById(R.id.searchView)

        val querySearchView = querySearchObservable().debounce(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())

        querySearchView.subscribe(object : Observer<String> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                mDisposable.add(d)
            }

            override fun onNext(t: String) {
                if (t.isNotEmpty()) {
                    Log.e(
                        TAG,
                        "onNext since last request : ${System.currentTimeMillis() - timeInMillis}"
                    )
                    Log.e(TAG, "onNext search query string: $t")
                    timeInMillis = System.currentTimeMillis()
                }

            }

            override fun onError(e: Throwable) {
                Log.e(TAG, e.message)
            }

        })

    }

    private fun querySearchObservable(): Observable<String> {

        return Observable.create { emitter ->

            mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!emitter.isDisposed) {
                        newText?.let { emitter.onNext(it) }
                    }
                    return false
                }
            })
        }
    }
}
