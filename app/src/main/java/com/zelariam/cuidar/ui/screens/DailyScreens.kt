package com.zelariam.cuidar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zelariam.cuidar.ui.components.CText
import com.zelariam.cuidar.ui.components.CuidarAvatar
import com.zelariam.cuidar.ui.components.CuidarBadge
import com.zelariam.cuidar.ui.components.CuidarButton
import com.zelariam.cuidar.ui.components.CuidarButtonStyle
import com.zelariam.cuidar.ui.components.CuidarCard
import com.zelariam.cuidar.ui.components.CuidarIcon
import com.zelariam.cuidar.ui.components.CuidarIconButton
import com.zelariam.cuidar.ui.components.CuidarPageTitle
import com.zelariam.cuidar.ui.components.CuidarSectionTitle
import com.zelariam.cuidar.ui.components.CuidarTone
import com.zelariam.cuidar.ui.components.ScreenColumn
import com.zelariam.cuidar.ui.components.ScreenGutter
import com.zelariam.cuidar.ui.theme.LocalCuidarPalette

@Composable
fun DashboardScreen(
    onDose: () -> Unit,
    onRoutine: () -> Unit,
    onCaregivers: () -> Unit,
    onAgenda: () -> Unit,
    onAlerts: () -> Unit,
    onElderMode: () -> Unit,
) {
    val p = LocalCuidarPalette.current
    ScreenColumn {
        ScreenGutter(top = 4, bottom = 16) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    CText("Boa tarde, Marina", size = 13.sp, weight = FontWeight.Medium, color = p.textSoft)
                    CText("Hoje, qui · 22 mai", size = 26.sp, weight = FontWeight.Bold, modifier = Modifier.padding(top = 2.dp))
                }
                Box {
                    CuidarIconButton("bell", onClick = onAlerts)
                    Box(Modifier.align(Alignment.TopEnd).padding(7.dp).size(10.dp).clip(CircleShape).background(p.amber).border(2.dp, p.card, CircleShape))
                }
            }
            Spacer(Modifier.height(16.dp))
            CuidarCard(padding = 0.dp, radius = 24.dp, onClick = onElderMode) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().background(Brush.linearGradient(listOf(p.primaryTint, p.goldTint))).padding(18.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CuidarAvatar("CA", size = 56.dp, tone = CuidarTone.Primary)
                        Column(Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CText("Cecília", size = 17.sp, weight = FontWeight.Bold)
                                CText(" · 78 anos", size = 13.sp, color = p.textSoft)
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.padding(top = 6.dp)) {
                                CuidarBadge("Bem hoje", tone = CuidarTone.Sage, dot = true, small = true)
                                CuidarBadge("Roseli no plantão", small = true)
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        QuickMetric("heart", "128/82", "Pressão", CuidarTone.Sage)
                        QuickMetric("droplet", "118", "Glicemia", CuidarTone.Gold)
                        QuickMetric("sparkle", "74", "FC", CuidarTone.Sage)
                        QuickMetric("moon", "7h2", "Sono", CuidarTone.Sage)
                    }
                }
            }
        }

        ScreenGutter(bottom = 16) {
            CuidarCard(padding = 0.dp, radius = 22.dp) {
                Column {
                    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                        Box(Modifier.size(52.dp).clip(RoundedCornerShape(16.dp)).background(p.amberTint), contentAlignment = Alignment.Center) {
                            CuidarIcon("pill", size = 28.dp, tint = p.amber)
                        }
                        Column(Modifier.weight(1f)) {
                            CuidarBadge("Em 18 min", tone = CuidarTone.Amber, dot = true, small = true)
                            CText("Atenolol · 25 mg", size = 16.sp, weight = FontWeight.Bold, modifier = Modifier.padding(top = 3.dp))
                            CText("14:30 · 1 comprimido", size = 13.sp, color = p.textSoft)
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        CuidarButton("Confirmar dose", modifier = Modifier.weight(1f), height = 48.dp, icon = "check", onClick = onDose)
                        CuidarButton("", modifier = Modifier.width(48.dp), style = CuidarButtonStyle.Soft, height = 48.dp, icon = "clock", onClick = onDose)
                    }
                }
            }
        }

        ScreenGutter(bottom = 16) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CuidarCard(modifier = Modifier.weight(1f), padding = 14.dp, radius = 18.dp, onClick = onCaregivers) {
                    Column {
                        CText("EM PLANTÃO", size = 10.5.sp, weight = FontWeight.SemiBold, color = p.textMute)
                        Row(modifier = Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                            CuidarAvatar("RS", size = 36.dp)
                            Column {
                                CText("Roseli S.", size = 13.sp, weight = FontWeight.Bold)
                                CText("até 18:00", size = 11.5.sp, color = p.textSoft)
                            }
                        }
                    }
                }
                CuidarCard(modifier = Modifier.weight(1f), padding = 14.dp, radius = 18.dp, onClick = onAgenda) {
                    Column {
                        CText("PRÓX. CONSULTA", size = 10.5.sp, weight = FontWeight.SemiBold, color = p.textMute)
                        Row(modifier = Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                            Box(Modifier.size(36.dp).clip(RoundedCornerShape(12.dp)).background(p.sageTint), contentAlignment = Alignment.Center) {
                                CuidarIcon("calendar", size = 18.dp, tint = p.sage)
                            }
                            Column {
                                CText("sex, 14:30", size = 13.sp, weight = FontWeight.Bold)
                                CText("Dra. Helena", size = 11.5.sp, color = p.textSoft)
                            }
                        }
                    }
                }
            }
        }

        ScreenGutter(bottom = 10) {
            CuidarSectionTitle("Pendentes hoje", action = "Ver rotina", onAction = onRoutine)
            Spacer(Modifier.height(10.dp))
            CuidarCard(padding = 0.dp, radius = 20.dp) {
                Column {
                    listOf(
                        "14:00" to "Soneca curta",
                        "14:30" to "Atenolol",
                        "16:00" to "Lanche da tarde",
                        "17:00" to "Ligar para o Pedro",
                    ).forEachIndexed { index, item ->
                        PendingTask(item.first, item.second, late = index == 1, last = index == 3)
                    }
                }
            }
        }

        ScreenGutter(bottom = 16) {
            CuidarSectionTitle("Última ocorrência")
            Spacer(Modifier.height(10.dp))
            CuidarCard(padding = 14.dp, radius = 18.dp) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
                    CuidarAvatar("RS", size = 36.dp)
                    Column(Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            CText("Roseli", size = 13.sp, weight = FontWeight.Bold)
                            CuidarBadge("humor", tone = CuidarTone.Sage, small = true)
                            Spacer(Modifier.weight(1f))
                            CText("13:42", size = 11.5.sp, color = p.textMute)
                        }
                        CText("Almoçou bem e está animada", size = 13.5.sp, weight = FontWeight.Medium, modifier = Modifier.padding(top = 4.dp))
                        CText("Aceitou todo o prato e pediu para ligar para a Marina depois.", size = 12.5.sp, color = p.textSoft, lineHeight = 18.sp, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        }

        ScreenGutter(bottom = 24) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CuidarButton("Pedir ajuda", modifier = Modifier.weight(1f), style = CuidarButtonStyle.Coral, height = 56.dp, icon = "sos", onClick = onAlerts)
                CuidarButton("Registrar", modifier = Modifier.weight(1f), height = 56.dp, icon = "plus", onClick = onAlerts)
            }
        }
    }
}

