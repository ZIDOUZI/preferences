package zdz.libs.preferences.dispresed

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import zdz.libs.preferences.annotations.SubtlePrefApi
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.utils.setOrDelete
import java.io.IOException

@SubtlePrefApi
class CombinedPref<T>(
    private val ds: DataStore<Preferences>,
    override val name: String,
    default: List<Pair<Preferences.Key<T & Any>, T>> = listOf(),
) : Pref<Map<String, T>> {
    
    override val default: Map<String, T> = default.associate { (k, v) -> k.name to v }
    private val keys: List<Preferences.Key<T & Any>> = default.map { it.first }
    
    override val flow: Flow<Map<String, T>>
        get() = ds.data.catch { if (it is IOException) emptyPreferences() else throw it }.map {
            keys.associate { key -> key.name to it.getValue(key) }
        }

    private fun Preferences.getValue(key: Preferences.Key<T & Any>): T =
        this[key] ?: default[key.name] ?: error("No default value for key ${key.name}")

    override suspend fun delete() {
        ds.edit { keys.forEach { key -> it.remove(key) } }
    }

    override suspend fun edit(block: suspend (Map<String, T>) -> Map<String, T>?): Map<String, T> =
        ds.edit { p ->
            keys.associate { it.name to p.getValue(it) }.let { block(it) }?.forEach { (k, v) ->
                p.setOrDelete(keys.first { key -> key.name == k }, v)
            } ?: delete()
        }.let { p -> keys.associate { it.name to p.getValue(it) } }
}