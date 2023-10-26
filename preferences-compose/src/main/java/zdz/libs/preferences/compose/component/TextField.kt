package zdz.libs.preferences.compose.component

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope
import zdz.libs.preferences.compose.delegator
import zdz.libs.preferences.contracts.Pref

@Composable
fun PreferenceGroupScope.TextField(
    key: Pref<String>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = Base(
    title = title,
    modifier = modifier,
    summary = summary,
    enabled = enabled,
    titlePresent = titlePresent,
    summaryPresent = summaryPresent,
    icon = icon,
    info = info,
    trailing = {
        val (get, set) = key.delegator
        TextField(value = get, onValueChange = set, enabled = enabled)
    },
    elevation = elevation
)

@Composable
@JvmName("TextFieldNullable")
fun PreferenceGroupScope.TextField(
    key: Pref<String?>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = Base(
    title = title,
    modifier = modifier,
    summary = summary,
    enabled = enabled,
    titlePresent = titlePresent,
    summaryPresent = summaryPresent,
    icon = icon,
    info = info,
    trailing = {
        var value by key.delegator
        TextField(value = value ?: "", onValueChange = { value = it }, enabled = enabled)
    },
    elevation = elevation
)