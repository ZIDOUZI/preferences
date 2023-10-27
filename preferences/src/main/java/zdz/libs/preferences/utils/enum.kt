package zdz.libs.preferences.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import zdz.libs.preferences.contracts.Serializer
import zdz.libs.preferences.model.MappedBuilder.Companion.build

@JvmInline
value class EnumSerializer<E : Enum<E>>(private val entries: List<E>) : Serializer<Int, E> {
    override fun deserialize(t: E): Int = t.ordinal
    override fun serialize(s: Int): E = entries[s]
}

@JvmInline
value class NEnumSerializer<E : Enum<E>>(private val entries: List<E>) : Serializer<Int?, E?> {
    override fun deserialize(t: E?): Int? = t?.ordinal
    override fun serialize(s: Int?): E? = s?.let { entries[s] }
}

@JvmInline
value class SEnumSetSerializer<E : Enum<E>>(private val entries: List<E>) :
    Serializer<String, List<E>> {
    override fun deserialize(t: List<E>): String = t.joinToString(", ") { it.name }
    override fun serialize(s: String): List<E> =
        s.split(", ").map { n -> entries.first { it.name == n } }
}

@JvmInline
value class IEnumSetSerializer<E : Enum<E>>(private val entries: List<E>) :
    Serializer<Int, Set<E>> {
    override fun deserialize(t: Set<E>): Int = t.fold(0) { acc, e -> acc or (1 shl e.ordinal) }
    override fun serialize(s: Int): Set<E> =
        entries.filterIndexed { i, _ -> s and (1 shl i) != 0 }.toSet()
}



inline operator fun <reified E : Enum<E>> DataStore<Preferences>.get(
    default: E,
    serializer: Serializer<Int, E> = EnumSerializer(enumValues<E>().toList()),
    cache: Boolean = false,
) = build(default.ordinal, ::intPreferencesKey, serializer, cache)

@Deprecated(
    "This method gives a Flow<T?> but never be null.",
    ReplaceWith("this.get(default)"),
    DeprecationLevel.HIDDEN
)
inline fun <reified E : Enum<E>> DataStore<Preferences>.nullable(
    default: E,
    serializer: Serializer<Int?, E?> = NEnumSerializer(enumValues<E>().toList()),
) = build(default.ordinal, ::intPreferencesKey, serializer)


inline fun <reified E : Enum<E>> DataStore<Preferences>.enum(
    serializer: Serializer<Int?, E?> = NEnumSerializer(enumValues<E>().toList()),
    cache: Boolean = false,
) = build(null, ::intPreferencesKey, serializer, cache)

inline fun <reified E : Enum<E>> DataStore<Preferences>.enumSet(
    default: Set<E> = setOf(),
    serializer: Serializer<Int, Set<E>> = IEnumSetSerializer(enumValues<E>().toList()),
    cache: Boolean = true,
) = build(serializer.deserialize(default), ::intPreferencesKey, serializer, cache)

inline fun <reified E : Enum<E>> DataStore<Preferences>.enumList(
    default: List<E> = listOf(),
    serializer: Serializer<String, List<E>> = SEnumSetSerializer(enumValues<E>().toList()),
    cache: Boolean = true,
) = build(default.joinToString(), ::stringPreferencesKey, serializer, cache)