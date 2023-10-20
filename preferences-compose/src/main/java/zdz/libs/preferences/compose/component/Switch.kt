package zdz.libs.preferences.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Switch
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
fun PreferenceGroupScope.Switch(
    key: Pref<Boolean>,
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
    var value by key.delegator
    Base(
        title = title,
        modifier = modifier.clickable { value = !value },
        summary = summary,
        enabled = enabled,
        titlePresent = titlePresent,
        summaryPresent = summaryPresent,
        icon = icon,
        trailing = {
            Switch(checked = value, enabled = enabled, onCheckedChange = { value = it })
        },
        info = info,
        elevation = elevation
    )
}