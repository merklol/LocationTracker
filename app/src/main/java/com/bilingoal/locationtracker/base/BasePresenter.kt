package com.bilingoal.locationtracker.base

open class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {
    var view: V? = null

    override fun subscribe(view: V) {
        this.view = view
    }

    override fun unsubscribe() {
        this.view = null
    }
}