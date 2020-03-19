import android.util.Log
import androidx.annotation.NonNull
import timber.log.Timber

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/3/10 下午3:16
 * @description: 打印release version log
 **/

class ReleaseCrashTimberTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, @NonNull message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }
        FakeCrashLibrary.log(priority, tag, message)
        if (t != null) {
            if (priority == Log.ERROR) {
                FakeCrashLibrary.logError(t)
            } else if (priority == Log.WARN) {
                FakeCrashLibrary.logWarning(t)
            }
        }
    }
}

/** Not a real crash reporting library!  */
class FakeCrashLibrary private constructor() {
    companion object {
        fun log(priority: Int, tag: String?, message: String?) {
            // TODO add log entry to circular buffer.
        }
        
        fun logWarning(t: Throwable?) {
            // TODO report non-fatal warning.
        }
        
        fun logError(t: Throwable?) {
            // TODO report non-fatal error.
        }
    }
    
    init {
        throw AssertionError("No instances.")
    }
}
