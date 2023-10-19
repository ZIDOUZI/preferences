package zdz.libs.preferences.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope
import zdz.libs.preferences.compose.delegator
import zdz.libs.preferences.contracts.Pref

@Composable
fun PreferenceGroupScope.Switch(
    key: Pref<Boolean>,
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    summary: String? = null,
    icon: @Composable (() -> Unit)? = null,
) {
    var value by key.delegator
    Base(title = title,
        modifier = modifier.clickable { value = !value },
        summary = summary,
        enabled = enabled,
        icon = icon,
        trailing = {
            Switch(checked = value, enabled = enabled, onCheckedChange = { value = it })
        })
}