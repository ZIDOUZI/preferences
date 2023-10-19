package zdz.libs.preferences.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zdz.libs.preferences.compose.contracts.PreferenceGroupScope

@Composable
fun Preference(
    divider: Boolean = true,
    content: @Composable (PreferenceAreaScope.() -> Unit),
) = PreferenceAreaScope(divider).content()

@Composable
fun PreferenceArea(
    modifier: Modifier = Modifier,
    divider: Boolean = true,
    padding: Dp = 5.dp,
    content: @Composable PreferenceAreaScope.() -> Unit,
): Unit = Column(
    modifier = modifier.verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.spacedBy(padding)
) {
    PreferenceAreaScope(divider).content()
}

class PreferenceAreaScope internal constructor(
    private val divider: Boolean,
) {
    @Composable
    fun Group(
        title: String,
        modifier: Modifier = Modifier,
        visible: Boolean = true,
        content: @Composable PreferenceGroupScope.() -> Unit
    ) = if (visible) Column(
        modifier = Modifier
            .padding(start = 4.dp)
            .fillMaxWidth()
            .then(modifier)
            .background(color = colorScheme.background, shape = shapes.small)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = colorScheme.tertiary
        )
        if (divider) HorizontalDivider()
        PreferenceGroupImpl().content()
    } else Unit
}