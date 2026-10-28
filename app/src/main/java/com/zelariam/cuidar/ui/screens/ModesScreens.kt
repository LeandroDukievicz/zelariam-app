package com.zelariam.cuidar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zelariam.cuidar.ui.components.CSerifText
import com.zelariam.cuidar.ui.components.CText
import com.zelariam.cuidar.ui.components.CuidarAvatar
import com.zelariam.cuidar.ui.components.CuidarBadge
import com.zelariam.cuidar.ui.components.CuidarButton
import com.zelariam.cuidar.ui.components.CuidarButtonStyle
import com.zelariam.cuidar.ui.components.CuidarCard
import com.zelariam.cuidar.ui.components.CuidarIcon
import com.zelariam.cuidar.ui.components.CuidarSectionTitle
import com.zelariam.cuidar.ui.components.CuidarTone
import com.zelariam.cuidar.ui.components.ScreenColumn
import com.zelariam.cuidar.ui.components.ScreenGutter
import com.zelariam.cuidar.ui.theme.LocalCuidarPalette
import com.zelariam.cuidar.ui.state.DemoDoseStatus

@Composable
fun CaregiverModeScreen(
    onDose: () -> Unit,
    onFamily: () -> Unit,
    doseStatus: DemoDoseStatus,
) {
    val p = LocalCuidarPalette.current
    ScreenColumn {
        Column(
            modifier = Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(p.primary, p.primaryDeep))).padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 24.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                CuidarBadge("Modo cuidador", tone = CuidarTone.Neutral, dot = true, small = true)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    CuidarIcon("clock", size = 14.dp, tint = Color.White)
                    CText("06h 12min restantes", size = 12.sp, weight = FontWeight.SemiBold, color = Color.White.copy(alpha = .9f))
                }
            }
            Row(modifier = Modifier.padding(top = 14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(Modifier.size(54.dp).clip(RoundedCornerShape(18.dp)).background(Color.White.copy(alpha = .22f)).border(2.dp, Color.White.copy(alpha = .4f), RoundedCornerShape(18.dp)), contentAlignment = Alignment.Center) {
                    CText("RS", size = 20.sp, weight = FontWeight.Bold, color = Color.White)
                }
                Column {
                    CText("Plantão de Roseli", size = 12.5.sp, weight = FontWeight.Medium, color = Color.White.copy(alpha = .85f))
                    CText("Cecília Almeida", size = 20.sp, weight = FontWeight.Bold, color = Color.White)
                    CText("78 anos · quarto da frente", size = 12.sp, color = Color.White.copy(alpha = .9f))
                }
            }
        }

        ScreenGutter(top = 0, bottom = 16) {
            CuidarCard(padding = 0.dp, radius = 22.dp) {
                Column {
                    Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                        Box(Modifier.size(54.dp).clip(RoundedCornerShape(16.dp)).background(p.amberTint), contentAlignment = Alignment.Center) {
                            CuidarIcon("pill", size = 28.dp, tint = p.amber)
                        }
                        Column(Modifier.weight(1f)) {
                            CuidarBadge(
                                text = when (doseStatus) {
                                    DemoDoseStatus.Pending -> "Agora · 14:30"
                                    DemoDoseStatus.Taken -> "Dose registrada · 14:48"
                                    DemoDoseStatus.Snoozed -> "Adiada até 15:18"
                                    DemoDoseStatus.NotTaken -> "Marcada como não tomada"
                                },
                                tone = if (doseStatus == DemoDoseStatus.Taken) CuidarTone.Sage else CuidarTone.Amber,
                                dot = true,
                                small = true,
                            )
                            CText("Atenolol · 25 mg", size = 17.sp, weight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                            CText("1 comprimido · com água", size = 12.5.sp, color = p.textSoft)
                        }
                    }
                    Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                        CuidarButton(
                            if (doseStatus == DemoDoseStatus.Taken) "Ver ou corrigir registro" else "Confirmar — tomou",
                            modifier = Modifier.fillMaxWidth(),
                            height = 64.dp,
                            icon = if (doseStatus == DemoDoseStatus.Taken) "note" else "check",
                            onClick = onDose,
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                            CuidarButton("Adiar", modifier = Modifier.weight(1f), style = CuidarButtonStyle.Ghost, height = 36.dp, onClick = onDose)
                            CuidarButton("Não tomou", modifier = Modifier.weight(1f), style = CuidarButtonStyle.Ghost, height = 36.dp, onClick = onDose)
                        }
                    }
                }
            }
        }

        ScreenGutter(bottom = 16) {
            CuidarSectionTitle("Próximas neste turno")
            Spacer(Modifier.height(10.dp))
            CuidarCard(padding = 0.dp, radius = 18.dp) {
                Column {
                    listOf(
                        Triple("16:00", "Lanche da tarde", CuidarTone.Gold),
                        Triple("17:00", "Ligar para o Pedro", CuidarTone.Sky),
                        Triple("17:45", "Medir pressão antes do turno", CuidarTone.Coral),
                    ).forEachIndexed { index, task ->
                        CaregiverTask(task.first, task.second, task.third, index == 2)
                    }
                }
            }
        }

        ScreenGutter(bottom = 16) {
            CuidarSectionTitle("Registrar rápido")
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    "Pressão" to ("heart" to CuidarTone.Coral),
                    "Refeição" to ("food" to CuidarTone.Gold),
                    "Humor" to ("sparkle" to CuidarTone.Sage),
                    "Outro" to ("note" to CuidarTone.Primary),
                ).forEach { (label, item) ->
                    QuickCareCard(label, item.first, item.second, Modifier.weight(1f), onDose)
                }
            }
        }

        ScreenGutter(bottom = 24) {
            CuidarButton("Avisar família — emergência", modifier = Modifier.fillMaxWidth(), style = CuidarButtonStyle.Coral, height = 56.dp, icon = "sos", onClick = onFamily)
            Spacer(Modifier.height(10.dp))
            CuidarButton("Passagem de plantão", modifier = Modifier.fillMaxWidth(), style = CuidarButtonStyle.Soft, height = 48.dp, icon = "handoff")
        }
    }
}

