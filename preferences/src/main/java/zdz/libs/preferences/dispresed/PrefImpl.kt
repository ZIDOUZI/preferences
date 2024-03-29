package zdz.libs.preferences.dispresed

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.utils.setOrDelete
import java.io.IOException

internal class PrefImpl<T>(
    private val ds: DataStore<Preferences>,
    override val default: T,
    internal val key: Preferences.Key<T & Any>,
) : Pref<T> {
    override val name: String = key.name

    override val flow: Flow<T> =
        ds.data.catch { if (it is IOException) emptyPreferences() else throw it }.map {
            it[key] ?: default
        }

    override suspend fun edit(block: suspend (T) -> T?): T? = ds.edit { p ->
        block(p[key] ?: default).let { p.setOrDelete(key, it) }
    }[key]

    override suspend fun delete() {
        ds.edit { it.remove(key) }
    }
}