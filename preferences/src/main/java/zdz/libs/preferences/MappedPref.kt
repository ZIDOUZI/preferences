package zdz.libs.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import zdz.libs.preferences.contracts.CachedPref
import zdz.libs.preferences.contracts.Serializer
import zdz.libs.preferences.utils.setOrDelete
import kotlin.reflect.KFunction1

internal class MappedPref<S, T>(
    private val ds: DataStore<Preferences>,
    override val default: T,
    internal val key: Preferences.Key<S & Any>,
    private val serializer: Serializer<S, T>,
    private val cache: Boolean = false,
) : CachedPref<T> {

    override val name: String = key.name
    private val serialize: KFunction1<S, T> = serializer::serialize
    private val deserialize: KFunction1<T, S> = serializer::deserialize

    private val deserialized = deserialize(default)
    private fun Preferences.getValue(): S = this[key] ?: deserialized
    override val flow: Flow<T> =
        ds.data.catch { if (it is Exception) emptyPreferences() else throw it }
            .map { serialize(it[key] ?: deserialized) }

    override suspend fun edit(block: suspend (T) -> T?): T? = ds.edit l@{ p ->
        if (cacheValue == p[key]) return@l
        block(serialize(p.getValue())).let {
            cacheValue = it ?: default
            p.setOrDelete(key, it?.let { deserialize(it) })
        }
    }.let { serialize(it.getValue()) }

    override suspend fun delete() {
        ds.edit { it.remove(key) }
        cacheValue = default
    }

    override var cacheValue: T? = null
        get() = if (cache) field else error("This Pref is not cached.")

    fun <R> map(otherSerializer: Serializer<T, R>) =
        MappedPref(ds, otherSerializer.serialize(default), key, serializer * otherSerializer)
}