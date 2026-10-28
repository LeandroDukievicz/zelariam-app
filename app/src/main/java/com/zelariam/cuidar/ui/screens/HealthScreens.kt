package com.zelariam.cuidar.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
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

private data class MedicationDemo(
    val name: String,
    val dose: String,
    val form: String,
    val time: String,
    val slot: String,
    val taken: Boolean = false,
    val takenAt: String = "",
    val byWhom: String = "",
    val next: Boolean = false,
)

private val medicationDemo = listOf(
    MedicationDemo("Losartana", "50 mg", "1 comprimido", "08:00", "Manhã", true, "08:12", "Roseli"),
    MedicationDemo("Metformina", "850 mg", "1 comprimido", "08:00", "Manhã", true, "08:13", "Roseli"),
    MedicationDemo("Vitamina D³", "7 gotas", "oral", "12:00", "Almoço", true, "12:04", "Roseli"),
    MedicationDemo("Atenolol", "25 mg", "1 comprimido", "14:30", "Tarde", next = true),
    MedicationDemo("Glifage XR", "500 mg", "1 comprimido", "20:00", "Noite"),
    MedicationDemo("Donepezila", "5 mg", "1 comprimido", "22:00", "Antes de dormir"),
)

@Composable
fun MedicationsScreen(
    onDose: () -> Unit,
    onIncidents: () -> Unit,
    onVitals: () -> Unit,
) {
    val p = LocalCuidarPalette.current
    ScreenColumn {
        ScreenGutter(top = 4, bottom = 12) {
            CuidarPageTitle(
                title = "Medicamentos",
                eyebrow = "Saúde · Cecília",
                trailing = { CuidarIconButton("plus", filled = true) },
            )
            Spacer(Modifier.height(12.dp))
            HealthTabs(active = "Medicamentos", onMedication = {}, onVitals = onVitals, onIncidents = onIncidents)
            Spacer(Modifier.height(14.dp))
            AdherenceCard()
        }

        listOf("Manhã", "Almoço", "Tarde", "Noite", "Antes de dormir").forEach { slot ->
            val items = medicationDemo.filter { it.slot == slot }
            if (items.isNotEmpty()) {
                ScreenGutter(bottom = 18) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 8.dp)) {
                        Box(Modifier.size(22.dp).clip(RoundedCornerShape(6.dp)).background(p.backgroundSoft), contentAlignment = Alignment.Center) {
                            CuidarIcon(slotIcon(slot), size = 14.dp, tint = p.primary)
                        }
                        CText(slot, size = 13.sp, weight = FontWeight.Bold)
                        CText("· ${items.first().time}", size = 12.sp, color = p.textMute)
                    }
                    CuidarCard(padding = 0.dp, radius = 18.dp) {
                        Column {
                            items.forEachIndexed { index, med ->
                                MedicationRow(med, last = index == items.lastIndex, onDose = onDose)
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun slotIcon(slot: String) = when (slot) {
    "Manhã", "Tarde" -> "sun"
    "Almoço" -> "food"
    else -> "moon"
}

@Composable
private fun AdherenceCard() {
    val p = LocalCuidarPalette.current
    CuidarCard(padding = 16.dp, radius = 20.dp, color = p.sageTint) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            Box(Modifier.size(56.dp), contentAlignment = Alignment.Center) {
                Canvas(Modifier.fillMaxSize()) {
                    drawCircle(color = p.line, style = Stroke(width = 3.dp.toPx()))
                    drawArc(
                        color = p.sage,
                        startAngle = -90f,
                        sweepAngle = 349f,
                        useCenter = false,
                        style = Stroke(width = 3.dp.toPx(), cap = androidx.compose.ui.graphics.StrokeCap.Round),
                    )
                }
                CText("97%", size = 14.sp, weight = FontWeight.Bold, color = p.sage)
            }
            Column(Modifier.weight(1f)) {
                CText("Adesão na semana", size = 15.sp, weight = FontWeight.Bold)
                CText("Apenas 1 dose perdida (Vit. D · seg). Ótimo trabalho da família!", size = 12.5.sp, color = p.textSoft, lineHeight = 17.sp, modifier = Modifier.padding(top = 2.dp))
            }
        }
    }
}

@Composable
private fun MedicationRow(med: MedicationDemo, last: Boolean, onDose: () -> Unit) {
    val p = LocalCuidarPalette.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (med.next) p.amberTint else Color.Transparent)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(
                when {
                    med.taken -> p.sageTint
                    med.next -> Color.White
                    else -> p.backgroundSoft
                },
            ),
            contentAlignment = Alignment.Center,
        ) {
            CuidarIcon("pill", size = 22.dp, tint = if (med.taken) p.sage else if (med.next) p.amber else p.primary)
        }
        Column(Modifier.weight(1f)) {
            CText("${med.name} · ${med.dose}", size = 14.5.sp, weight = FontWeight.SemiBold)
            CText(
                if (med.taken) "✓ ${med.takenAt} · por ${med.byWhom}" else med.form,
                size = 12.sp,
                color = p.textSoft,
                modifier = Modifier.padding(top = 1.dp),
            )
        }
        when {
            med.taken -> CuidarIcon("check", size = 20.dp, tint = p.sage)
            med.next -> CuidarButton("Tomei", style = CuidarButtonStyle.Primary, height = 36.dp, onClick = onDose)
            else -> Box(Modifier.size(24.dp).clip(RoundedCornerShape(8.dp)).border(1.5.dp, p.line, RoundedCornerShape(8.dp)))
        }
    }
    if (!last) Spacer(Modifier.fillMaxWidth().height(1.dp).background(p.lineSoft))
}

