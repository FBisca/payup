package com.payup.data.manager

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SchedulerManager {
    fun workThread(): Scheduler {
        return Schedulers.io()
    }

    fun mainThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}
