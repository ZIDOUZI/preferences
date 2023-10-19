package zdz.libs.preferences.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zdz.libs.compose.ex.AsIcon
import zdz.libs.compose.ex.SquircleShape
import zdz.libs.compose.ex.status
import zdz.libs.compose.ex.takeUnless


internal val elevationModifier = Modifier.shadow(2.dp)
internal val sliderModifier = Modifier.composed {
    this
        .background(colorScheme.surface)
        .padding(start = 58.dp, end = 26.dp)
}
internal val stepperModifier = Modifier.composed {
    this
        .background(colorScheme.surface)
        .fillMaxWidth()
}

@Composable
internal fun ResetIcon(enabled: Boolean, action: () -> Unit) =
    IconButton(onClick = action, enabled = enabled) {
        R.drawable.baseline_restore_24.AsIcon(description = "reset")
    }

internal val squircleShape = SquircleShape(CornerSize(15))

@Composable
internal fun OrderBox(
    order: Int? = null,
    enabled: Boolean = true,
    shape: Shape = squircleShape,
    onClick: () -> Unit = {},
) {
    val color by colorScheme.primary.takeUnless { order == null }.status(enabled = enabled)
    Box(
        modifier = Modifier
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = 18.dp,
                )
            )
            .padding(8.dp)
            .border(2.dp, color = colorScheme.primary, shape = shape)
            .background(color = color, shape = shape)
            .size(22.dp)
    ) {
        if (order != null) Text(
            text = order.toString(),
            modifier = Modifier.align(Alignment.Center),
            color = colorScheme.onPrimary,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@ExperimentalLayoutApi
@Composable
fun RowPresent(
    modifier: Modifier = Modifier,
    flowRow: Boolean = false,
    space: Dp = 4.dp,
    content: @Composable RowScope.() -> Unit,
) = if (flowRow) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(space),
        modifier = modifier,
        content = content
    )
} else {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space),
        modifier = modifier.horizontalScroll(rememberScrollState()),
        content = content
    )
}