/*
 * Created by Lee Oh Hyoung on 2020/05/28 .. 
 */
package kr.pnu.ga2019.util

import io.reactivex.Scheduler

interface BaseSchedulerProvider {

    fun io(): Scheduler

    fun computation(): Scheduler

    fun trampoline(): Scheduler

    fun mainThread(): Scheduler
}
