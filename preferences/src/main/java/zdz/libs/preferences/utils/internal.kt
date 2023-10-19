package zdz.libs.preferences.utils

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences

internal fun <T> MutablePreferences.setOrDelete(key: Preferences.Key<T & Any>, value: T?): Any =
    value?.let { set(key, it) } ?: remove(key)
