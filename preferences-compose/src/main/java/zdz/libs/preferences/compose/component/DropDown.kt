package zdz.libs.preferences.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope
import zdz.libs.preferences.compose.delegator
import zdz.libs.preferences.compose.rem
import zdz.libs.preferences.contracts.Pref

@Composable
fun <T> PreferenceGroupScope.DropDown(
    key: Pref<T>,
    entries: Map<T, String>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) {
    var expanded by remember { mutableStateOf(false) }
    var value by key.delegator
    Base(
        title = title,
        modifier = modifier.clickable { expanded = enabled && !expanded },
        summary = summary,
        enabled = enabled,
        titlePresent = titlePresent,
        summaryPresent = summaryPresent,
        icon = icon,
        info = info,
        trailing = { Text(text = entries[value] ?: "") },
        elevation = elevation
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
inline fun <reified E : Enum<E>> PreferenceGroupScope.DropDown(
    key: Pref<E>,
    title: String,
    modifier: Modifier = Modifier,
    entries: Array<E> = enumValues<E>(),
    label: (E) -> String = { it.name },
    summary: String? = null,
    enabled: Boolean = true,
    noinline titlePresent: @Composable (() -> Unit)? = null,
    noinline summaryPresent: @Composable (() -> Unit)? = null,
    noinline icon: @Composable (() -> Unit)? = null,
    noinline info: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = DropDown(
    key = key,
    entries = entries.associateWith(label),
    title = title,
    modifier = modifier,
    summary = summary,
    enabled = enabled,
    titlePresent = titlePresent,
    summaryPresent = summaryPresent,
    icon = icon,
    info = info,
    elevation = elevation
)

@Composable
inline fun <reified E : Enum<E>> PreferenceGroupScope.DropDown(
    key: Pref<E?>,
    title: String,
    nullString: String,
    modifier: Modifier = Modifier,
    entries: Array<E> = enumValues<E>(),
    label: (E) -> String = { it.name },
    summary: String? = null,
    enabled: Boolean = true,
    noinline titlePresent: @Composable (() -> Unit)? = null,
    noinline summaryPresent: @Composable (() -> Unit)? = null,
    noinline icon: @Composable (() -> Unit)? = null,
    noinline info: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = DropDown(
    key = key,
    entries = buildMap { put(null, nullString); putAll(entries.associateWith(label)) },
    title = title,
    modifier = modifier,
    summary = summary,
    enabled = enabled,
    titlePresent = titlePresent,
    summaryPresent = summaryPresent,
    icon = icon,
    info = info,
    elevation = elevation
)

@JvmName("MultiCombo")
@Composable
fun <T> PreferenceGroupScope.DropDown(
    key: Pref<Set<T>>,
    entries: Map<T, String>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) {
    var expanded by remember { mutableStateOf(false) }
    var value by key.delegator
    Base(
        title = title,
        modifier = modifier.clickable { expanded = enabled && !expanded },
        summary = summary,
        enabled = enabled,
        titlePresent = titlePresent,
        summaryPresent = summaryPresent,
        icon = icon,
        info = info,
        trailing = {
            Text(text = value.joinToString(", ") { entries[it]!! })
        },
        elevation = elevation
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
inline fun <reified E : Enum<E>> PreferenceGroupScope.DropDown(
    key: Pref<Set<E>>,
    title: String,
    modifier: Modifier = Modifier,
    entries: Array<E> = enumValues<E>(),
    label: (E) -> String = { it.name },
    summary: String? = null,
    enabled: Boolean = true,
    noinline titlePresent: @Composable (() -> Unit)? = null,
    noinline summaryPresent: @Composable (() -> Unit)? = null,
    noinline icon: @Composable (() -> Unit)? = null,
    noinline info: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = DropDown(
    key = key,
    entries = entries.associateWith(label),
    title = title,
    modifier = modifier,
    summary = summary,
    enabled = enabled,
    titlePresent = titlePresent,
    summaryPresent = summaryPresent,
    icon = icon,
    info = info,
    elevation = elevation
)

@JvmName("MultiCombo")
@Composable
inline fun <reified E : Enum<E>> PreferenceGroupScope.DropDown(
    key: Pref<Set<E?>>,
    title: String,
    nullString: String,
    modifier: Modifier = Modifier,
    entries: Array<E> = enumValues<E>(),
    label: (E) -> String = { it.name },
    summary: String? = null,
    enabled: Boolean = true,
    noinline titlePresent: @Composable (() -> Unit)? = null,
    noinline summaryPresent: @Composable (() -> Unit)? = null,
    noinline icon: @Composable (() -> Unit)? = null,
    noinline info: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = DropDown<E?>(
    key = key,
    entries = buildMap { put(null, nullString); putAll(entries.associateWith(label)) },
    title = title,
    modifier = modifier,
    summary = summary,
    enabled = enabled,
    titlePresent = titlePresent,
    summaryPresent = summaryPresent,
    icon = icon,
    info = info,
    elevation = elevation
)