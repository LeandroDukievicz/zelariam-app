package com.zelariam.cuidar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zelariam.cuidar.ui.theme.LocalCuidarPalette

data class BottomTab(val route: String, val label: String, val icon: String)

val CuidarTabs = listOf(
    BottomTab("hoje", "Hoje", "sun"),
    BottomTab("rotina", "Rotina", "check"),
    BottomTab("medicamentos", "Saúde", "heart"),
    BottomTab("agenda", "Agenda", "calendar"),
    BottomTab("alertas", "Mais", "more"),
)

@Composable
fun CuidarBottomBar(
    activeRoute: String,
    onNavigate: (String) -> Unit,
) {
    val p = LocalCuidarPalette.current
    val activeTab = when (activeRoute) {
        "rotina" -> "rotina"
        "medicamentos", "dose", "ocorrencias", "vitais" -> "medicamentos"
        "agenda" -> "agenda"
        "alertas", "cuidadores", "familia", "relatorio" -> "alertas"
        else -> "hoje"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(95.dp)
            .background(p.card)
            .border(width = 1.dp, color = p.lineSoft)
            .padding(start = 4.dp, end = 4.dp, top = 1.dp, bottom = 8.dp),
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            CuidarTabs.forEach { tab ->
                val selected = tab.route == activeTab
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clickable { onNavigate(tab.route) }
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                        if (selected) Box(
                            modifier = Modifier
                                .width(36.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(p.primary),
                        )
                    }
                    Spacer(Modifier.height(5.dp))
                    CuidarIcon(tab.icon, size = 23.dp, tint = if (selected) p.primary else p.textMute)
                    CText(tab.label, size = 10.5.sp, weight = if (selected) FontWeight.Bold else FontWeight.Medium, color = if (selected) p.primary else p.textMute)
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(134.dp)
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(p.text.copy(alpha = .25f)),
        )
    }
}

@Composable
fun ScreenColumn(
    modifier: Modifier = Modifier,
    scroll: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    val p = LocalCuidarPalette.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(p.background)
            .then(if (scroll) Modifier.verticalScroll(rememberScrollState()) else Modifier)
            .padding(bottom = 24.dp),
        content = content,
    )
}

@Composable
fun ScreenGutter(
    modifier: Modifier = Modifier,
    top: Int = 0,
    bottom: Int = 0,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier.padding(horizontal = 20.dp).padding(top = top.dp, bottom = bottom.dp)) {
        content()
    }
}

@Composable
fun CuidarDivider(modifier: Modifier = Modifier, color: Color? = null) {
    val p = LocalCuidarPalette.current
    Box(modifier.fillMaxWidth().height(1.dp).background(color ?: p.lineSoft))
}
