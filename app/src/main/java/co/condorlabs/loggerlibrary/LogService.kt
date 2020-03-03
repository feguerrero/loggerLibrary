package co.condorlabs.loggerlibrary

import android.os.Build
import android.util.Log
import java.util.regex.Pattern

/**
 * @author Felipe E Guerrero
 */
abstract class LogService {

    private fun getTag(): String? {
        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size <= CALL_STACK_INDEX) {
            throw IllegalStateException(
                "Synthetic stacktrace didn't have enough elements: are you using proguard?"
            )
        }
        return createStackElementTag(stackTrace[CALL_STACK_INDEX])
    }

    private fun createStackElementTag(element: StackTraceElement): String {
        var className = element.className
        fun removeAnonymousClassName(): String {
            val m = anonymousClassRegex.matcher(className)
            if (m.find()) {
                className = m.replaceAll(String())
            }
            return className
        }
        className = removeAnonymousClassName()
        val tag = className.substring(className.lastIndexOf('.') + 1)
        // Tag length limit was removed in API 24.
        return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tag
        } else tag.substring(0, MAX_TAG_LENGTH)
    }

    fun v(priority: Int, message: String) {
        prepareLog(priority, null, message)
    }

    fun v(priority: Int, throwable: Throwable) {
        prepareLog(priority, throwable, null)
    }

    fun d(priority: Int, message: String) {
        prepareLog(priority, null, message)
    }

    fun d(priority: Int, throwable: Throwable) {
        prepareLog(priority, throwable, null)
    }

    fun i(priority: Int, message: String) {
        prepareLog(priority, null, message)
    }
    fun i(priority: Int, throwable: Throwable) {
        prepareLog(priority, throwable, null)
    }

    fun e(priority: Int, message: String) {
        prepareLog(priority, null, message)
    }
    fun e(priority: Int, throwable: Throwable) {
        prepareLog(priority, throwable, null)
    }

    fun w(priority: Int, message: String) {
        prepareLog(priority, null, message)
    }
    fun w(priority: Int, throwable: Throwable) {
        prepareLog(priority, throwable, null)
    }

    fun log(priority: Int, message: String) {
        prepareLog(priority, null, message)
    }

    fun log(priority: Int, throwable: Throwable) {
        prepareLog(priority, throwable, null)
    }

    fun logEvent(wEvent: LogEvent) {
        logEvent(getTag(), wEvent)
    }

    private fun prepareLog(
        priority: Int,
        throwable: Throwable?,
        message: String?
    ) {
        var messageToShow = message ?: String()

        if (message.isNullOrBlank()) {
            if (throwable == null) {
                return // Swallow message if it's null and there's no throwable.
            }
            messageToShow = Log.getStackTraceString(throwable)
        } else {
            if (throwable != null) {
                messageToShow += "\n" + Log.getStackTraceString(throwable)
            }
        }

        log(priority, getTag(), messageToShow, throwable)
    }

    abstract fun log(priority: Int, tag: String?, message: String, throwable: Throwable?)

    abstract fun logEvent(tag: String?, logEvent: LogEvent)

    abstract fun logKeyEvent(key: String, value: String)

    abstract fun logKeyEvent(key: String, value: Boolean)

    abstract fun logKeyEvent(key: String, value: Float)

    abstract fun logKeyEvent(key: String, value: Double)

    abstract fun logKeyEvent(key: String, value: Int)

    companion object {
        private const val MAX_TAG_LENGTH = 23
        private const val CALL_STACK_INDEX = 4
        private val anonymousClassRegex = Pattern.compile("(\\$\\d+)+$")
    }
}
