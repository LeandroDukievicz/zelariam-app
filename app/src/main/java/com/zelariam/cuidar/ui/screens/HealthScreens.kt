package com.zelariam.cuidar.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.zelariam.cuidar.ui.state.DemoDoseStatus
import com.zelariam.cuidar.ui.state.DemoOccurrence
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text

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
    val statusText: String? = null,
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
    doseStatus: DemoDoseStatus,
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
                                val shownMedication = if (med.name == "Atenolol") {
                                    med.copy(
                                        taken = doseStatus == DemoDoseStatus.Taken,
                                        next = doseStatus != DemoDoseStatus.Taken,
                                        takenAt = when (doseStatus) {
                                            DemoDoseStatus.Taken -> "14:48"
                                            DemoDoseStatus.Snoozed -> "Adiada até 15:18"
                                            DemoDoseStatus.NotTaken -> "Não tomada nesta sessão"
                                            DemoDoseStatus.Pending -> ""
                                        },
                                        byWhom = "Marina",
                                        statusText = when (doseStatus) {
                                            DemoDoseStatus.Pending -> null
                                            DemoDoseStatus.Taken -> null
                                            DemoDoseStatus.Snoozed -> "Adiada até 15:18 nesta sessão"
                                            DemoDoseStatus.NotTaken -> "Não tomada nesta sessão"
                                        },
                                    )
                                } else med
                                MedicationRow(shownMedication, last = index == items.lastIndex, onDose = onDose)
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
                if (med.taken) "✓ ${med.takenAt} · por ${med.byWhom}" else med.statusText ?: med.form,
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
fun DoseScreen(
    status: DemoDoseStatus,
    onClose: () -> Unit,
    onConfirm: () -> Unit,
    onSnooze: () -> Unit,
    onNotTaken: () -> Unit,
    onCorrect: () -> Unit,
) {
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
                    CuidarBadge(
                        text = when (status) {
                            DemoDoseStatus.Pending -> "Próxima dose"
                            DemoDoseStatus.Taken -> "Dose registrada"
                            DemoDoseStatus.Snoozed -> "Adiada 30 min"
                            DemoDoseStatus.NotTaken -> "Não tomada"
                        },
                        tone = if (status == DemoDoseStatus.Taken) CuidarTone.Sage else CuidarTone.Amber,
                        dot = true,
                        small = true,
                    )
                    CText("Atenolol · 25 mg", size = 20.sp, weight = FontWeight.Bold, modifier = Modifier.padding(top = 5.dp))
                    CText("1 comprimido · 14:30", size = 13.sp, color = p.textSoft)
                }
            }
            Spacer(Modifier.height(18.dp))
            CuidarButton(
                text = if (status == DemoDoseStatus.Taken) "Corrigir registro" else "Confirmar dose",
                modifier = Modifier.fillMaxWidth(),
                height = 64.dp,
                icon = if (status == DemoDoseStatus.Taken) "note" else "check",
                onClick = if (status == DemoDoseStatus.Taken) onCorrect else onConfirm,
            )
            Spacer(Modifier.height(12.dp))
            DoseDetail("clock", "Horário", if (status == DemoDoseStatus.Snoozed) "Adiada até 15:18" else "Agora · 14:48", trailing = "Ajustar")
            Spacer(Modifier.height(10.dp))
            DoseDetail("user", "Quem deu", "Roseli Santos")
            Spacer(Modifier.height(10.dp))
            DoseDetail("note", "Observação (opcional)", "Ex: \"Tomou com suco de laranja.\"", italic = true)
            Spacer(Modifier.height(14.dp))
            if (status != DemoDoseStatus.Taken) Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CuidarButton("Adiar 30 min", modifier = Modifier.weight(1f), style = CuidarButtonStyle.Ghost, height = 48.dp, onClick = onSnooze)
                CuidarButton("Não tomou", modifier = Modifier.weight(1f), style = CuidarButtonStyle.Ghost, height = 48.dp, onClick = onNotTaken)
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

