package zdz.libs.preferences.compose.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zdz.libs.compose.ex.NumberBox
import zdz.libs.preferences.compose.ResetIcon
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope
import zdz.libs.preferences.compose.delegator
import zdz.libs.preferences.compose.elevationModifier
import zdz.libs.preferences.compose.stepperModifier
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.model.resetAsync

@Composable
fun PreferenceGroupScope.Stepper(
    key: Pref<Int>,
    title: String,
    modifier: Modifier = Modifier,
    range: IntRange = 0..100,
    @androidx.annotation.IntRange(from = 1L) delta: Int = 1,
    enabled: Boolean = true,
    present: @Composable (@Composable () -> Unit) -> Unit = { it() },
    titlePresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    elevation: Dp = 2.dp,
) = Base(
    title = title,
    modifier = modifier,
    enabled = enabled,
    titlePresent = titlePresent,
    summaryPresent = {
        var value by key.delegator
        NumberBox(
            value = value,
            modifier = Modifier.fillMaxWidth(),
            innerModifier = Modifier.padding(8.dp),
            range = range,
            delta = delta,
            enabled = enabled,
            present = present,
        ) { value = it }
    },
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation,
)

@Composable
fun PreferenceGroupScope.Stepper(
    key: Pref<Float>,
    title: String,
    modifier: Modifier = Modifier,
    range: ClosedRange<Float> = 0f..1f,
    @androidx.annotation.FloatRange(from = Double.MIN_VALUE) delta: Float = 0.1f,
    enabled: Boolean = true,
    present: @Composable (@Composable () -> Unit) -> Unit = { DefaultPresent(it) },
    titlePresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    elevation: Dp = 2.dp,
) = Base(
    title = title,
    modifier = modifier,
    enabled = enabled,
    summaryPresent = {
        var value by key.delegator
        NumberBox(
            value = value,
            range = range,
            delta = delta,
            enabled = enabled,
            present = present,
        ) { value = it }
    },
    titlePresent = titlePresent,
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation,
)

@Composable
fun PreferenceGroupScope.LargeStepper(
    key: Pref<Int>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    range: IntRange = 0..100,
    @androidx.annotation.IntRange(from = 1L) delta: Int = 1,
    enabled: Boolean = true,
    present: @Composable (@Composable () -> Unit) -> Unit = { it() },
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    elevation: Dp = 2.dp,
) = Column(
    modifier = elevationModifier(elevation),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Base(
        title = title,
        modifier = modifier,
        summary = summary,
        enabled = enabled,
        titlePresent = titlePresent,
        summaryPresent = summaryPresent,
        icon = icon,
        info = info,
        trailing = trailing,
        elevation = 0.dp,
    )
    var value by key.delegator
    NumberBox(
        value = value,
        modifier = stepperModifier,
        innerModifier = Modifier.padding(8.dp),
        range = range,
        delta = delta,
        enabled = enabled,
        present = present,
    ) { value = it }
}

@Composable
fun PreferenceGroupScope.LargeStepper(
    key: Pref<Float>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    range: ClosedRange<Float> = 0f..1f,
    @androidx.annotation.FloatRange(from = Double.MIN_VALUE) delta: Float = 0.1f,
    enabled: Boolean = true,
    present: @Composable (@Composable () -> Unit) -> Unit = { DefaultPresent(it) },
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    elevation: Dp = 2.dp,
) = Column(
    modifier = elevationModifier(elevation),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Base(
        title = title,
        modifier = modifier,
        summary = summary,
        enabled = enabled,
        titlePresent = titlePresent,
        summaryPresent = summaryPresent,
        icon = icon,
        info = info,
        trailing = trailing,
        elevation = 0.dp,
    )
    var value by key.delegator
    NumberBox(
        value = value,
        modifier = stepperModifier,
        innerModifier = Modifier.padding(8.dp),
        range = range,
        delta = delta,
        enabled = enabled,
        present = present,
    ) { value = it }
}

@Composable
private fun DefaultPresent(content: @Composable () -> Unit) {
    Row {
        content()
        Text(text = " %")
    }
}