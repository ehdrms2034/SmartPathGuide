/*
 * Created by Lee Oh Hyoung on 2020/05/26 .. 
 */
package kr.pnu.ga2019

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class SmartPathGuide : Application() {

    companion object {
        private const val TAG: String = "SmartPathGuide"
        private const val PRINT_STACK_COUNT: Int = 5
    }

    override fun onCreate() {
        super.onCreate()
        setLogger()
    }

    private fun setLogger() {
        PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)
            .tag(TAG)
            .methodCount(PRINT_STACK_COUNT)
            .build()
            .let { formatStrategy ->
                Logger.addLogAdapter(
                    object: AndroidLogAdapter(formatStrategy) {
                        override fun isLoggable(priority: Int, tag: String?): Boolean {
                            // DEBUG 모드에서만 로그 출력
                            return BuildConfig.DEBUG
                        }
                    }
                )
            }
    }
}
