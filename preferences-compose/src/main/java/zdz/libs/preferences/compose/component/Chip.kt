package zdz.libs.preferences.compose.component

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zdz.libs.preferences.compose.RowPresent
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope
import zdz.libs.preferences.compose.delegator
import zdz.libs.preferences.compose.rem
import zdz.libs.preferences.compose.state
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.model.editAsync


@ExperimentalLayoutApi
@ExperimentalMaterial3Api
@Composable
inline fun <reified T> PreferenceGroupScope.SingleChip(
    key: Pref<T>,
    entries: Map<T, String>,
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    flowRow: Boolean = false,
    noinline icon: @Composable (() -> Unit)? = null,
) = Base(
    title = title,
    modifier = modifier,
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
)

@ExperimentalMaterial3Api
@ExperimentalLayoutApi
@Composable
inline fun <reified E : Enum<E>> PreferenceGroupScope.SingleChip(
    key: Pref<E?>,
    title: String,
    modifier: Modifier = Modifier,
    nullString: String? = null,
    label: (E) -> String = { it.name },
    enabled: Boolean = true,
    flowRow: Boolean = false,
    noinline icon: @Composable (() -> Unit)? = null,
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
    icon = icon
)

@ExperimentalMaterial3Api
@ExperimentalLayoutApi
@Composable
inline fun <reified E : Enum<E>> PreferenceGroupScope.SingleChip(
    key: Pref<E>,
    title: String,
    modifier: Modifier = Modifier,
    label: (E) -> String = { it.name },
    enabled: Boolean = true,
    flowRow: Boolean = false,
    noinline icon: @Composable (() -> Unit)? = null,
) = SingleChip(
    key = key,
    entries = enumValues<E>().associateWith { label(it) },
    title = title,
    modifier = modifier,
    enabled = enabled,
    flowRow = flowRow,
    icon = icon
)

@ExperimentalLayoutApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PreferenceGroupScope.MultipleChip(
    key: Pref<Set<T>>,
    entries: Map<T, String>,
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    flowRow: Boolean = false,
    icon: @Composable (() -> Unit)? = null,
) {
    var value by key.delegator
    Base(
        title = title,
        modifier = modifier,
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
    )
}

@ExperimentalLayoutApi
@Composable
inline fun <reified E : Enum<E>> PreferenceGroupScope.MultipleChip(
    key: Pref<Set<E>>,
    title: String,
    modifier: Modifier = Modifier,
    label: (E) -> String = { it.name },
    enabled: Boolean = true,
    flowRow: Boolean = false,
    noinline icon: @Composable (() -> Unit)? = null,
) = MultipleChip(
    key = key,
    entries = enumValues<E>().associateWith { label(it) },
    title = title,
    modifier = modifier,
    enabled = enabled,
    flowRow = flowRow,
    icon = icon
)