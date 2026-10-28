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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zelariam.cuidar.ui.components.CSerifText
import com.zelariam.cuidar.ui.components.CText
import com.zelariam.cuidar.ui.components.CuidarBadge
import com.zelariam.cuidar.ui.components.CuidarButton
import com.zelariam.cuidar.ui.components.CuidarButtonStyle
import com.zelariam.cuidar.ui.components.CuidarCard
import com.zelariam.cuidar.ui.components.CuidarIcon
import com.zelariam.cuidar.ui.components.CuidarIconButton
import com.zelariam.cuidar.ui.components.CuidarSectionTitle
import com.zelariam.cuidar.ui.components.CuidarTone
import com.zelariam.cuidar.ui.components.ScreenColumn
import com.zelariam.cuidar.ui.components.ScreenGutter
import com.zelariam.cuidar.ui.theme.LocalCuidarPalette

private data class AlertDemo(
    val tone: CuidarTone,
    val time: String,
    val title: String,
    val body: String,
    val action: String,
    val urgency: String,
    val destination: String,
)

private val alertDemos = listOf(
    AlertDemo(CuidarTone.Amber, "agora", "Atenolol atrasado há 18 min", "Tomada das 14:30 ainda não confirmada.", "Confirmar", "atenção", "dose"),
    AlertDemo(CuidarTone.Gold, "11 min", "Pressão um pouco alta", "142/89 às 14:02 — acima da média da semana.", "Ver histórico", "atenção", "relatorio"),
    AlertDemo(CuidarTone.Sage, "1h", "Roseli iniciou o plantão", "Check-in às 13:01. Tudo certo na chegada.", "Abrir", "aviso", "cuidadores"),
    AlertDemo(CuidarTone.Sky, "ontem", "Pedro adicionou uma observação", "“Marquei a fisio do dia 12 com transporte.”", "Ler", "aviso", "familia"),
    AlertDemo(CuidarTone.Coral, "2 dias", "Queda leve registrada", "Roseli registrou tropeço no tapete da sala. Sem ferimentos.", "Ver registro", "crítico", "relatorio"),
)

