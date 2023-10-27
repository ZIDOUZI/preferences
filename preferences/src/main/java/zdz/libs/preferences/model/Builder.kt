package zdz.libs.preferences.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import zdz.libs.preferences.MappedPref
import zdz.libs.preferences.PrefImpl
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.contracts.Serializer
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

data class Builder<T>(
    var ds: DataStore<Preferences>,
    var default: T,
    var key: (String) -> Preferences.Key<T & Any>,
    var cache: Boolean = false, // cache has no usage in this, only for it change to mapped
) : ReadOnlyProperty<Any?, Pref<T>> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): Pref<T> =
        PrefImpl(ds, default, key(property.name))

    fun <R> map(serializer: Serializer<T, R>) =
        MappedBuilder(ds, default, key, serializer, cache)

    fun <R> map(convert: (T) -> R, reverse: (R) -> T) =
        map(Serializer(convert, reverse))

    companion object {
        fun <T> DataStore<Preferences>.build(
            default: T,
            key: (String) -> Preferences.Key<T & Any>
        ) = Builder(this, default, key)

        fun <T : Any?> DataStore<Preferences>.build(key: (String) -> Preferences.Key<T & Any>) =
            Builder(this, null, key)
    }
}

data class MappedBuilder<S, T>(
    var ds: DataStore<Preferences>,
    var default: S,
    var key: (String) -> Preferences.Key<S & Any>,
    var serializer: Serializer<S, T>,
    var cache: Boolean = false,
) : ReadOnlyProperty<Any?, Pref<T>> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Pref<T> =
        MappedPref(ds, serializer.serialize(default), key(property.name), serializer, cache)

    companion object {
        fun <S, T> DataStore<Preferences>.build(
            default: S,
            key: (String) -> Preferences.Key<S & Any>,
            serializer: Serializer<S, T>,
            cache: Boolean = false
        ) = MappedBuilder(this, default, key, serializer, cache)

        fun <S, T> DataStore<Preferences>.build(
            key: (String) -> Preferences.Key<S & Any>,
            serializer: Serializer<S?, T>,
            cache: Boolean = false
        ) = MappedBuilder(this, null, key, serializer, cache)
    }
}