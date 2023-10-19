package zdz.libs.preferences.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import zdz.libs.preferences.CombinedPref
import zdz.libs.preferences.MappedPref
import zdz.libs.preferences.VerifiedPref
import zdz.libs.preferences.annotations.SubtlePrefApi
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.contracts.Serializer
import kotlin.properties.ReadOnlyProperty

fun <T, R> Pref<T>.map(
    serializer: Serializer<T, R>
): Pref<R> = MappedPref(this, serializer)

fun <T, R> Pref<T>.map(convert: (T) -> R, reverse: (R) -> T): Pref<R> =
    MappedPref(this, Serializer(convert, reverse))

fun <T> Pref<T>.takeTo(value: T, fallback: T) =
    map({ it == value }, { if (it) value else fallback })

fun <T> Pref<T?>.takeTo(value: T) = map({ it == value }, { if (it) value else null })

fun <T> Pref<T>.verify(
    predicate: (T) -> Boolean,
): Pref<T> = VerifiedPref(this, predicate)

fun <T, R> ReadOnlyProperty<Any?, Pref<T>>.map(convert: (T) -> R, reverse: (R) -> T) =
    ReadOnlyProperty { thisRef: Any?, property ->
        getValue(thisRef, property).map(convert, reverse)
    }

fun <T> ReadOnlyProperty<Any?, Pref<T>>.verify(predicate: (T) -> Boolean) =
    ReadOnlyProperty { thisRef: Any?, property ->
        getValue(thisRef, property).verify(predicate)
    }

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