@Composable
fun AlertsScreen(
    onDose: () -> Unit,
    onReport: () -> Unit,
    onCaregivers: () -> Unit,
    onFamily: () -> Unit,
) {
    val p = LocalCuidarPalette.current
    ScreenColumn {
        ScreenGutter(top = 4, bottom = 14) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    CText("Mais", size = 13.sp, weight = FontWeight.Medium, color = p.textSoft)
                    CText("Alertas", size = 26.sp, weight = FontWeight.Bold)
                }
                CuidarIconButton("settings")
            }
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SummaryCard("Hoje", "5", CuidarTone.Primary, Modifier.weight(1f))
                SummaryCard("Pendentes", "2", CuidarTone.Amber, Modifier.weight(1f))
                SummaryCard("Críticos", "1", CuidarTone.Coral, Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 14.dp)) {
                listOf("Todos", "Não lidos", "Críticos", "Família", "Cuidadores").forEachIndexed { i, label ->
                    CuidarBadge(label, tone = if (i == 0) CuidarTone.Primary else CuidarTone.Neutral, small = true)
                }
            }
        }

        alertDemos.forEach { alert ->
            val destination = when (alert.destination) {
                "dose" -> onDose
                "cuidadores" -> onCaregivers
                "familia" -> onFamily
                else -> onReport
            }
            ScreenGutter(bottom = 10) {
                CuidarCard(padding = 0.dp, radius = 18.dp) {
                    Row(Modifier.fillMaxWidth()) {
                        Box(Modifier.width(5.dp).height(100.dp).background(toneColor(alert.tone)))
                        Column(Modifier.weight(1f).padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(Modifier.size(26.dp).clip(RoundedCornerShape(8.dp)).background(toneTint(alert.tone)), contentAlignment = Alignment.Center) {
                                    CuidarIcon(if (alert.urgency == "crítico") "alert" else "bell", size = 15.dp, tint = toneColor(alert.tone))
                                }
                                CuidarBadge(alert.urgency, tone = alert.tone, small = true)
                                Spacer(Modifier.weight(1f))
                                CText(alert.time, size = 11.sp, color = p.textMute)
                            }
                            CText(alert.title, size = 14.5.sp, weight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                            CText(alert.body, size = 12.5.sp, color = p.textSoft, lineHeight = 18.sp, modifier = Modifier.padding(top = 2.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 12.dp)) {
                                CuidarButton(alert.action, style = if (alert.urgency == "crítico") CuidarButtonStyle.Coral else CuidarButtonStyle.Primary, height = 36.dp, onClick = destination)
                                CuidarButton("Avisar família", style = CuidarButtonStyle.Ghost, height = 36.dp, onClick = onFamily)
                            }
                        }
                    }
                }
            }
        }

        ScreenGutter(bottom = 24) {
            CuidarCard(padding = 14.dp, radius = 18.dp, color = p.backgroundSoft) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(p.card), contentAlignment = Alignment.Center) {
                        CuidarIcon("moon", size = 18.dp, tint = p.sky)
                    }
                    Column(Modifier.weight(1f)) {
                        CText("Horário silencioso · 22h → 7h", size = 13.5.sp, weight = FontWeight.SemiBold)
                        CText("Apenas alertas críticos durante a noite.", size = 11.5.sp, color = p.textSoft)
                    }
                    Box(Modifier.size(width = 40.dp, height = 24.dp).clip(RoundedCornerShape(12.dp)).background(p.sage).padding(2.dp), contentAlignment = Alignment.CenterEnd) {
                        Box(Modifier.size(20.dp).clip(RoundedCornerShape(10.dp)).background(Color.White))
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(label: String, value: String, tone: CuidarTone, modifier: Modifier = Modifier) {
    val p = LocalCuidarPalette.current
    CuidarCard(modifier = modifier, padding = 12.dp, radius = 14.dp) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            CText(value, size = 22.sp, weight = FontWeight.Bold, color = toneColor(tone), lineHeight = 24.sp)
            CText(label, size = 11.sp, weight = FontWeight.SemiBold, color = p.textSoft, modifier = Modifier.padding(top = 4.dp), maxLines = 1)
        }
    }
}

