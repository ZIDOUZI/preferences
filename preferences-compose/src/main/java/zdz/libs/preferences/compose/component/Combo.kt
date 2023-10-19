package zdz.libs.preferences.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope
import zdz.libs.preferences.compose.delegator
import zdz.libs.preferences.compose.rem
import zdz.libs.preferences.contracts.Pref

@Composable
fun <T> PreferenceGroupScope.Combo(
    key: Pref<T>,
    entries: Map<T, String>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    icon: @Composable (() -> Unit)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    var value by key.delegator
    Base(
        title = title,
        modifier = modifier.clickable { expanded = enabled && !expanded },
        summary = summary,
        icon = icon,
        trailing = { Text(text = entries[value] ?: "") }
    )
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        entries.forEach { (entry, label) ->
            DropdownMenuItem(text = { Text(text = label) }, onClick = {
                value = entry
                expanded = false
            })
        }
    }
}

@Composable
inline fun <reified E : Enum<E>> PreferenceGroupScope.Combo(
    key: Pref<E>,
    title: String,
    modifier: Modifier = Modifier,
    entries: Array<E> = enumValues<E>(),
    label: (E) -> String = { it.name },
    summary: String? = null,
    enabled: Boolean = true,
    noinline icon: @Composable (() -> Unit)? = null,
) = Combo(
    key = key,
    entries = entries.associateWith(label),
    title = title,
    modifier = modifier,
    summary = summary,
    enabled = enabled,
    icon = icon
)

@Composable
inline fun <reified E : Enum<E>> PreferenceGroupScope.Combo(
    key: Pref<E?>,
    title: String,
    nullString: String,
    modifier: Modifier = Modifier,
    entries: Array<E> = enumValues<E>(),
    label: (E) -> String = { it.name },
    summary: String? = null,
    enabled: Boolean = true,
    noinline icon: @Composable (() -> Unit)? = null,
) = Combo(
    key = key,
    entries = buildMap { put(null, nullString); putAll(entries.associateWith(label)) },
    title = title,
    modifier = modifier,
    summary = summary,
    enabled = enabled,
    icon = icon
)

@JvmName("MultiCombo")
@Composable
fun <T> PreferenceGroupScope.Combo(
    key: Pref<Set<T>>,
    entries: Map<T, String>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    icon: @Composable (() -> Unit)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    var value by key.delegator
    Base(
        title = title,
        modifier = modifier.clickable { expanded = enabled && !expanded },
        summary = summary,
        icon = icon,
        trailing = {
            Text(text = value.joinToString(", ") { entries[it]!! })
        }
    )
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        entries.forEach { (entry, label) ->
            DropdownMenuItem(
                text = { Text(text = label) },
                onClick = { value %= entry }
            )
        }
    }
}

@JvmName("MultiCombo")
@Composable
inline fun <reified E : Enum<E>> PreferenceGroupScope.Combo(
    key: Pref<Set<E>>,
    title: String,
    modifier: Modifier = Modifier,
    entries: Array<E> = enumValues<E>(),
    label: (E) -> String = { it.name },
    summary: String? = null,
    enabled: Boolean = true,
    noinline icon: @Composable (() -> Unit)? = null,
) = Combo(
    key = key,
    entries = entries.associateWith(label),
    title = title,
    modifier = modifier,
    summary = summary,
    enabled = enabled,
    icon = icon
)

@JvmName("MultiCombo")
@Composable
inline fun <reified E : Enum<E>> PreferenceGroupScope.Combo(
    key: Pref<Set<E?>>,
    title: String,
    nullString: String,
    modifier: Modifier = Modifier,
    entries: Array<E> = enumValues<E>(),
    label: (E) -> String = { it.name },
    summary: String? = null,
    enabled: Boolean = true,
    noinline icon: @Composable (() -> Unit)? = null,
) = Combo<E?>(
    key = key,
    entries = buildMap { put(null, nullString); putAll(entries.associateWith(label)) },
    title = title,
    modifier = modifier,
    summary = summary,
    enabled = enabled,
    icon = icon
)