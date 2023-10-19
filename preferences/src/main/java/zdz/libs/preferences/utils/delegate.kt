package zdz.libs.preferences.utils

import kotlinx.coroutines.runBlocking
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.model.PreferenceIOScope
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

operator fun <T> Pref<T>.getValue(thisRef: Any?, property: KProperty<*>): T =
    runBlocking(PreferenceIOScope.coroutineContext) { current() }

operator fun <T> Pref<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) =
    runBlocking(PreferenceIOScope.coroutineContext) { emit(value) }

val <T> ReadOnlyProperty<Any?, Pref<T>>.value: ReadWriteProperty<Any?, T>
    get() = object : ReadWriteProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T =
            with(this@value.getValue(thisRef, property)) {
                runBlocking(PreferenceIOScope.coroutineContext) { current() }
            }
        
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T): Unit =
            with(this@value.getValue(thisRef, property)) {
                runBlocking(PreferenceIOScope.coroutineContext) { emit(value) }
            }
    }