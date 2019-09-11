package com.vipin.flatmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vipin.flatmap.model.Comment
import com.vipin.flatmap.model.Post
import com.vipin.flatmap.request.RequestGenerator
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.name

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: DataCommentAdapter
    private lateinit var mDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDisposable = CompositeDisposable()

        mRecyclerView = findViewById(R.id.recyclerView)
        mAdapter = DataCommentAdapter()
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        getPostObservable()
            .subscribeOn(Schedulers.io())
            .flatMap {
                getCommentsObservable(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Post> {

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                    mDisposable.add(d)
                }

                override fun onNext(t: Post) {
                    mAdapter.updatePost(t)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.message)
                }

            })


    }

    private fun getPostObservable(): Observable<Post> {

        return RequestGenerator.create()
            .getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { posts ->
                mAdapter.setPosts(posts)
                Observable.fromIterable(posts)
            }
    }

    private fun getCommentsObservable(post: Post): Observable<Post> {
        return RequestGenerator.create()
            .getComments(post.id)
            .subscribeOn(Schedulers.io())
            .map { t ->
                post.comments = t
                post
            }

    }
}













