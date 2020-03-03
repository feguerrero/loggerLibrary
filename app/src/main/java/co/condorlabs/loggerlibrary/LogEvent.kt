package co.condorlabs.loggerlibrary

import android.app.Activity

/**
 * @author Felipe E Guerrero
 */
class LogEvent(
    val type: String,
    val params: HashMap<String, Any?>?,
    val description: String?,
    val activity: Activity?,
    val tag: String?
) {

    constructor(
        type: String,
        params: HashMap<String, Any?>? = null,
        description: String? = null
    ) : this(type, params, description, null, null)

    constructor(
        type: String,
        activity: Activity,
        tag: String?
    ) : this(type, null, null, activity, tag)
}
