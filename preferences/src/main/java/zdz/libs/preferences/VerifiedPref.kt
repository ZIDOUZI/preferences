package zdz.libs.preferences

import zdz.libs.preferences.contracts.Pref

class VerifiedPref<T> internal constructor(
    private val pref: Pref<T>,
    private val verify: (T) -> Boolean,
) : Pref<T> by pref {
    override suspend fun edit(block: suspend (T) -> T?): T? =
        pref.edit { p -> block(p)?.takeIf { verify(it) } ?: default }
}