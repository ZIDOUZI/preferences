package zdz.libs.preferences.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import zdz.libs.preferences.PrefImpl
import zdz.libs.preferences.contracts.Pref
import kotlin.properties.ReadOnlyProperty

inline operator fun <reified E : Enum<E>> DataStore<Preferences>.get(
    default: E,
    values: Array<E> = enumValues(),
) = ReadOnlyProperty<Any?, Pref<E>> { _, property ->
    PrefImpl(this, default.ordinal, intPreferencesKey(property.name)).map(
        { it.let { i -> values[i] } },
        { it.ordinal }
    )
}

@Deprecated(
    "This method gives a Flow<T?> but never be null.",
    ReplaceWith("this.get(default)"),
    DeprecationLevel.HIDDEN
)
inline fun <reified E : Enum<E>> DataStore<Preferences>.nullable(
    default: E,
    values: Array<E> = enumValues(),
) = ReadOnlyProperty<Any?, Pref<E?>> { _, property ->
    PrefImpl(this, null, intPreferencesKey(property.name)).map(
        { it?.let { i -> values[i] } ?: default },
        { it?.ordinal }
    )
}


inline fun <reified E : Enum<E>> DataStore<Preferences>.enum(values: Array<E> = enumValues()) =
    ReadOnlyProperty<Any?, Pref<E?>> { _, property ->
        PrefImpl(this, null, intPreferencesKey(property.name)).map(
            convert = { it?.let { i -> values[i] } },
            reverse = { it?.ordinal }
        )
    }

inline fun <reified E : Enum<E>> DataStore<Preferences>.enumSet(
    values: Array<E>,
    default: Set<E> = setOf(),
) = ReadOnlyProperty<Any?, Pref<Set<E>>> { _, property ->
    PrefImpl(this, default.map { it.name }.toSet(), stringSetPreferencesKey(property.name)).map(
        convert = { it.map { s -> enumValueOf<E>(s) }.filter { e -> e in values }.toSet() },
        reverse = { it.map { e -> e.name }.toSet() }
    )
}

inline fun <reified E : Enum<E>> DataStore<Preferences>.enumSet(
    default: Set<E> = setOf(),
) = ReadOnlyProperty<Any?, Pref<Set<E>>> { _, property ->
    PrefImpl(this, default.map { it.name }.toSet(), stringSetPreferencesKey(property.name)).map(
        convert = { it.map { s -> enumValueOf<E>(s) }.toSet() },
        reverse = { it.map { e -> e.name }.toSet() }
    )
}

inline fun <reified E : Enum<E>> DataStore<Preferences>.enumList(
    default: List<E> = listOf(),
) = ReadOnlyProperty<Any?, Pref<List<E>>> { _, property ->
    PrefImpl(this, default.joinToString(), stringPreferencesKey(property.name)).map(
        convert = { it.splitToEnum() },
        reverse = { it.joinToString() }
    )
}

inline fun <reified E : Enum<E>> DataStore<Preferences>.enumList(
    values: Array<E>,
    default: List<E> = listOf(),
) = ReadOnlyProperty<Any?, Pref<List<E>>> { _, property ->
    PrefImpl(this, default.joinToString(), stringPreferencesKey(property.name)).map(
        convert = { it.splitToEnum<E>().filter { e -> e in values } },
        reverse = { it.joinToString() }
    )
}

inline fun <reified E : Enum<E>> String.splitToEnum(): List<E> =
    split(", ").mapNotNull { s -> if (s == "") null else enumValueOf<E>(s) }