@Composable
fun ReportScreen(onBack: () -> Unit) {
    val p = LocalCuidarPalette.current
    ScreenColumn {
        ScreenGutter(top = 4, bottom = 14) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                CuidarIconButton("back", onClick = onBack)
                CText("Relatório médico", size = 14.sp, weight = FontWeight.Bold)
                CuidarIconButton("download")
            }
        }

        ScreenGutter(bottom = 16) {
            CuidarCard(padding = 0.dp, radius = 20.dp) {
                Column {
                    Column(Modifier.fillMaxWidth().background(p.cardAlt).padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(Modifier.size(38.dp).clip(RoundedCornerShape(10.dp)).background(BrushPair(p.primary, p.primaryDeep)), contentAlignment = Alignment.Center) {
                                CSerifText("+", size = 22.sp, color = Color.White)
                            }
                            Column {
                                CText("CUIDAR+", size = 11.sp, weight = FontWeight.SemiBold, color = p.textMute)
                                CText("Relatório · 22 mai 2026", size = 11.5.sp, color = p.textSoft)
                            }
                        }
                        CSerifText("Cecília Almeida, 78", size = 22.sp, modifier = Modifier.padding(top = 14.dp))
                        CText("Período: 8 mai → 22 mai · 14 dias", size = 12.sp, color = p.textSoft, modifier = Modifier.padding(top = 2.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(top = 8.dp)) {
                            CuidarBadge("Hipertensão", small = true)
                            CuidarBadge("Diabetes tipo 2", small = true)
                        }
                    }

                    Row(Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ReportKpi("Adesão", "97%", CuidarTone.Sage, Modifier.weight(1f))
                        ReportKpi("PA média", "127/82", CuidarTone.Sage, Modifier.weight(1f))
                        ReportKpi("Glicemia", "↑ 119", CuidarTone.Gold, Modifier.weight(1f))
                    }

                    Column(Modifier.padding(start = 16.dp, end = 16.dp, bottom = 18.dp)) {
                        CText("●  Pressão arterial · sistólica", size = 12.5.sp, weight = FontWeight.Bold, color = p.text)
                        Spacer(Modifier.height(6.dp))
                        Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(p.cardAlt).padding(10.dp)) {
                            VitalChart(listOf(124f, 130f, 128f, 122f, 134f, 126f, 131f, 125f, 129f, 128f, 122f, 127f, 130f, 128f), p.coral, 48)
                        }
                        Spacer(Modifier.height(14.dp))
                        CText("●  Medicamentos em uso", size = 12.5.sp, weight = FontWeight.Bold, color = p.text)
                        CText(
                            "Losartana 50mg · 1× manhã · adesão 100%\nMetformina 850mg · 2× ao dia · adesão 96%\nAtenolol 25mg · 1× tarde · adesão 93% (1 dose perdida)\nVit. D³ · 7 gotas · adesão 100%\nDonepezila 5mg · noite · adesão 100%",
                            size = 11.5.sp,
                            color = p.textSoft,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(top = 6.dp),
                        )
                        Spacer(Modifier.height(14.dp))
                        CText("●  Ocorrências relevantes", size = 12.5.sp, weight = FontWeight.Bold, color = p.text)
                        CText(
                            "• 20/05 16:08 — tropeço leve em tapete da sala, sem ferimento.\n• 19/05 22:40 — insônia inicial, recorrente nesta semana.\n• Queixa leve de dor no joelho D durante caminhadas.",
                            size = 11.5.sp,
                            color = p.textSoft,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(top = 6.dp),
                        )
                    }
                }
            }
        }

        ScreenGutter(bottom = 16) {
            CuidarButton("Gerar PDF", modifier = Modifier.fillMaxWidth(), height = 56.dp, icon = "download")
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CuidarButton("WhatsApp", modifier = Modifier.weight(1f), style = CuidarButtonStyle.Soft, icon = "phone")
                CuidarButton("Imprimir", modifier = Modifier.weight(1f), style = CuidarButtonStyle.Soft, icon = "print")
            }
        }

        ScreenGutter(bottom = 24) {
            Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(p.skyTint).padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Top) {
                CuidarIcon("info", size = 16.dp, tint = p.sky)
                CText("Os dados são organizados pelo Cuidar+. Apresente este relatório ao médico responsável para apoiar a tomada de decisão.", size = 11.5.sp, color = p.textSoft, lineHeight = 16.sp)
            }
        }
    }
}

@Composable
private fun ReportKpi(label: String, value: String, tone: CuidarTone, modifier: Modifier = Modifier) {
    val p = LocalCuidarPalette.current
    val (bg, fg) = when (tone) {
        CuidarTone.Gold -> p.goldTint to p.gold
        CuidarTone.Sage -> p.sageTint to p.sage
        else -> p.primaryTint to p.primary
    }
    Column(modifier.clip(RoundedCornerShape(12.dp)).background(bg).padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        CText(value, size = 15.sp, weight = FontWeight.Bold, color = fg)
        CText(label, size = 10.sp, weight = FontWeight.SemiBold, color = p.textSoft, modifier = Modifier.padding(top = 2.dp))
    }
}

@Composable
private fun BrushPair(first: Color, second: Color) = androidx.compose.ui.graphics.Brush.linearGradient(listOf(first, second))

@Composable
private fun toneColor(tone: CuidarTone): Color {
    val p = LocalCuidarPalette.current
    return when (tone) {
        CuidarTone.Primary -> p.primary
        CuidarTone.Sage -> p.sage
        CuidarTone.Sky -> p.sky
        CuidarTone.Gold -> p.gold
        CuidarTone.Amber -> p.amber
        CuidarTone.Coral -> p.coral
        CuidarTone.Neutral -> p.textSoft
    }
}

@Composable
private fun toneTint(tone: CuidarTone): Color {
    val p = LocalCuidarPalette.current
    return when (tone) {
        CuidarTone.Primary -> p.primaryTint
        CuidarTone.Sage -> p.sageTint
        CuidarTone.Sky -> p.skyTint
        CuidarTone.Gold -> p.goldTint
        CuidarTone.Amber -> p.amberTint
        CuidarTone.Coral -> p.coralTint
        CuidarTone.Neutral -> p.lineSoft
    }
}
