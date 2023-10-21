package zdz.libs.preferences.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import zdz.libs.preferences.CombinedPref
import zdz.libs.preferences.MappedPref
import zdz.libs.preferences.annotations.ExperimentalPrefApi
import zdz.libs.preferences.annotations.SubtlePrefApi
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.contracts.Serializer
import kotlin.properties.ReadOnlyProperty

fun <T, R> Pref<T>.map(serializer: Serializer<T, R>): Pref<R> =
    (this as? MappedPref<*, T>)?.map(serializer) ?: MappedPref(this, serializer)

fun <T, R> Pref<T>.map(convert: (T) -> R, reverse: (R) -> T): Pref<R> =
    map(Serializer(convert, reverse))

fun <T> Pref<T>.takeTo(value: T, fallback: T) =
    map({ it == value }, { if (it) value else fallback })

fun <T> Pref<T?>.takeTo(value: T) = map({ it == value }, { if (it) value else null })

fun <T> Pref<T>.verify(predicate: (T) -> Boolean, throwIfInvalid: Boolean = false) = map({ it }) {
    when {
        predicate(it) -> it
        throwIfInvalid -> throw AssertionError("Invalid value: $it")
        else -> default
    }
}

@ExperimentalPrefApi
@SubtlePrefApi
fun <T> DataStore<Preferences>.combined(vararg pairs: Pair<Preferences.Key<T & Any>, T>) =
    ReadOnlyProperty<Any?, CombinedPref<T>> { _, property ->
        CombinedPref(
            this,
            pairs.associate { (k, v) -> k.name to v },
            property.name,
            pairs.map { (k, _) -> k }.toMutableList()
        )
    }