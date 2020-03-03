package co.condorlabs.loggerlibrary

import co.condorlabs.loggerlibrary.LogEvent

/**
 * @author Felipe E Guerrero
 */
interface Logger {

    fun v(message: String)

    fun v(t: Throwable)

    fun d(message: String)

    fun d(t: Throwable)

    fun i(message: String)

    fun i(t: Throwable)

    fun e(message: String)

    fun e(t: Throwable)

    fun w(message: String)

    fun w(t: Throwable)

    fun log(priority: Int, message: String)

    fun log(priority: Int, t: Throwable)

    fun logEvent(wEvent: LogEvent)

    fun logKeyEvent(key: String, value: String)

    fun logKeyEvent(key: String, value: Boolean)

    fun logKeyEvent(key: String, value: Float)

    fun logKeyEvent(key: String, value: Double)

    fun logKeyEvent(key: String, value: Int)
}
