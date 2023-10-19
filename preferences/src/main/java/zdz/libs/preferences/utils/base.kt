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
import zdz.libs.preferences.model.Builder.Companion.build

/*
 * NotNull Constructors, contains [Int], [Long], [Float], [String], [Boolean], [StringSet][Set]
 */
operator fun DataStore<Preferences>.get(default: Int) =
    build(default, ::intPreferencesKey)

operator fun DataStore<Preferences>.get(default: Long) =
    build(default, ::longPreferencesKey)

operator fun DataStore<Preferences>.get(default: Float) =
    build(default, ::floatPreferencesKey)

operator fun DataStore<Preferences>.get(default: Double) =
    build(default, ::doublePreferencesKey)

operator fun DataStore<Preferences>.get(default: String) =
    build(default, ::stringPreferencesKey)

operator fun DataStore<Preferences>.get(default: Boolean) =
    build(default, ::booleanPreferencesKey)

operator fun DataStore<Preferences>.get(default: Set<String>) =
    build(default, ::stringSetPreferencesKey)

/*
 * Nullable Constructors, contains [Int], [Long], [Float], [String], [Boolean], [StringSet][Set].
 * The default value can't be null, else use constructors below.
 */
@Deprecated(
    "This method gives a Flow<T?> but never be null.",
    ReplaceWith("this.get(default)"),
    DeprecationLevel.HIDDEN
)
fun DataStore<Preferences>.nullable(default: Int) =
    build(default, ::intPreferencesKey)

@Deprecated(
    "This method gives a Flow<T?> but never be null.",
    ReplaceWith("this.get(default)"),
    DeprecationLevel.HIDDEN
)
fun DataStore<Preferences>.nullable(default: Long) =
    build(default, ::longPreferencesKey)

@Deprecated(
    "This method gives a Flow<T?> but never be null.",
    ReplaceWith("this.get(default)"),
    DeprecationLevel.HIDDEN
)
fun DataStore<Preferences>.nullable(default: Float) =
    build(default, ::floatPreferencesKey)

@Deprecated(
    "This method gives a Flow<T?> but never be null.",
    ReplaceWith("this.get(default)"),
    DeprecationLevel.HIDDEN
)
fun DataStore<Preferences>.nullable(default: String) =
    build(default, ::stringPreferencesKey)

@Deprecated(
    "This method gives a Flow<T?> but never be null.",
    ReplaceWith("this.get(default)"),
    DeprecationLevel.HIDDEN
)
fun DataStore<Preferences>.nullable(default: Boolean) =
    build(default, ::booleanPreferencesKey)

/*
 * Nullable Constructors, contains [Int], [Long], [Float], [String], [Boolean], [StringSet][Set].
 * The default value is null.
 */
fun DataStore<Preferences>.boolean() =
    build(::booleanPreferencesKey)

fun DataStore<Preferences>.int() =
    build(::intPreferencesKey)

fun DataStore<Preferences>.long() =
    build(::longPreferencesKey)

fun DataStore<Preferences>.float() =
    build(::floatPreferencesKey)

fun DataStore<Preferences>.double() =
    build(::doublePreferencesKey)

fun DataStore<Preferences>.string() =
    build(::stringPreferencesKey)

fun DataStore<Preferences>.stringSet() =
    build(::stringSetPreferencesKey)