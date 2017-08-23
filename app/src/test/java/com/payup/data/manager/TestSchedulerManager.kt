package com.payup.data.manager

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulerManager : SchedulerManager() {

    override fun mainThread(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun workThread(): Scheduler {
        return Schedulers.trampoline()
    }
}
