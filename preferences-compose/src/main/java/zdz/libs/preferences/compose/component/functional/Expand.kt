package zdz.libs.preferences.compose.component.functional

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zdz.libs.preferences.compose.component.Base
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope

@Composable
fun PreferenceGroupScope.Expand(
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    hideWhenExpanded: Boolean = false,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
    content: @Composable PreferenceGroupScope.() -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    AnimatedContent(
        targetState = expanded,
        transitionSpec = {
            fadeIn() + slideInVertically { if (targetState) it else -it } togetherWith fadeOut() + slideOutVertically { if (targetState) -it else it }
        },
        label = title,
    ) {
        if (it) {
            content()
        }
        if (!it && !hideWhenExpanded) {
            Base(
                title = title,
                modifier = modifier.clickable { expanded = !expanded },
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
    }
}