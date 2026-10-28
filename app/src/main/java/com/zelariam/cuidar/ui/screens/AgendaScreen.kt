package com.zelariam.cuidar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zelariam.cuidar.ui.components.CText
import com.zelariam.cuidar.ui.components.CuidarBadge
import com.zelariam.cuidar.ui.components.CuidarCard
import com.zelariam.cuidar.ui.components.CuidarIcon
import com.zelariam.cuidar.ui.components.CuidarIconButton
import com.zelariam.cuidar.ui.components.CuidarSectionTitle
import com.zelariam.cuidar.ui.components.CuidarTone
import com.zelariam.cuidar.ui.components.ScreenColumn
import com.zelariam.cuidar.ui.components.ScreenGutter
import com.zelariam.cuidar.ui.theme.LocalCuidarPalette

private data class AppointmentDemo(
    val date: String,
    val day: String,
    val month: String,
    val time: String,
    val title: String,
    val specialty: String,
    val place: String,
    val tone: CuidarTone,
    val soon: Boolean = false,
)

private val appointments = listOf(
    AppointmentDemo("sex, 23 mai", "23", "mai", "14:30", "Dra. Helena Vieira", "Endocrinologia", "H. Sírio · 6º and.", CuidarTone.Primary, true),
    AppointmentDemo("qua, 28 mai", "28", "mai", "09:00", "Coleta de exames", "Lab. Fleury", "Em jejum 12h", CuidarTone.Sky),
    AppointmentDemo("ter, 03 jun", "03", "jun", "10:30", "Dr. Henrique Costa", "Cardiologia · retorno", "Tele consulta", CuidarTone.Sage),
    AppointmentDemo("qui, 12 jun", "12", "jun", "15:00", "Fisioterapia", "Reabilita Pinheiros", "5ª sessão de 10", CuidarTone.Gold),
)

@Composable
fun AgendaScreen(onCaregivers: () -> Unit) {
    val p = LocalCuidarPalette.current
    ScreenColumn {
        ScreenGutter(top = 4, bottom = 14) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    CText("Agenda", size = 26.sp, weight = FontWeight.Bold)
                    CText("maio · 2026", size = 13.sp, color = p.textSoft, modifier = Modifier.padding(top = 1.dp))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CuidarIconButton("search")
                    CuidarIconButton("plus", filled = true)
                }
            }
            Spacer(Modifier.height(14.dp))
            MiniCalendar()
        }

        ScreenGutter(bottom = 16) {
            CuidarSectionTitle("Hoje")
            Spacer(Modifier.height(10.dp))
            CuidarCard(padding = 0.dp, radius = 18.dp, onClick = onCaregivers) {
                Row(
                    modifier = Modifier.fillMaxWidth().background(p.primaryTint).padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Box(Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(p.card), contentAlignment = Alignment.Center) {
                        CuidarIcon("clock", size = 22.dp, tint = p.primary)
                    }
                    Column(Modifier.weight(1f)) {
                        CText("Plantão · Roseli", size = 14.sp, weight = FontWeight.Bold)
                        CText("iniciou às 08:00 · termina 18:00", size = 12.5.sp, color = p.textSoft)
                    }
                    CuidarBadge("em curso", tone = CuidarTone.Primary, dot = true)
                }
            }
        }

        ScreenGutter(bottom = 10) {
            CuidarSectionTitle("Próximas consultas", action = "Ver tudo")
            Spacer(Modifier.height(10.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                appointments.forEach { appointment -> AppointmentCard(appointment) }
            }
        }
    }
}

@Composable
private fun MiniCalendar() {
    val p = LocalCuidarPalette.current
    CuidarCard(padding = 12.dp, radius = 18.dp) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("D", "S", "T", "Q", "Q", "S", "S").forEach { day ->
                CText(day, size = 10.5.sp, weight = FontWeight.SemiBold, color = p.textMute)
            }
        }
        Spacer(Modifier.height(6.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            (18..24).forEach { day ->
                val today = day == 22
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(36.dp)) {
                    Box(
                        Modifier.size(30.dp).clip(RoundedCornerShape(10.dp)).background(if (today) p.primary else p.card),
                        contentAlignment = Alignment.Center,
                    ) {
                        CText(day.toString(), size = 14.sp, weight = if (today) FontWeight.Bold else FontWeight.Medium, color = if (today) p.card else p.text)
                    }
                    if (!today && day in listOf(23, 24)) Box(Modifier.padding(top = 2.dp).size(4.dp).clip(RoundedCornerShape(2.dp)).background(p.primary))
                }
            }
        }
    }
}

@Composable
private fun AppointmentCard(item: AppointmentDemo) {
    val p = LocalCuidarPalette.current
    val (tint, foreground) = when (item.tone) {
        CuidarTone.Primary -> p.primaryTint to p.primaryDeep
        CuidarTone.Sky -> p.skyTint to p.sky
        CuidarTone.Sage -> p.sageTint to p.sage
        CuidarTone.Gold -> p.goldTint to p.gold
        else -> p.primaryTint to p.primary
    }
    CuidarCard(padding = 0.dp, radius = 18.dp) {
        Row(Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.width(64.dp).background(tint).padding(vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CText(item.date.substringBefore(','), size = 10.sp, weight = FontWeight.Bold, color = foreground)
                CText(item.day, size = 19.sp, weight = FontWeight.Bold, color = foreground, lineHeight = 21.sp)
                CText(item.month, size = 11.5.sp, weight = FontWeight.SemiBold, color = foreground)
            }
            Column(Modifier.weight(1f).padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    CText(item.time, size = 12.5.sp, weight = FontWeight.SemiBold, color = p.textSoft)
                    if (item.soon) CuidarBadge("amanhã", tone = CuidarTone.Amber, dot = true, small = true)
                }
                CText(item.title, size = 14.5.sp, weight = FontWeight.SemiBold, modifier = Modifier.padding(top = 3.dp))
                CText(item.specialty, size = 12.5.sp, color = p.textSoft, modifier = Modifier.padding(top = 2.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(top = 3.dp)) {
                    CuidarIcon("home", size = 12.dp, tint = p.textMute)
                    CText(item.place, size = 11.5.sp, color = p.textMute)
                }
            }
        }
    }
}
