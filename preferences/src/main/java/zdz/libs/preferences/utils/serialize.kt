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
operator fun <T> DataStore<Preferences>.get(
    serializer: Serializer<Int?, T>, cache: Boolean = false
) = build(key = ::intPreferencesKey, serializer = serializer, cache = cache)

@JvmName("getByDoubleSerializer")
operator fun <T> DataStore<Preferences>.get(
    serializer: Serializer<Double?, T>, cache: Boolean = false
) = build(key = ::doublePreferencesKey, serializer = serializer, cache = cache)

@JvmName("getByStringSerializer")
operator fun <T> DataStore<Preferences>.get(
    serializer: Serializer<String?, T>, cache: Boolean = false
) = build(key = ::stringPreferencesKey, serializer = serializer, cache = cache)

@JvmName("getByBooleanSerializer")
operator fun <T> DataStore<Preferences>.get(
    serializer: Serializer<Boolean?, T>, cache: Boolean = false
) = build(key = ::booleanPreferencesKey, serializer = serializer, cache = cache)

@JvmName("getByFloatSerializer")
operator fun <T> DataStore<Preferences>.get(
    serializer: Serializer<Float?, T>, cache: Boolean = false
) = build(key = ::floatPreferencesKey, serializer = serializer, cache = cache)

@JvmName("getByLongSerializer")
operator fun <T> DataStore<Preferences>.get(
    serializer: Serializer<Long?, T>, cache: Boolean = false
) = build(key = ::longPreferencesKey, serializer = serializer, cache = cache)

@JvmName("getByStringSetSerializer")
operator fun <T> DataStore<Preferences>.get(
    serializer: Serializer<Set<String>?, T>, cache: Boolean = false
) = build(key = ::stringSetPreferencesKey, serializer = serializer, cache = cache)