package zdz.libs.preferences.utils

import kotlinx.coroutines.runBlocking
import zdz.libs.preferences.annotations.ShadowedCoroutinesApi
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.model.PreferenceIOScope

@ShadowedCoroutinesApi
operator fun <T> Pref<out Collection<T>>.contains(element: T) =
    runBlocking(PreferenceIOScope.coroutineContext) { current().contains(element) }

//fun <T> Pref<out Collection<T>>.asMultable()