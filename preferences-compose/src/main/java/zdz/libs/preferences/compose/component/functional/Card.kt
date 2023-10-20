package zdz.libs.preferences.compose.component.functional

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import zdz.libs.preferences.compose.component.Base
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope

@Composable
fun PreferenceGroupScope.Card(
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    icon: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
) = Base(
    title = title,
    modifier = modifier.clickable(onClick = onClick),
    summary = summary,
    enabled = enabled,
    icon = icon,
    trailing = trailing,
)