@Composable
fun DoseScreen(onClose: () -> Unit) {
    val p = LocalCuidarPalette.current
    Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = .32f))) {
        Column(Modifier.align(Alignment.TopCenter).fillMaxWidth().padding(20.dp).alpha(.3f)) {
            CText("Medicamentos", size = 24.sp, weight = FontWeight.Bold)
            repeat(2) {
                CuidarCard(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), padding = 14.dp, radius = 18.dp) {
                    CText("Losartana · 50 mg", size = 16.sp, weight = FontWeight.SemiBold)
                    CText("1 comprimido · manhã", size = 12.sp, color = p.textSoft)
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(.88f)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(p.card)
                .verticalScroll(rememberScrollState())
                .padding(start = 22.dp, end = 22.dp, top = 12.dp, bottom = 26.dp),
        ) {
            Box(Modifier.align(Alignment.CenterHorizontally).size(width = 38.dp, height = 4.dp).clip(RoundedCornerShape(2.dp)).background(p.line))
            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(Modifier.size(64.dp).clip(RoundedCornerShape(18.dp)).background(p.amberTint), contentAlignment = Alignment.Center) {
                    CuidarIcon("pill", size = 32.dp, tint = p.amber)
                }
                Column(Modifier.weight(1f)) {
                    CuidarBadge("Próxima dose", tone = CuidarTone.Amber, dot = true, small = true)
                    CText("Atenolol · 25 mg", size = 20.sp, weight = FontWeight.Bold, modifier = Modifier.padding(top = 5.dp))
                    CText("1 comprimido · 14:30", size = 13.sp, color = p.textSoft)
                }
            }
            Spacer(Modifier.height(18.dp))
            CuidarButton("Confirmar dose", modifier = Modifier.fillMaxWidth(), height = 64.dp, icon = "check", onClick = onClose)
            Spacer(Modifier.height(12.dp))
            DoseDetail("clock", "Horário", "Agora · 14:48", trailing = "Ajustar")
            Spacer(Modifier.height(10.dp))
            DoseDetail("user", "Quem deu", "Roseli Santos")
            Spacer(Modifier.height(10.dp))
            DoseDetail("note", "Observação (opcional)", "Ex: \"Tomou com suco de laranja.\"", italic = true)
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CuidarButton("Adiar 30 min", modifier = Modifier.weight(1f), style = CuidarButtonStyle.Ghost, height = 48.dp, onClick = onClose)
                CuidarButton("Não tomou", modifier = Modifier.weight(1f), style = CuidarButtonStyle.Ghost, height = 48.dp, onClick = onClose)
            }
        }
    }
}