@Composable
private fun QuickMetric(icon: String, value: String, label: String, tone: CuidarTone) {
    val p = LocalCuidarPalette.current
    val tint = when (tone) {
        CuidarTone.Sage -> p.sage
        CuidarTone.Gold -> p.gold
        else -> p.primary
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(72.dp)) {
        CuidarIcon(icon, size = 18.dp, tint = tint)
        CText(value, size = 15.sp, weight = FontWeight.Bold, modifier = Modifier.padding(top = 2.dp))
        CText(label, size = 10.5.sp, weight = FontWeight.Medium, color = p.textMute)
    }
}

@Composable
private fun PendingTask(time: String, title: String, late: Boolean, last: Boolean) {
    val p = LocalCuidarPalette.current
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            Modifier
                .size(22.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(if (late) p.amberTint else Color.Transparent)
                .border(1.5.dp, if (late) p.amber else p.line, RoundedCornerShape(7.dp)),
        )
        Column(Modifier.weight(1f)) {
            CText(title, size = 14.sp, weight = FontWeight.SemiBold)
            CText("$time · ${if (title == "Atenolol") "remédio" else "rotina"}", size = 11.5.sp, color = p.textSoft)
        }
        if (late) CuidarBadge("atrasado", tone = CuidarTone.Amber, dot = true, small = true)
    }
    if (!last) Spacer(Modifier.fillMaxWidth().padding(horizontal = 14.dp).height(1.dp).background(p.lineSoft))
}

private data class RoutineItem(
    val time: String,
    val title: String,
    val tag: String,
    val tone: CuidarTone,
    val done: Boolean = false,
    val current: Boolean = false,
    val alert: Boolean = false,
)

private val routineItems = listOf(
    RoutineItem("07:30", "Acordar e medir pressão", "saúde", CuidarTone.Sage, done = true),
    RoutineItem("08:00", "Café da manhã + remédios", "refeição", CuidarTone.Gold, done = true),
    RoutineItem("09:30", "Alongamento leve (15 min)", "movimento", CuidarTone.Sky, done = true),
    RoutineItem("11:00", "Caminhada no quarteirão", "movimento", CuidarTone.Sky, done = true),
    RoutineItem("12:00", "Almoço + Vitamina D", "refeição", CuidarTone.Gold, done = true),
    RoutineItem("14:00", "Soneca curta", "descanso", CuidarTone.Primary, current = true),
    RoutineItem("14:30", "Atenolol", "remédio", CuidarTone.Amber, alert = true),
    RoutineItem("16:00", "Lanche da tarde", "refeição", CuidarTone.Gold),
    RoutineItem("17:00", "Ligar para o Pedro", "social", CuidarTone.Primary),
    RoutineItem("19:30", "Jantar leve", "refeição", CuidarTone.Gold),
    RoutineItem("20:00", "Glifage XR", "remédio", CuidarTone.Amber),
    RoutineItem("21:30", "Banho morno", "higiene", CuidarTone.Sage),
    RoutineItem("22:00", "Donepezila + dormir", "remédio", CuidarTone.Amber),
)

