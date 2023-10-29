package zdz.libs.preferences.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import zdz.libs.preferences.annotations.ExperimentalPrefApi
import zdz.libs.preferences.annotations.SubtlePrefApi
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.dispresed.CombinedPref
import zdz.libs.preferences.dispresed.PrefImpl
import kotlin.properties.ReadOnlyProperty

@OptIn(SubtlePrefApi::class)
@ExperimentalPrefApi
fun <T> DataStore<Preferences>.combined(vararg pref: Pair<Preferences.Key<T & Any>, T>) =
    ReadOnlyProperty<Any?, CombinedPref<T>> { _, property ->
        CombinedPref(this, property.name, pref.toList())
    }

@OptIn(SubtlePrefApi::class)
@ExperimentalPrefApi
fun <T> DataStore<Preferences>.combined(
    vararg keys: Preferences.Key<T & Any>,
    default: T,
) = ReadOnlyProperty<Any?, CombinedPref<T>> { _, property ->
    CombinedPref(this, property.name, keys.map { it to default })
}

@SubtlePrefApi
fun <T> DataStore<Preferences>.combined(vararg pref: Pref<T>) =
    ReadOnlyProperty<Any?, CombinedPref<T>> { _, property ->
        CombinedPref(this, property.name, pref.map { (it as PrefImpl).key to it.default })
    }