@Composable
private fun DoseDetail(icon: String, label: String, value: String, trailing: String? = null, italic: Boolean = false) {
    val p = LocalCuidarPalette.current
    CuidarCard(padding = 14.dp, radius = 16.dp, color = p.backgroundSoft) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(p.card), contentAlignment = Alignment.Center) {
                CuidarIcon(icon, size = 19.dp, tint = p.primary)
            }
            Column(Modifier.weight(1f)) {
                CText(label, size = 11.5.sp, weight = FontWeight.SemiBold, color = p.textMute)
                CText(value, size = 15.sp, weight = FontWeight.SemiBold, color = if (italic) p.textMute else p.text, modifier = Modifier.padding(top = 2.dp))
            }
            if (trailing != null) CText(trailing, size = 13.sp, weight = FontWeight.SemiBold, color = p.primary)
        }
    }
}

private data class OccurrenceDemo(
    val whenText: String,
    val who: String,
    val initials: String,
    val tag: String,
    val tone: CuidarTone,
    val title: String,
    val body: String,
)

private val occurrenceDemos = listOf(
    OccurrenceDemo("hoje · 13:42", "Roseli", "RS", "humor", CuidarTone.Sage, "Almoçou bem e está animada", "Aceitou todo o prato e pediu para ligar para a Marina depois."),
    OccurrenceDemo("hoje · 11:15", "Roseli", "RS", "movimento", CuidarTone.Sky, "Caminhou 12 min no quarteirão", "Reclamou um pouco do joelho direito perto do fim, mas voltou bem."),
    OccurrenceDemo("ontem · 22:40", "Vanderlei", "VL", "sono", CuidarTone.Gold, "Demorou a pegar no sono", "Ficou inquieta até umas 23h30. Tomou um chá de camomila."),
    OccurrenceDemo("ontem · 16:08", "Roseli", "RS", "queda leve", CuidarTone.Amber, "Tropeçou no tapete da sala", "Sem batida na cabeça. Examinei o tornozelo, sem inchaço. Recolhi o tapete."),
    OccurrenceDemo("2 dias · 09:20", "Marina", "MA", "consulta", CuidarTone.Primary, "Cardio — Dr. Henrique", "Trocou Captopril por Atenolol 25mg. Próximo retorno em 60 dias."),
)

@Composable
fun IncidentsScreen(
    onMedications: () -> Unit,
    onVitals: () -> Unit,
) {
    val p = LocalCuidarPalette.current
    ScreenColumn {
        ScreenGutter(top = 4, bottom = 12) {
            CuidarPageTitle("Ocorrências", eyebrow = "Saúde · Cecília", trailing = { CuidarIconButton("plus", filled = true) })
            Spacer(Modifier.height(12.dp))
            HealthTabs(active = "Ocorrências", onMedication = onMedications, onVitals = onVitals, onIncidents = {})
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("Todas", "Quedas", "Humor", "Sono", "Alimentação", "Dor").forEachIndexed { index, label ->
                    CuidarBadge(label, tone = if (index == 0) CuidarTone.Primary else CuidarTone.Neutral, small = true)
                }
            }
        }

        occurrenceDemos.forEach { event ->
            ScreenGutter(bottom = 10) {
                CuidarCard(padding = 14.dp, radius = 18.dp) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
                        CuidarAvatar(event.initials, size = 40.dp, tone = event.tone)
                        Column(Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                CText(event.who, size = 13.5.sp, weight = FontWeight.Bold)
                                CuidarBadge(event.tag, tone = event.tone, small = true)
                                Spacer(Modifier.weight(1f))
                                CText(event.whenText, size = 10.5.sp, color = p.textMute)
                            }
                            CText(event.title, size = 14.sp, weight = FontWeight.SemiBold, lineHeight = 19.sp, modifier = Modifier.padding(top = 4.dp))
                            CText(event.body, size = 13.sp, color = p.textSoft, lineHeight = 18.sp, modifier = Modifier.padding(top = 4.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.padding(top = 10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    CuidarIcon("heart", size = 14.dp, tint = p.textMute)
                                    CText("Apoiar", size = 12.sp, color = p.textMute)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    CuidarIcon("note", size = 14.dp, tint = p.textMute)
                                    CText("Comentar", size = 12.sp, color = p.textMute)
                                }
                            }
                        }
                    }
                }
            }
        }

        ScreenGutter(bottom = 24) {
            CuidarCard(padding = 14.dp, radius = 18.dp, color = p.backgroundSoft, border = p.line) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(p.card), contentAlignment = Alignment.Center) {
                        CuidarIcon("mic", size = 20.dp, tint = p.primary)
                    }
                    Column(Modifier.weight(1f)) {
                        CText("Registrar por voz", size = 13.5.sp, weight = FontWeight.SemiBold)
                        CText("Fale e a gente organiza", size = 12.sp, color = p.textSoft)
                    }
                    CuidarButton("Gravar", style = CuidarButtonStyle.Soft, height = 36.dp)
                }
            }
        }
    }
}