@Composable
fun RoutineScreen(onMedication: () -> Unit) {
    val p = LocalCuidarPalette.current
    val doneCount = routineItems.count { it.done }
    ScreenColumn {
        ScreenGutter(top = 4, bottom = 14) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    CText("Rotina", size = 26.sp, weight = FontWeight.Bold)
                    CText("quinta · 22 de maio", size = 13.sp, color = p.textSoft, modifier = Modifier.padding(top = 1.dp))
                }
                CuidarIconButton("plus", filled = true)
            }
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("qua" to "21", "qui" to "22", "sex" to "23", "sáb" to "24", "dom" to "25", "seg" to "26", "ter" to "27").forEachIndexed { i, pair ->
                    DayChip(pair.first, pair.second, today = i == 1, completed = i == 0, modifier = Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(14.dp))
            CuidarCard(padding = 14.dp, radius = 18.dp) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    CText("$doneCount de ${routineItems.size} concluídos", size = 13.5.sp, weight = FontWeight.SemiBold)
                    CText("${doneCount * 100 / routineItems.size}% do dia", size = 12.sp, color = p.textSoft)
                }
                Spacer(Modifier.height(8.dp))
                Box(Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)).background(p.backgroundSoft)) {
                    Box(Modifier.fillMaxWidth(doneCount.toFloat() / routineItems.size).height(8.dp).clip(RoundedCornerShape(4.dp)).background(Brush.horizontalGradient(listOf(p.sage, p.primary))))
                }
            }
        }

        ScreenGutter(bottom = 24) {
            Box {
                Box(Modifier.padding(start = 72.dp, top = 14.dp, bottom = 14.dp).width(2.dp).fillMaxWidth().background(p.lineSoft))
                Column {
                    routineItems.forEach { item ->
                        RoutineTimelineRow(item = item, onClick = if (item.tag == "remédio") onMedication else null)
                    }
                }
            }
        }
    }
}

@Composable
private fun DayChip(day: String, number: String, today: Boolean, completed: Boolean, modifier: Modifier = Modifier) {
    val p = LocalCuidarPalette.current
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (today) p.primary else p.card)
            .then(if (today) Modifier else Modifier.border(1.dp, p.lineSoft, RoundedCornerShape(14.dp)))
            .padding(vertical = 9.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CText(day.uppercase(), size = 10.sp, weight = FontWeight.SemiBold, color = if (today) Color.White.copy(alpha = .8f) else p.textMute)
        CText(number, size = 17.sp, weight = FontWeight.Bold, color = if (today) Color.White else p.text, modifier = Modifier.padding(top = 2.dp))
        if (completed && !today) Box(Modifier.padding(top = 4.dp).size(5.dp).clip(CircleShape).background(p.sage))
        else Spacer(Modifier.height(5.dp))
    }
}

@Composable
private fun RoutineTimelineRow(item: RoutineItem, onClick: (() -> Unit)?) {
    val p = LocalCuidarPalette.current
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        CText(item.time, modifier = Modifier.width(50.dp).padding(top = 14.dp), size = 12.sp, weight = FontWeight.SemiBold, color = if (item.current) p.primary else p.textSoft)
        Box(Modifier.width(12.dp).padding(top = 16.dp), contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(p.background)
                    .border(2.dp, if (item.done) p.sage else if (item.alert) p.amber else p.line, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                if (item.done) CuidarIcon("check", size = 10.dp, tint = p.sage)
                if (item.current) Box(Modifier.size(6.dp).clip(CircleShape).background(p.primary))
            }
        }
        CuidarCard(
            modifier = Modifier.weight(1f),
            padding = 12.dp,
            radius = 14.dp,
            color = when {
                item.current -> p.primaryTint
                item.alert -> p.amberTint
                item.done -> p.cardAlt
                else -> p.card
            },
            border = if (item.current) p.primary else p.lineSoft,
            onClick = onClick,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(Modifier.weight(1f)) {
                    CText(
                        item.title,
                        size = 14.sp,
                        weight = FontWeight.SemiBold,
                        color = p.text,
                        decoration = if (item.done) TextDecoration.LineThrough else TextDecoration.None,
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 4.dp)) {
                        CuidarBadge(item.tag, tone = item.tone, small = true)
                        if (item.alert) CuidarBadge("atrasado", tone = CuidarTone.Amber, dot = true, small = true)
                        if (item.current) CuidarBadge("agora", tone = CuidarTone.Primary, dot = true, small = true)
                    }
                }
                if (!item.done) {
                    Box(
                        Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(9.dp))
                            .border(1.5.dp, if (item.alert) p.amber else p.line, RoundedCornerShape(9.dp)),
                    )
                }
            }
        }
    }
}