@Composable
private fun CaregiverTask(time: String, title: String, tone: CuidarTone, last: Boolean) {
    val p = LocalCuidarPalette.current
    val (bg, fg) = toneColors(tone)
    Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(Modifier.size(38.dp).clip(RoundedCornerShape(11.dp)).background(bg), contentAlignment = Alignment.Center) {
            CuidarIcon(if (title.contains("Ligar")) "phone" else if (title.contains("medir")) "heart" else "food", size = 18.dp, tint = fg)
        }
        Column(Modifier.weight(1f)) {
            CText(title, size = 14.sp, weight = FontWeight.SemiBold)
            CText(time, size = 11.5.sp, color = p.textSoft, modifier = Modifier.padding(top = 1.dp))
        }
        Box(Modifier.size(24.dp).clip(RoundedCornerShape(8.dp)).border(1.5.dp, p.line, RoundedCornerShape(8.dp)))
    }
    if (!last) Box(Modifier.fillMaxWidth().padding(horizontal = 14.dp).height(1.dp).background(p.lineSoft))
}

@Composable
private fun QuickCareCard(label: String, icon: String, tone: CuidarTone, modifier: Modifier, onClick: () -> Unit) {
    val p = LocalCuidarPalette.current
    val (bg, fg) = toneColors(tone)
    CuidarCard(modifier = modifier.clickable(onClick = onClick), padding = 10.dp, radius = 14.dp) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Box(Modifier.size(36.dp).clip(RoundedCornerShape(11.dp)).background(bg), contentAlignment = Alignment.Center) {
                CuidarIcon(icon, size = 18.dp, tint = fg)
            }
            CText(label, size = 11.sp, weight = FontWeight.SemiBold, modifier = Modifier.padding(top = 6.dp))
        }
    }
}

