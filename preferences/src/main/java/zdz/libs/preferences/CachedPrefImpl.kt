package zdz.libs.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import zdz.libs.preferences.contracts.CachedPref
import zdz.libs.preferences.utils.setOrDelete
import java.io.IOException

internal class CachedPrefImpl<T>(
    private val ds: DataStore<Preferences>,
    override val default: T,
    private val key: Preferences.Key<T & Any>
) : CachedPref<T> {
    override val name: String = key.name

    override val flow: Flow<T> =
        ds.data.catch { if (it is IOException) emptyPreferences() else throw it }.map {
            it[key] ?: default
        }

    override suspend fun edit(block: suspend (T) -> T?): T? = ds.edit { p ->
        p.setOrDelete(key, block(p[key] ?: default))
    }[key]

    override suspend fun delete() {
        ds.edit { it.remove(key) }
    }

    override val cacheValue: T
        get() = throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    override val validate: Boolean
        get() = throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
}