@Composable
fun VitalsScreen(
    onMedications: () -> Unit,
    onIncidents: () -> Unit,
) {
    val p = LocalCuidarPalette.current
    ScreenColumn {
        ScreenGutter(top = 4, bottom = 12) {
            CuidarPageTitle("Sinais vitais", eyebrow = "Saúde · Cecília", trailing = { CuidarIconButton("plus", filled = true) })
            Spacer(Modifier.height(12.dp))
            HealthTabs(active = "Sinais vitais", onMedication = onMedications, onVitals = {}, onIncidents = onIncidents)
        }

        ScreenGutter(bottom = 14) {
            CuidarCard(padding = 16.dp, radius = 22.dp) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SmallIcon("heart", p.coralTint, p.coral)
                        CText("Pressão arterial", size = 13.5.sp, weight = FontWeight.Bold)
                    }
                    CText("14 dias", size = 12.sp, color = p.textSoft)
                }
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 8.dp)) {
                    CText("128", size = 36.sp, weight = FontWeight.Bold)
                    CText("/ 82", size = 18.sp, weight = FontWeight.SemiBold, color = p.textSoft, modifier = Modifier.padding(bottom = 2.dp))
                    CText("mmHg · 14:02", size = 12.sp, color = p.textMute, modifier = Modifier.padding(bottom = 4.dp))
                    Spacer(Modifier.weight(1f))
                    CuidarBadge("estável", tone = CuidarTone.Sage, dot = true, small = true)
                }
                Spacer(Modifier.height(8.dp))
                VitalChart(listOf(124f, 130f, 128f, 122f, 134f, 126f, 131f, 125f, 129f, 128f, 122f, 127f, 130f, 128f), p.coral, 64)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    CText("9 mai", size = 10.5.sp, color = p.textMute)
                    CText("16 mai", size = 10.5.sp, color = p.textMute)
                    CText("hoje", size = 10.5.sp, color = p.textMute)
                }
            }
        }

        ScreenGutter(bottom = 14) {
            CuidarCard(padding = 16.dp, radius = 22.dp) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SmallIcon("droplet", p.goldTint, p.gold)
                        CText("Glicemia em jejum", size = 13.5.sp, weight = FontWeight.Bold)
                    }
                    CText("14 dias", size = 12.sp, color = p.textSoft)
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp), modifier = Modifier.padding(top = 8.dp)) {
                    CText("118", size = 30.sp, weight = FontWeight.Bold)
                    CText("mg/dL", size = 13.sp, weight = FontWeight.SemiBold, color = p.textSoft)
                    Spacer(Modifier.weight(1f))
                    CuidarBadge("↑ acima da meta", tone = CuidarTone.Gold, small = true)
                }
                Spacer(Modifier.height(8.dp))
                VitalChart(listOf(110f, 115f, 108f, 112f, 119f, 121f, 116f, 118f, 124f, 122f, 115f, 117f, 120f, 118f), p.gold, 50)
            }
        }

        ScreenGutter(bottom = 16) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                VitalMiniCard("Freq. cardíaca", "74", "bpm", "sparkle", CuidarTone.Primary, "hoje", Modifier.weight(1f))
                VitalMiniCard("Saturação", "96", "% SpO₂", "shield", CuidarTone.Sage, "hoje", Modifier.weight(1f))
            }
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                VitalMiniCard("Peso", "62.4", "kg", "weight", CuidarTone.Sky, "−0.3 / 7d", Modifier.weight(1f))
                VitalMiniCard("Temperatura", "36.4", "°C", "thermo", CuidarTone.Sage, "hoje", Modifier.weight(1f))
            }
        }

        ScreenGutter(bottom = 24) {
            CuidarButton("Registrar medição", modifier = Modifier.fillMaxWidth(), style = CuidarButtonStyle.Soft, height = 48.dp, icon = "plus")
        }
    }
}

