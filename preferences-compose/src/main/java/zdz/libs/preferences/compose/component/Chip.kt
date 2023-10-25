package zdz.libs.preferences.compose.component

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zdz.libs.preferences.compose.RowPresent
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope
import zdz.libs.preferences.compose.delegator
import zdz.libs.preferences.compose.rem
import zdz.libs.preferences.compose.state
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.model.editAsync


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T> PreferenceGroupScope.SingleChip(
    key: Pref<T>,
    entries: Map<T, String>,
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    flowRow: Boolean = false,
    noinline titlePresent: @Composable (() -> Unit)? = null,
    noinline icon: @Composable (() -> Unit)? = null,
    noinline info: @Composable (() -> Unit)? = null,
    noinline trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = Base(
    title = title,
    modifier = modifier,
    enabled = enabled,
    titlePresent = titlePresent,
    summaryPresent = {
        RowPresent(flowRow = flowRow) {
            val contain = null is T && (null as T) in entries.keys
            entries.forEach { (k, v) ->
                FilterChip(
                    selected = key.state == k,
                    onClick = { key.editAsync { if (!contain && it == k) null else k } },
                    label = { Text(text = v) },
                    enabled = enabled,
                )
            }
        }
    },
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation,
)

@Composable
inline fun <reified E : Enum<E>> PreferenceGroupScope.SingleChip(
    key: Pref<E?>,
    title: String,
    modifier: Modifier = Modifier,
    nullString: String? = null,
    label: (E) -> String = { it.name },
    enabled: Boolean = true,
    flowRow: Boolean = false,
    noinline titlePresent: @Composable (() -> Unit)? = null,
    noinline icon: @Composable (() -> Unit)? = null,
    noinline info: @Composable (() -> Unit)? = null,
    noinline trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = SingleChip(
    key = key,
    entries = buildMap {
        if (nullString != null) put(null, nullString)
        this.putAll(enumValues<E>().map { it to label(it) })
    },
    title = title,
    modifier = modifier,
    enabled = enabled,
    flowRow = flowRow,
    titlePresent = titlePresent,
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation,
)

@Composable
inline fun <reified E : Enum<E>> PreferenceGroupScope.SingleChip(
    key: Pref<E>,
    title: String,
    modifier: Modifier = Modifier,
    label: (E) -> String = { it.name },
    enabled: Boolean = true,
    flowRow: Boolean = false,
    noinline titlePresent: @Composable (() -> Unit)? = null,
    noinline icon: @Composable (() -> Unit)? = null,
    noinline info: @Composable (() -> Unit)? = null,
    noinline trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = SingleChip(
    key = key,
    entries = enumValues<E>().associateWith { label(it) },
    title = title,
    modifier = modifier,
    enabled = enabled,
    flowRow = flowRow,
    titlePresent = titlePresent,
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation,
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun <T> PreferenceGroupScope.MultipleChip(
    key: Pref<Set<T>>,
    entries: Map<T, String>,
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    flowRow: Boolean = false,
    titlePresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) {
    var value by key.delegator
    Base(
        title = title,
        modifier = modifier,
        enabled = enabled,
        titlePresent = titlePresent,
        summaryPresent = {
            RowPresent(flowRow = flowRow) {
                entries.forEach { (k, v) ->
                    FilterChip(
                        selected = k in value,
                        onClick = { value %= k },
                        label = { Text(text = v) },
                        enabled = enabled,
                    )
                }
            }
        },
        icon = icon,
        info = info,
        trailing = trailing,
        elevation = elevation,
    )
}

@Composable
inline fun <reified E : Enum<E>> PreferenceGroupScope.MultipleChip(
    key: Pref<Set<E>>,
    title: String,
    modifier: Modifier = Modifier,
    label: (E) -> String = { it.name },
    enabled: Boolean = true,
    flowRow: Boolean = false,
    noinline titlePresent: @Composable (() -> Unit)? = null,
    noinline icon: @Composable (() -> Unit)? = null,
    noinline info: @Composable (() -> Unit)? = null,
    noinline trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = MultipleChip(
    key = key,
    entries = enumValues<E>().associateWith { label(it) },
    title = title,
    modifier = modifier,
    enabled = enabled,
    flowRow = flowRow,
    titlePresent = titlePresent,
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation,
)