@Composable
fun ElderModeScreen(
    onDose: () -> Unit,
    onHelp: () -> Unit,
    doseStatus: DemoDoseStatus,
) {
    val p = LocalCuidarPalette.current
    ScreenColumn {
        Column(Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            CText("Olá, Cecília 👋", size = 16.sp, weight = FontWeight.SemiBold, color = p.textSoft)
            CSerifText("Quinta, 22 de maio", size = 34.sp, lineHeight = 38.sp, modifier = Modifier.padding(top = 4.dp))
            CText("14:48 da tarde", size = 17.sp, color = p.textSoft, modifier = Modifier.padding(top = 6.dp))
        }

        ScreenGutter(bottom = 14) {
            CuidarCard(padding = 0.dp, radius = 26.dp) {
                Column {
                    Row(Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(p.amberTint, p.primaryTint))).padding(22.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Box(Modifier.size(76.dp).clip(RoundedCornerShape(24.dp)).background(Color.White), contentAlignment = Alignment.Center) {
                            CuidarIcon("pill", size = 42.dp, tint = p.amber)
                        }
                        Column(Modifier.weight(1f)) {
                            CText(if (doseStatus == DemoDoseStatus.Pending) "Agora é hora do" else "Status da dose", size = 16.sp, weight = FontWeight.SemiBold, color = p.textSoft)
                            CText("Atenolol", size = 28.sp, weight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                            CText(
                                when (doseStatus) {
                                    DemoDoseStatus.Pending -> "1 comprimido"
                                    DemoDoseStatus.Taken -> "Registrada nesta sessão"
                                    DemoDoseStatus.Snoozed -> "Adiada por 30 min"
                                    DemoDoseStatus.NotTaken -> "Marcada como não tomada"
                                },
                                size = 16.sp,
                                color = p.textSoft,
                                modifier = Modifier.padding(top = 2.dp),
                            )
                        }
                    }
                    Column(Modifier.padding(16.dp)) {
                        CuidarButton(
                            if (doseStatus == DemoDoseStatus.Taken) "Ver ou corrigir registro" else "Já tomei",
                            modifier = Modifier.fillMaxWidth(), height = 72.dp,
                            icon = if (doseStatus == DemoDoseStatus.Taken) "note" else "check",
                            onClick = onDose,
                        )
                        if (doseStatus != DemoDoseStatus.Taken) CuidarButton("Ainda não tomei", modifier = Modifier.fillMaxWidth().padding(top = 10.dp), style = CuidarButtonStyle.Ghost, height = 52.dp, onClick = onDose)
                    }
                }
            }
        }

        ScreenGutter(bottom = 14) {
            CuidarCard(padding = 20.dp, radius = 24.dp) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    Box(Modifier.size(64.dp).clip(RoundedCornerShape(20.dp)).background(p.sageTint), contentAlignment = Alignment.Center) {
                        CuidarIcon("calendar", size = 32.dp, tint = p.sage)
                    }
                    Column(Modifier.weight(1f)) {
                        CText("Próxima consulta", size = 15.sp, weight = FontWeight.SemiBold, color = p.textSoft)
                        CText("Sexta · 14:30", size = 22.sp, weight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                        CText("Dra. Helena", size = 16.sp, color = p.textSoft, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        }

        ScreenGutter(bottom = 14) {
            CText("Ligar para", size = 17.sp, weight = FontWeight.Bold, modifier = Modifier.padding(start = 4.dp, bottom = 10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf("Marina" to ("M" to CuidarTone.Primary), "Pedro" to ("P" to CuidarTone.Sky), "Roseli" to ("R" to CuidarTone.Sage)).forEach { (name, identity) ->
                    ElderContact(name, identity.first, identity.second, Modifier.weight(1f))
                }
            }
        }

        ScreenGutter(top = 12, bottom = 28) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(Brush.linearGradient(listOf(p.coral, Color(0xFF9B3E2A))) )
                    .clickable(onClick = onHelp)
                    .padding(20.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.align(Alignment.Center)) {
                    Box(Modifier.size(56.dp).clip(CircleShape).background(Color.White.copy(alpha = .2f)), contentAlignment = Alignment.Center) {
                        CuidarIcon("sos", size = 36.dp, tint = Color.White)
                    }
                    Column {
                        CText("Preciso de ajuda", size = 24.sp, weight = FontWeight.Bold, color = Color.White)
                        CText("avisa a família e a Roseli", size = 13.5.sp, color = Color.White.copy(alpha = .9f), modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun ElderContact(name: String, initials: String, tone: CuidarTone, modifier: Modifier) {
    val p = LocalCuidarPalette.current
    CuidarCard(modifier = modifier, padding = 14.dp, radius = 20.dp) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            CuidarAvatar(initials, size = 56.dp, tone = tone)
            CText(name, size = 16.sp, weight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
            Row(Modifier.fillMaxWidth().padding(top = 8.dp).height(38.dp).clip(RoundedCornerShape(12.dp)).background(p.sageTint), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                CuidarIcon("phone", size = 14.dp, tint = p.sage)
                CText("Ligar", size = 13.sp, weight = FontWeight.Bold, color = p.sage, modifier = Modifier.padding(start = 6.dp))
            }
        }
    }
}

@Composable
private fun toneColors(tone: CuidarTone): Pair<androidx.compose.ui.graphics.Color, androidx.compose.ui.graphics.Color> {
    val p = LocalCuidarPalette.current
    return when (tone) {
        CuidarTone.Primary -> p.primaryTint to p.primary
        CuidarTone.Sage -> p.sageTint to p.sage
        CuidarTone.Sky -> p.skyTint to p.sky
        CuidarTone.Gold -> p.goldTint to p.gold
        CuidarTone.Amber -> p.amberTint to p.amber
        CuidarTone.Coral -> p.coralTint to p.coral
        CuidarTone.Neutral -> p.lineSoft to p.textSoft
    }
}