@Composable
private fun SmallIcon(icon: String, background: Color, foreground: Color) {
    Box(Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(background), contentAlignment = Alignment.Center) {
        CuidarIcon(icon, size = 16.dp, tint = foreground)
    }
}

@Composable
private fun VitalMiniCard(
    label: String,
    value: String,
    unit: String,
    icon: String,
    tone: CuidarTone,
    extra: String,
    modifier: Modifier = Modifier,
) {
    val p = LocalCuidarPalette.current
    val (tint, foreground) = when (tone) {
        CuidarTone.Sage -> p.sageTint to p.sage
        CuidarTone.Sky -> p.skyTint to p.sky
        CuidarTone.Gold -> p.goldTint to p.gold
        else -> p.primaryTint to p.primary
    }
    CuidarCard(modifier = modifier, padding = 14.dp, radius = 18.dp) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            SmallIcon(icon, tint, foreground)
            CText(extra, size = 10.5.sp, weight = FontWeight.SemiBold, color = p.textMute)
        }
        CText(label, size = 11.sp, weight = FontWeight.SemiBold, color = p.textMute, modifier = Modifier.padding(top = 8.dp))
        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(top = 2.dp)) {
            CText(value, size = 22.sp, weight = FontWeight.Bold)
            CText(unit, size = 11.sp, weight = FontWeight.SemiBold, color = p.textSoft, modifier = Modifier.padding(bottom = 2.dp))
        }
    }
}

@Composable
internal fun VitalChart(values: List<Float>, color: Color, height: Int) {
    Canvas(Modifier.fillMaxWidth().height(height.dp)) {
        val minValue = values.minOrNull() ?: 0f
        val maxValue = values.maxOrNull() ?: 1f
        val span = (maxValue - minValue).coerceAtLeast(1f)
        val points = values.mapIndexed { index, value ->
            Offset(
                x = size.width * index / (values.size - 1),
                y = size.height - ((value - minValue + 4f) / (span + 8f)) * size.height,
            )
        }
        val area = Path().apply {
            moveTo(points.first().x, size.height)
            points.forEachIndexed { index, point -> if (index == 0) lineTo(point.x, point.y) else lineTo(point.x, point.y) }
            lineTo(points.last().x, size.height)
            close()
        }
        drawPath(area, color.copy(alpha = .12f))
        val line = Path().apply {
            points.forEachIndexed { index, point -> if (index == 0) moveTo(point.x, point.y) else lineTo(point.x, point.y) }
        }
        drawPath(line, color, style = Stroke(width = 2.dp.toPx()))
        drawCircle(color, radius = 2.6.dp.toPx(), center = points.last())
    }
}

@Composable
private fun HealthTabs(
    active: String,
    onMedication: () -> Unit,
    onVitals: () -> Unit,
    onIncidents: () -> Unit,
) {
    val p = LocalCuidarPalette.current
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        listOf("Medicamentos" to onMedication, "Sinais vitais" to onVitals, "Ocorrências" to onIncidents).forEach { (label, callback) ->
            val selected = label == active
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (selected) p.text else p.card)
                    .then(if (selected) Modifier else Modifier.border(1.dp, p.lineSoft, CircleShape))
                    .clickable(onClick = callback)
                    .padding(horizontal = 13.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                CText(label, size = 12.5.sp, weight = FontWeight.SemiBold, color = if (selected) p.background else p.textSoft)
            }
        }
    }
}