@Composable
fun IncidentsScreen(
    onMedications: () -> Unit,
    onVitals: () -> Unit,
    occurrences: List<DemoOccurrence>,
    canUndoLastOccurrence: Boolean,
    onAddOccurrence: (String, String, String) -> Unit,
    onUndoLastOccurrence: () -> Unit,
) {
    val p = LocalCuidarPalette.current
    var selectedFilter by remember { mutableStateOf("Todas") }
    var showAddDialog by remember { mutableStateOf(false) }
    val filterTags = mapOf(
        "Quedas" to setOf("queda", "queda leve"),
        "Humor" to setOf("humor"),
        "Sono" to setOf("sono"),
        "Alimentação" to setOf("alimentação"),
        "Dor" to setOf("dor"),
    )
    val shownOccurrences = if (selectedFilter == "Todas") occurrences else {
        occurrences.filter { it.tag in filterTags[selectedFilter].orEmpty() }
    }
    ScreenColumn {
        ScreenGutter(top = 4, bottom = 12) {
            CuidarPageTitle("Ocorrências", eyebrow = "Saúde · Cecília", trailing = { CuidarIconButton("plus", filled = true, onClick = { showAddDialog = true }) })
            Spacer(Modifier.height(12.dp))
            HealthTabs(active = "Ocorrências", onMedication = onMedications, onVitals = onVitals, onIncidents = {})
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
                listOf("Todas", "Quedas", "Humor", "Sono", "Alimentação", "Dor").forEach { label ->
                    FilterChip(label, selected = selectedFilter == label, onClick = { selectedFilter = label })
                }
            }
        }

        if (canUndoLastOccurrence) ScreenGutter(bottom = 12) {
            CuidarCard(padding = 12.dp, radius = 14.dp, color = p.primaryTint) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    CText("Ocorrência demonstrativa adicionada", size = 12.5.sp, weight = FontWeight.SemiBold)
                    CuidarButton("Desfazer", style = CuidarButtonStyle.Ghost, height = 36.dp, onClick = onUndoLastOccurrence)
                }
            }
        }

        shownOccurrences.forEach { event ->
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

        if (shownOccurrences.isEmpty()) ScreenGutter(bottom = 16) {
            CuidarCard(padding = 18.dp, radius = 18.dp, color = p.backgroundSoft) {
                CText("Nenhuma ocorrência nesta categoria.", size = 13.sp, color = p.textSoft)
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

    if (showAddDialog) AddOccurrenceDialog(
        onDismiss = { showAddDialog = false },
        onSave = { title, body, tag ->
            onAddOccurrence(title, body, tag)
            showAddDialog = false
        },
    )
}

@Composable
private fun FilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val p = LocalCuidarPalette.current
    Box(
        Modifier.clip(CircleShape)
            .background(if (selected) p.primary else p.card)
            .then(if (selected) Modifier else Modifier.border(1.dp, p.lineSoft, CircleShape))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 7.dp),
    ) {
        CText(label, size = 11.5.sp, weight = FontWeight.SemiBold, color = if (selected) Color.White else p.textSoft)
    }
}

@Composable
private fun AddOccurrenceDialog(onDismiss: () -> Unit, onSave: (String, String, String) -> Unit) {
    val p = LocalCuidarPalette.current
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("humor") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nova ocorrência demonstrativa") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Este registro fica apenas nesta sessão de demonstração.", color = p.textSoft)
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("O que aconteceu?") }, singleLine = true)
                OutlinedTextField(value = body, onValueChange = { body = it }, label = { Text("Detalhes") }, minLines = 2)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    listOf("humor", "movimento", "sono", "alimentação", "dor", "queda").forEach { option ->
                        FilterChip(option.replaceFirstChar { it.uppercase() }, category == option) { category = option }
                    }
                }
            }
        },
        confirmButton = {
            CuidarButton("Adicionar", height = 40.dp, onClick = if (title.isBlank()) null else ({ onSave(title, body, category) }))
        },
        dismissButton = { CuidarButton("Cancelar", style = CuidarButtonStyle.Ghost, height = 40.dp, onClick = onDismiss) },
    )
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
