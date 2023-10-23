package zdz.libs.preferences.compose.component.functional

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zdz.libs.preferences.StaticPref
import zdz.libs.preferences.compose.component.Base
import zdz.libs.preferences.compose.component.Switch
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope
import zdz.libs.preferences.compose.delegator
import zdz.libs.preferences.contracts.Pref

private val defaultTransitionScope =
    fun AnimatedContentTransitionScope<Boolean>.(): ContentTransform =
        fadeIn() + slideInVertically { if (targetState) it else -it } togetherWith fadeOut() + slideOutVertically { if (targetState) -it else it }


@Composable
fun PreferenceGroupScope.Expand(
    title: String,
    modifier: Modifier = Modifier,
    key: Pref<Boolean>? = null,
    summary: String? = null,
    enabled: Boolean = true,
    transitionSpec: AnimatedContentTransitionScope<Boolean>.() -> ContentTransform = defaultTransitionScope,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
    content: @Composable PreferenceGroupScope.() -> Unit,
) {
    var expanded by key?.delegator ?: remember<MutableState<Boolean>> { mutableStateOf(false) }
    AnimatedContent(
        targetState = expanded,
        transitionSpec = transitionSpec,
        label = title,
    ) {
        if (it) {
            content()
        } else {
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

@Composable
fun PreferenceGroupScope.Expand(
    title: String,
    modifier: Modifier = Modifier,
    state: MutableState<Boolean> = remember { mutableStateOf(false) },
    summary: String? = null,
    enabled: Boolean = true,
    transitionSpec: AnimatedContentTransitionScope<Boolean>.() -> ContentTransform = defaultTransitionScope,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
    content: @Composable PreferenceGroupScope.() -> Unit,
) {
    var expanded by state
    AnimatedContent(
        targetState = expanded,
        transitionSpec = transitionSpec,
        label = title,
    ) {
        if (it) {
            content()
        } else {
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

@Composable
fun PreferenceGroupScope.ExpandSwitch(
    key: Pref<Boolean>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    transitionSpec: AnimatedContentTransitionScope<Boolean>.() -> ContentTransform = defaultTransitionScope,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
    content: @Composable PreferenceGroupScope.() -> Unit,
) {
    var expanded by key.delegator
    AnimatedContent(
        targetState = expanded,
        transitionSpec = transitionSpec,
        label = title,
    ) {
        if (it) {
            content()
        } else {
            Switch(
                key = key,
                title = title,
                modifier = modifier.clickable { expanded = !expanded },
                summary = summary,
                enabled = enabled,
                titlePresent = titlePresent,
                summaryPresent = summaryPresent,
                icon = icon,
                info = info,
                elevation = elevation,
            )
        }
    }
}

@Composable
fun PreferenceGroupScope.Expand(
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    transitionSpec: AnimatedContentTransitionScope<Boolean>.() -> ContentTransform = defaultTransitionScope,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    elevation: Dp = 2.dp,
    content: @Composable PreferenceGroupScope.() -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    AnimatedContent(
        targetState = expanded,
        transitionSpec = transitionSpec,
        label = title,
    ) {
        if (it) {
            content()
        } else {
            Switch(
                key = StaticPref(false),
                title = title,
                modifier = modifier.clickable { expanded = !expanded },
                summary = summary,
                enabled = enabled,
                titlePresent = titlePresent,
                summaryPresent = summaryPresent,
                icon = icon,
                info = info,
                elevation = elevation,
            )
        }
    }
}