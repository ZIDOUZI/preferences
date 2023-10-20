package zdz.libs.preferences.compose.component.functional

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zdz.libs.preferences.compose.component.Base
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope

@Composable
fun PreferenceGroupScope.Card(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
) = Base(
    title = title,
    modifier = modifier.clickable(onClick = onClick),
    summary = summary,
    enabled = enabled,
    titlePresent = titlePresent,
    summaryPresent = summaryPresent,
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation
)
