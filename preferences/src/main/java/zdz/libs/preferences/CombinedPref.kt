package zdz.libs.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import zdz.libs.preferences.annotations.ExperimentalPrefApi
import zdz.libs.preferences.annotations.SubtlePrefApi
import zdz.libs.preferences.utils.setOrDelete
import java.io.IOException

@ExperimentalPrefApi
@SubtlePrefApi
class CombinedPref<T>(
    private val ds: DataStore<Preferences>,
    val default: Map<String, T>,
    val name: String,
    private val keys: MutableList<Preferences.Key<T & Any>> = mutableListOf(),
) {
    val flow: Flow<Map<String, T>>
        get() = ds.data.catch { if (it is IOException) emptyPreferences() else throw it }.map {
            keys.associate { key ->
                name + key.name to it.getValue(key)
            }
        }

    private fun Preferences.getValue(key: Preferences.Key<T & Any>): T =
        this[key] ?: default[key.name] ?: error("No default value for key ${key.name}")

    private fun Preferences.Key<T & Any>.ensureIn() = also { if (it !in keys) keys.add(it) }

    suspend fun delete() {
        ds.edit { keys.forEach { key -> it.remove(key) } }
    }

    suspend fun edit(key: Preferences.Key<T & Any>, block: suspend (T) -> T) = ds.edit { p ->
        p.setOrDelete(key.ensureIn(), block(p.getValue(key)))
    }[key]

    suspend fun emit(key: Preferences.Key<T & Any>, value: T) = edit(key) { value }

}