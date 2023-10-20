package zdz.libs.preferences.compose.component

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zdz.libs.preferences.compose.ResetIcon
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope
import zdz.libs.preferences.compose.delegator
import zdz.libs.preferences.compose.elevationModifier
import zdz.libs.preferences.compose.sliderModifier
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.model.resetAsync
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import androidx.compose.material3.Slider as m3Slider

@Composable
@JvmName("IntSlider")
fun PreferenceGroupScope.Slider(
    key: Pref<Int>,
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @IntRange(from = 0L) steps: Int = 0,
    range: kotlin.ranges.IntRange = 0..100,
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
        m3Slider(
            value = value.toFloat(),
            steps = steps,
            enabled = enabled,
            valueRange = range.first.toFloat()..range.last.toFloat(),
            onValueChange = { value = it.roundToInt() }
        )
    },
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation,
)

@Composable
@JvmName("FloatSlider")
fun PreferenceGroupScope.Slider(
    key: Pref<Float>,
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @IntRange(from = 0L) steps: Int = 0,
    range: ClosedFloatingPointRange<Float> = 0f..1f,
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
        m3Slider(
            value = value,
            steps = steps,
            enabled = enabled,
            valueRange = range,
            onValueChange = { value = it }
        )
    },
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation,
)

@Composable
@JvmName("DoubleSlider")
fun PreferenceGroupScope.Slider(
    key: Pref<Double>,
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @IntRange(from = 0L) steps: Int = 0,
    range: ClosedFloatingPointRange<Double> = 0.0..1.0,
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
        m3Slider(
            value = value.toFloat(),
            steps = steps,
            enabled = enabled,
            valueRange = range.start.toFloat()..range.endInclusive.toFloat(),
            onValueChange = { value = it.toDouble() }
        )
    },
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation,
)

@Composable
@JvmName("LongSlider")
fun PreferenceGroupScope.Slider(
    key: Pref<Long>,
    title: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @IntRange(from = 0L) steps: Int = 0,
    range: LongRange = 0L..100L,
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
        m3Slider(
            value = value.toFloat(),
            steps = steps,
            enabled = enabled,
            valueRange = range.first.toFloat()..range.last.toFloat(),
            onValueChange = { value = it.roundToLong() }
        )
    },
    icon = icon,
    info = info,
    trailing = trailing,
    elevation = elevation,
)

@Composable
@JvmName("LargeIntSlider")
fun PreferenceGroupScope.LargeSlider(
    key: Pref<Int>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    @IntRange(from = 0L) steps: Int = 0,
    range: kotlin.ranges.IntRange = 0..100,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    elevation: Dp = 2.dp,
) = Column(elevationModifier(elevation)) {
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
    m3Slider(
        modifier = sliderModifier,
        value = value.toFloat(),
        steps = steps,
        enabled = enabled,
        valueRange = range.first.toFloat()..range.last.toFloat(),
        onValueChange = { value = it.roundToInt() }
    )
}

@Composable
@JvmName("LargeFloatSlider")
fun PreferenceGroupScope.LargeSlider(
    key: Pref<Float>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    @IntRange(from = 0L) steps: Int = 0,
    range: ClosedFloatingPointRange<Float> = 0f..1f,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    elevation: Dp = 2.dp,
) = Column(elevationModifier(elevation)) {
    Base(
        title = title,
        modifier = modifier,
        summary = summary,
        titlePresent = titlePresent,
        summaryPresent = summaryPresent,
        icon = icon,
        info = info,
        trailing = trailing,
        elevation = 0.dp,
    )
    var value by key.delegator
    m3Slider(
        modifier = sliderModifier,
        value = value,
        steps = steps,
        enabled = enabled,
        valueRange = range,
        onValueChange = { value = it }
    )
}

@Composable
@JvmName("LargeDoubleSlider")
fun PreferenceGroupScope.LargeSlider(
    key: Pref<Double>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    @IntRange(from = 0L) steps: Int = 0,
    range: ClosedFloatingPointRange<Double> = 0.0..1.0,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    elevation: Dp = 2.dp,
) = Column(elevationModifier(elevation)) {
    Base(
        title = title,
        modifier = modifier,
        summary = summary,
        titlePresent = titlePresent,
        summaryPresent = summaryPresent,
        icon = icon,
        info = info,
        trailing = trailing,
        elevation = 0.dp,
    )
    var value by key.delegator
    m3Slider(
        modifier = sliderModifier,
        value = value.toFloat(),
        steps = steps,
        enabled = enabled,
        valueRange = range.start.toFloat()..range.endInclusive.toFloat(),
        onValueChange = { value = it.toDouble() }
    )
}

@Composable
@JvmName("LargeLongSlider")
fun PreferenceGroupScope.LargeSlider(
    key: Pref<Long>,
    title: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    enabled: Boolean = true,
    @IntRange(from = 0L) steps: Int = 0,
    range: LongRange = 0L..100L,
    titlePresent: @Composable (() -> Unit)? = null,
    summaryPresent: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    info: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    elevation: Dp = 2.dp,
) = Column(elevationModifier(elevation)) {
    Base(
        title = title,
        modifier = modifier,
        summary = summary,
        titlePresent = titlePresent,
        summaryPresent = summaryPresent,
        icon = icon,
        info = info,
        trailing = trailing,
        elevation = 0.dp,
    )
    var value by key.delegator
    m3Slider(
        modifier = sliderModifier,
        value = value.toFloat(),
        steps = steps,
        enabled = enabled,
        valueRange = range.first.toFloat()..range.last.toFloat(),
        onValueChange = { value = it.roundToLong() }
    )
}