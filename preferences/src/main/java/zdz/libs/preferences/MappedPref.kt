package zdz.libs.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.contracts.Serializer

internal class MappedPref<S, T>(
    internal val pref: Pref<S>,
    private val serializer: Serializer<S, T>,
) : Pref<T> {
    private val serialize = serializer::serialize
    private val deserialize = serializer::deserialize
    override val name: String
        get() = pref.name
    override val default = serialize(pref.default)
    override val flow: Flow<T> = pref.flow.map { it?.let { s -> serialize(s) } ?: default }
    override suspend fun edit(block: suspend (T) -> T?): T? =
        pref.edit { block(serialize(it))?.let(deserialize) ?: pref.default }?.let { serialize(it) }

    override suspend fun delete() = pref.delete()
    fun <R> map(otherSerializer: Serializer<T, R>) = MappedPref(pref, serializer * otherSerializer)
}