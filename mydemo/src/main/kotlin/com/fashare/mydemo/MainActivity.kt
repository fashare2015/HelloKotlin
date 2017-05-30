package com.fashare.mydemo

import android.app.Activity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.fashare.mydemo.data.HomeInfo
import com.fashare.no_view_holder.NoViewHolder
import com.fashare.no_view_holder.annotation.click.BindItemClick
import com.fashare.no_view_holder.widget.NoOnItemClickListener
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    // retrofit 网络请求
    val retrofit = Retrofit.Builder().baseUrl(Api.BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // NoViewHolder! 所有 view 的容器
    var mNoViewHolder: NoViewHolder? = null

    // 下拉刷新
    @BindView(R.id.srl_refresh)
    var mSrlRefresh: SwipeRefreshLayout? = null

    // 点击事件
    @BindItemClick(id = R.id.rv_story)
    internal var clickMeiZhi = NoOnItemClickListener<HomeInfo.Story> { view, data, pos -> toast("click: $pos, $data") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        mNoViewHolder = NoViewHolder.Builder(this)
                .initView(HomeInfo())
                .build()

        initView()
        loadData()
    }

    private fun initView() {
        mSrlRefresh?.setOnRefreshListener { loadData() }
    }

    private fun loadData() {
        mSrlRefresh?.setRefreshing(true)

        retrofit.create(Api::class.java)
                .getHomeInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mNoViewHolder?.notifyDataSetChanged(it)  // mHomeInfo 发生变化, 通知 UI 及时刷新
                }, {
                    toast("服务器跑路啦~")
                }, {
                    mSrlRefresh?.setRefreshing(false)
                })
    }
}

fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
