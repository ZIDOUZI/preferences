package zdz.libs.preferences.compose.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zdz.libs.compose.ex.status
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope

@Suppress("UnusedReceiverParameter")
@PublishedApi
@Composable
internal fun PreferenceGroupScope.Base(
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
    val textColor by colorScheme.onSurface.status(enabled = enabled)
    val iconColor by colorScheme.onSurfaceVariant.status(enabled = enabled)
    ListItem(
        headlineContent = {
            titlePresent?.invoke() ?: Text(text = title)
        },
        modifier = modifier,
        supportingContent = {
            summaryPresent?.invoke() ?: summary?.let { Text(text = it) }
        },
        leadingContent = icon.takeUnless { it == {} } ?: { Box(modifier = Modifier.size(24.dp)) },
        overlineContent = info,
        trailingContent = trailing,
        shadowElevation = elevation,
        colors = ListItemDefaults.colors(
            leadingIconColor = iconColor,
            trailingIconColor = iconColor,
            headlineColor = textColor,
            supportingColor = textColor,
        )
    )
}