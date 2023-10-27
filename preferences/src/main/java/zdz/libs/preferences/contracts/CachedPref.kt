package zdz.libs.preferences.contracts

interface CachedPref<T> : Pref<T> {
    val cacheValue: T?
}