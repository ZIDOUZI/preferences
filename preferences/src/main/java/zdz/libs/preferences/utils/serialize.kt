package zdz.libs.preferences.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import zdz.libs.preferences.contracts.Serializer
import zdz.libs.preferences.model.MappedBuilder.Companion.build

@JvmName("getByIntSerializer")
operator fun <T> DataStore<Preferences>.get(serializer: Serializer<Int?, T>) =
    build(key = ::intPreferencesKey, serializer = serializer)

@JvmName("getByDoubleSerializer")
operator fun <T> DataStore<Preferences>.get(serializer: Serializer<Double?, T>) =
    build(key = ::doublePreferencesKey, serializer = serializer)

@JvmName("getByStringSerializer")
operator fun <T> DataStore<Preferences>.get(serializer: Serializer<String?, T>) =
    build(key = ::stringPreferencesKey, serializer = serializer)

@JvmName("getByBooleanSerializer")
operator fun <T> DataStore<Preferences>.get(serializer: Serializer<Boolean?, T>) =
    build(key = ::booleanPreferencesKey, serializer = serializer)

@JvmName("getByFloatSerializer")
operator fun <T> DataStore<Preferences>.get(serializer: Serializer<Float?, T>) =
    build(key = ::floatPreferencesKey, serializer = serializer)

@JvmName("getByLongSerializer")
operator fun <T> DataStore<Preferences>.get(serializer: Serializer<Long?, T>) =
    build(key = ::longPreferencesKey, serializer = serializer)

@JvmName("getByStringSetSerializer")
operator fun <T> DataStore<Preferences>.get(serializer: Serializer<Set<String>?, T>) =
    build(key = ::stringSetPreferencesKey, serializer = serializer)