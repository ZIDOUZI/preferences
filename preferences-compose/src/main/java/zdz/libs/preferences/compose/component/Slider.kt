package zdz.libs.preferences.compose.component

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    icon: @Composable (() -> Unit)? = null,
) = Base(
    title = title,
    modifier = modifier,
    summaryPresent = {
        var value by key.delegator
        m3Slider(
            value = value.toFloat(),
            steps = steps,
            valueRange = range.first.toFloat()..range.last.toFloat(),
            onValueChange = { value = it.roundToInt() }
        )
    },
    trailing = trailing,
    icon = icon,
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
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    icon: @Composable (() -> Unit)? = null,
) = Base(
    title = title,
    modifier = modifier,
    summaryPresent = {
        var value by key.delegator
        m3Slider(
            value = value,
            steps = steps,
            valueRange = range,
            onValueChange = { value = it }
        )
    },
    icon = icon,
    trailing = trailing,
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
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    icon: @Composable (() -> Unit)? = null,
) = Base(
    title = title,
    modifier = modifier,
    summaryPresent = {
        var value by key.delegator
        m3Slider(
            value = value.toFloat(),
            steps = steps,
            valueRange = range.start.toFloat()..range.endInclusive.toFloat(),
            onValueChange = { value = it.toDouble() }
        )
    },
    icon = icon,
    trailing = trailing,
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
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    icon: @Composable (() -> Unit)? = null,
) = Base(
    title = title,
    modifier = modifier,
    summaryPresent = {
        var value by key.delegator
        m3Slider(
            value = value.toFloat(),
            steps = steps,
            valueRange = range.first.toFloat()..range.last.toFloat(),
            onValueChange = { value = it.roundToLong() }
        )
    },
    icon = icon,
    trailing = trailing,
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
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    icon: @Composable (() -> Unit)? = null,
) = Column(elevationModifier) {
    Base(
        title = title,
        modifier = modifier,
        summary = summary,
        trailing = trailing,
        elevation = 0.dp,
        icon = icon,
    )
    var value by key.delegator
    m3Slider(
        modifier = sliderModifier,
        value = value.toFloat(),
        steps = steps,
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
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    icon: @Composable (() -> Unit)? = null,
) = Column(elevationModifier) {
    Base(
        title = title,
        modifier = modifier,
        summary = summary,
        icon = icon,
        trailing = trailing,
    )
    var value by key.delegator
    m3Slider(
        modifier = sliderModifier,
        value = value,
        steps = steps,
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
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    icon: @Composable (() -> Unit)? = null,
) = Column(elevationModifier) {
    Base(
        title = title,
        modifier = modifier,
        summary = summary,
        icon = icon,
        trailing = trailing,
    )
    var value by key.delegator
    m3Slider(
        modifier = sliderModifier,
        value = value.toFloat(),
        steps = steps,
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
    trailing: @Composable (() -> Unit)? = { ResetIcon(enabled) { key.resetAsync() } },
    icon: @Composable (() -> Unit)? = null,
) = Column(elevationModifier) {
    Base(
        title = title,
        modifier = modifier,
        summary = summary,
        icon = icon,
        trailing = trailing,
    )
    var value by key.delegator
    m3Slider(
        modifier = sliderModifier,
        value = value.toFloat(),
        steps = steps,
        valueRange = range.first.toFloat()..range.last.toFloat(),
        onValueChange = { value = it.roundToLong() }
    )
}