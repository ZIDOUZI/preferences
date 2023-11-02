package zdz.libs.preferences.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zdz.libs.compose.ex.AlertDialog
import zdz.libs.compose.ex.Dialog
import zdz.libs.compose.ex.str
import zdz.libs.preferences.compose.OrderBox
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope
import zdz.libs.preferences.compose.delegator
import zdz.libs.preferences.compose.remAssign
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.model.get
import zdz.libs.preferences.model.set

@JvmName("SinglePopup")
@Composable
fun <T> PreferenceGroupScope.SinglePopup(
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
    trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) {
    var current by key.delegator
    val dialog = Dialog(
        title = title,
        content = {
            LazyColumn {
                items(entries.size) { index ->
                    val (t, u) = entries.entries.elementAt(index)
                    Column {
                        RadioButton(selected = current == t, onClick = {
                            current = t
                            hide()
                        })
                        Text(text = u, style = typography.bodyMedium)
                    }
                }
            }
        })
    Base(
        title = title,
        modifier = modifier.clickable { dialog.show() },
        summary = summary,
        enabled = enabled,
        titlePresent = titlePresent,
        summaryPresent = summaryPresent,
        icon = icon,
        info = info,
        trailing = trailing,
        elevation = elevation,
    )
}

@JvmName("SinglePopup")
@Composable
inline fun <reified E : Enum<E>> PreferenceGroupScope.SinglePopup(
    key: Pref<E>,
    entries: Array<E> = enumValues<E>(),
    label: (E) -> String = { it.name },
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    noinline titlePresent: @Composable (() -> Unit)? = null,
    noinline summaryPresent: @Composable (() -> Unit)? = null,
    noinline icon: @Composable (() -> Unit)? = null,
    noinline info: @Composable (() -> Unit)? = null,
    noinline trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = SinglePopup(
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
    trailing = trailing,
    elevation = elevation
)

@Composable
private inline fun <T, R> PreferenceGroupScope.MultiplePopupCore(
    key: Pref<out Collection<T>>,
    crossinline onSelectedChanged: (List<T>) -> Unit,
    entries: Map<T, R>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    noinline titlePresent: @Composable (() -> Unit)? = null,
    noinline summaryPresent: @Composable (() -> Unit)? = null,
    noinline icon: @Composable (() -> Unit)? = null,
    noinline info: @Composable (() -> Unit)? = null,
    noinline trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
    crossinline content: @Composable (T, selected: MutableList<T>, label: R) -> Unit,
) {
    var visible by remember { mutableStateOf(false) }
    val selected = remember(visible) { key.get().toMutableStateList() }
    if (visible) AlertDialog(
        title = title,
        confirmLabel = android.R.string.ok.str,
        dismissLabel = android.R.string.cancel.str,
        neutralLabel = "清除", // TODO: extract string resource
        onConfirm = { onSelectedChanged(selected); visible = false },
        onNeutral = { selected.clear() },
        onDismiss = { visible = false },
        content = {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(entries.size) { i ->
                    val (t, u) = entries.entries.elementAt(i)
                    content(t, selected, u)
                }
            }
        })
    Base(
        title = title,
        modifier = modifier.clickable { visible = true },
        summary = summary,
        enabled = enabled,
        titlePresent = titlePresent,
        summaryPresent = summaryPresent,
        icon = icon,
        info = info,
        trailing = trailing,
        elevation = elevation,
    )
}

@Composable
fun <T> PreferenceGroupScope.MultiplePopup(
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
    trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = MultiplePopupCore(
    key = key,
    onSelectedChanged = { key.set(it.toSet()) },
    entries = entries,
    title = title,
    modifier = modifier,
    summary = summary,
    enabled = enabled,
    titlePresent = titlePresent,
    summaryPresent = summaryPresent,
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation,
) { t, selected, label ->
    Column {
        RadioButton(
            selected = t in selected,
            onClick = { selected %= t })
        Text(text = label, style = typography.bodyMedium)
    }
}

@JvmName("MultipleListPopup")
@Composable
fun <T> PreferenceGroupScope.MultiplePopup(
    key: Pref<List<T>>,
    entries: Map<T, String>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = MultiplePopupCore(
    key = key,
    onSelectedChanged = { key.set(it) },
    entries = entries,
    title = title,
    modifier = modifier,
    summary = summary,
    enabled = enabled,
    titlePresent = titlePresent,
    summaryPresent = summaryPresent,
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation,
) { t, selected, label ->
    Row(
        modifier = Modifier
            .clickable { selected %= t }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OrderBox(
            order = (selected.indexOf(t) + 1).takeIf { t in selected },
            enabled = enabled
        ) { selected %= t }
        Text(text = label, style = typography.bodyMedium)
    }
}

@Composable
fun <T, R> PreferenceGroupScope.MultiplePopup(
    key: Pref<List<T>>,
    entries: Map<T, R>,
    present: @Composable RowScope.(R) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = MultiplePopupCore(
    key = key,
    onSelectedChanged = { key.set(it) },
    entries = entries,
    title = title,
    modifier = modifier,
    summary = summary,
    enabled = enabled,
    titlePresent = titlePresent,
    summaryPresent = summaryPresent,
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation,
) { t, selected, label ->
    Row(
        modifier = Modifier
            .clickable { selected %= t }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OrderBox(
            order = (selected.indexOf(t) + 1).takeIf { t in selected },
            enabled = enabled
        ) { selected %= t }
        present(label)
    }
}