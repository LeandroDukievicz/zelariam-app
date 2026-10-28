package com.zelariam.cuidar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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

private data class CaregiverDemo(
    val name: String,
    val role: String,
    val initials: String,
    val tone: CuidarTone,
    val shift: String,
    val rating: String,
    val workingToday: Boolean = false,
)

private val caregivers = listOf(
    CaregiverDemo("Roseli Santos", "Cuidadora diurna", "RS", CuidarTone.Primary, "08:00 – 18:00", "4.9", true),
    CaregiverDemo("Vanderlei Lopes", "Cuidador noturno", "VL", CuidarTone.Sky, "20:00 – 06:00", "4.8"),
    CaregiverDemo("Ângela Ferreira", "Folguista (sáb)", "AF", CuidarTone.Sage, "08:00 – 20:00", "5.0"),
)

@Composable
fun CaregiversScreen(
    onFamily: () -> Unit,
    onMode: () -> Unit,
) {
    val p = LocalCuidarPalette.current
    ScreenColumn {
        ScreenGutter(top = 4, bottom = 14) {
            CuidarPageTitle("Cuidadores", eyebrow = "Mais · equipe", trailing = { CuidarIconButton("plus", filled = true, onClick = onFamily) })
        }

        ScreenGutter(bottom = 16) {
            CuidarCard(padding = 0.dp, radius = 22.dp) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().background(BrushPair(p.primaryTint, p.sageTint)).padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CuidarAvatar("RS", size = 56.dp, tone = CuidarTone.Primary)
                        Column(Modifier.weight(1f)) {
                            CuidarBadge("Em plantão agora", dot = true, small = true)
                            CText("Roseli Santos", size = 17.sp, weight = FontWeight.Bold, modifier = Modifier.padding(top = 6.dp))
                            CText("Cuidadora diurna · check-in 08:02", size = 12.5.sp, color = p.textSoft)
                        }
                    }
                    Column(Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            CText("08:00", size = 11.sp, weight = FontWeight.SemiBold, color = p.textSoft)
                            CText("agora · 14:48", size = 11.sp, weight = FontWeight.SemiBold, color = p.primary)
                            CText("18:00", size = 11.sp, weight = FontWeight.SemiBold, color = p.textSoft)
                        }
                        Spacer(Modifier.height(6.dp))
                        Box(Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)).background(p.backgroundSoft)) {
                            Box(Modifier.fillMaxWidth(.68f).height(8.dp).clip(RoundedCornerShape(4.dp)).background(BrushPair(p.primary, p.primaryDeep)))
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 12.dp)) {
                            CuidarButton("Ligar", modifier = Modifier.weight(1f), style = CuidarButtonStyle.Soft, height = 36.dp, icon = "phone")
                            CuidarButton("Mensagem", modifier = Modifier.weight(1f), style = CuidarButtonStyle.Soft, height = 36.dp, icon = "note")
                            CuidarButton("Passagem", modifier = Modifier.weight(1f), style = CuidarButtonStyle.Ghost, height = 36.dp, icon = "handoff")
                        }
                    }
                }
            }
        }

        ScreenGutter(bottom = 16) {
            CuidarSectionTitle("Equipe de cuidado", action = "Convidar", onAction = onFamily)
            Spacer(Modifier.height(10.dp))
            CuidarCard(padding = 0.dp, radius = 18.dp) {
                Column {
                    caregivers.forEachIndexed { index, person ->
                        CaregiverRow(person, last = index == caregivers.lastIndex)
                    }
                }
            }
        }

        ScreenGutter(bottom = 16) {
            CuidarSectionTitle("Escala da semana")
            Spacer(Modifier.height(10.dp))
            CuidarCard(padding = 14.dp, radius = 18.dp) {
                Row(modifier = Modifier.padding(start = 78.dp, bottom = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    listOf("0h", "6h", "12h", "18h", "24h").forEach { CText(it, size = 9.5.sp, weight = FontWeight.SemiBold, color = p.textMute) }
                }
                listOf("qua 21", "qui 22 · hoje", "sex 23", "sáb 24", "dom 25").forEachIndexed { index, day ->
                    ScheduleRow(day, today = index == 1, index = index)
                }
            }
        }

        ScreenGutter(bottom = 24) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CuidarButton("Iniciar plantão", modifier = Modifier.weight(1f), height = 56.dp, icon = "play", onClick = onMode)
                CuidarButton("Passagem", modifier = Modifier.weight(1f), style = CuidarButtonStyle.Soft, height = 56.dp, icon = "handoff")
            }
        }
    }
}

@Composable
private fun CaregiverRow(person: CaregiverDemo, last: Boolean) {
    val p = LocalCuidarPalette.current
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CuidarAvatar(person.initials, size = 42.dp, tone = person.tone)
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CText(person.name, size = 14.sp, weight = FontWeight.Bold)
                if (person.workingToday) Box(Modifier.size(7.dp).clip(RoundedCornerShape(4.dp)).background(p.sage))
            }
            CText(person.role, size = 12.5.sp, color = p.textSoft, modifier = Modifier.padding(top = 1.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 4.dp)) {
                CText("★ ${person.rating}", size = 11.sp, color = p.textMute)
                CText("·", size = 11.sp, color = p.textMute)
                CText(person.shift, size = 11.sp, color = p.textMute)
            }
        }
        CuidarIcon("chev", size = 18.dp, tint = p.textSoft)
    }
    if (!last) Box(Modifier.fillMaxWidth().padding(horizontal = 14.dp).height(1.dp).background(p.lineSoft))
}

@Composable
private fun ScheduleRow(day: String, today: Boolean, index: Int) {
    val p = LocalCuidarPalette.current
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(vertical = 5.dp)) {
        CText(day, modifier = Modifier.width(78.dp), size = 12.sp, weight = if (today) FontWeight.Bold else FontWeight.Medium, color = if (today) p.primary else p.textSoft)
        BoxWithConstraints(Modifier.weight(1f).height(22.dp).clip(RoundedCornerShape(6.dp)).background(p.backgroundSoft)) {
            val firstTone = when (index) {
                3, 4 -> p.sage
                else -> p.primary
            }
            Box(
                Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = maxWidth * .32f)
                    .width(maxWidth * if (index == 3 || index == 4) .48f else .33f)
                    .height(18.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(firstTone),
                contentAlignment = Alignment.Center,
            ) { CText(if (index == 3 || index == 4) "AF" else "RS", size = 9.5.sp, weight = FontWeight.Bold, color = Color.White) }
            if (index < 3) {
                Box(
                    Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = maxWidth * .75f)
                        .width(maxWidth * .25f)
                        .height(18.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(if (today) p.sky.copy(alpha = .42f) else p.sky),
                    contentAlignment = Alignment.Center,
                ) { CText("VL", size = 9.5.sp, weight = FontWeight.Bold, color = p.card) }
            }
        }
    }
}

@Composable
fun FamilyScreen(onCaregivers: () -> Unit) {
    val p = LocalCuidarPalette.current
    ScreenColumn {
        ScreenGutter(top = 4, bottom = 14) {
            CuidarPageTitle("Família", eyebrow = "Mais · pessoas", trailing = { CuidarIconButton("plus", filled = true, onClick = onCaregivers) })
        }

        ScreenGutter(bottom = 16) {
            CuidarCard(padding = 16.dp, radius = 22.dp, color = p.skyTint, border = p.sky.copy(alpha = .45f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(Modifier.size(52.dp).clip(RoundedCornerShape(16.dp)).background(p.card), contentAlignment = Alignment.Center) {
                        CuidarIcon("qr", size = 26.dp, tint = p.sky)
                    }
                    Column(Modifier.weight(1f)) {
                        CText("Convide alguém", size = 15.sp, weight = FontWeight.Bold)
                        CText("Por link, QR Code ou WhatsApp. Você decide o que cada pessoa vê.", size = 12.5.sp, color = p.textSoft, lineHeight = 17.sp, modifier = Modifier.padding(top = 1.dp))
                    }
                }
            }
        }

        val family = listOf(
            Triple("Marina Almeida", "Filha", "MA") to (CuidarTone.Primary to "Administradora"),
            Triple("Pedro Almeida", "Filho", "PA") to (CuidarTone.Sky to "Pode editar"),
            Triple("Camila Ribeiro", "Neta", "CR") to (CuidarTone.Sage to "Apenas ler"),
            Triple("Beatriz Almeida", "Filha", "BA") to (CuidarTone.Gold to "Pode editar"),
        )

        ScreenGutter(bottom = 16) {
            CuidarSectionTitle("4 pessoas")
            Spacer(Modifier.height(10.dp))
            CuidarCard(padding = 0.dp, radius = 18.dp) {
                Column {
                    family.forEachIndexed { index, (person, permission) ->
                        FamilyRow(person.first, person.second, person.third, permission.first, permission.second, index == family.lastIndex)
                    }
                }
            }
        }

        ScreenGutter(bottom = 16) {
            CuidarSectionTitle("Convites pendentes")
            Spacer(Modifier.height(10.dp))
            CuidarCard(padding = 14.dp, radius = 18.dp) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(Modifier.size(42.dp).clip(RoundedCornerShape(14.dp)).background(p.goldTint), contentAlignment = Alignment.Center) {
                        CText("?", size = 18.sp, weight = FontWeight.Bold, color = p.gold)
                    }
                    Column(Modifier.weight(1f)) {
                        CText("tio.eduardo@…", size = 14.sp, weight = FontWeight.SemiBold)
                        CText("Enviado há 2 dias", size = 12.sp, color = p.textSoft)
                    }
                    CuidarButton("Reenviar", style = CuidarButtonStyle.Ghost, height = 36.dp)
                }
            }
        }

        ScreenGutter(bottom = 16) {
            CuidarSectionTitle("Níveis de permissão")
            Spacer(Modifier.height(10.dp))
            CuidarCard(padding = 0.dp, radius = 18.dp) {
                Column {
                    listOf(
                        Triple("Administradora", "Vê e edita tudo. Pode convidar e remover.", CuidarTone.Primary),
                        Triple("Pode editar", "Pode registrar doses, ocorrências e medições.", CuidarTone.Sky),
                        Triple("Apenas ler", "Vê tudo, mas não modifica nada.", CuidarTone.Sage),
                    ).forEachIndexed { index, item ->
                        PermissionRow(item.first, item.second, item.third, index == 2)
                    }
                }
            }
        }

        ScreenGutter(bottom = 24) {
            CuidarButton("Convidar família", modifier = Modifier.fillMaxWidth(), height = 56.dp, icon = "users", onClick = onCaregivers)
        }
    }
}

@Composable
private fun FamilyRow(name: String, role: String, initials: String, tone: CuidarTone, permission: String, last: Boolean) {
    val p = LocalCuidarPalette.current
    Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        CuidarAvatar(initials, size = 42.dp, tone = tone)
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CText(name, size = 14.sp, weight = FontWeight.Bold)
                if (permission == "Administradora") CuidarBadge("você", small = true)
            }
            CText(role, size = 12.5.sp, color = p.textSoft, modifier = Modifier.padding(top = 1.dp))
            CuidarBadge(permission, tone = tone, small = true)
        }
        CuidarIcon("chev", size = 18.dp, tint = p.textSoft)
    }
    if (!last) Box(Modifier.fillMaxWidth().padding(horizontal = 14.dp).height(1.dp).background(p.lineSoft))
}

@Composable
private fun PermissionRow(title: String, description: String, tone: CuidarTone, last: Boolean) {
    val p = LocalCuidarPalette.current
    val color = when (tone) {
        CuidarTone.Sky -> p.sky
        CuidarTone.Sage -> p.sage
        else -> p.primary
    }
    Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
        Box(Modifier.padding(top = 6.dp).size(8.dp).clip(RoundedCornerShape(4.dp)).background(color))
        Column {
            CText(title, size = 13.5.sp, weight = FontWeight.Bold)
            CText(description, size = 12.sp, color = p.textSoft, lineHeight = 17.sp, modifier = Modifier.padding(top = 1.dp))
        }
    }
    if (!last) Box(Modifier.fillMaxWidth().padding(horizontal = 14.dp).height(1.dp).background(p.lineSoft))
}

@Composable
private fun BrushPair(first: androidx.compose.ui.graphics.Color, second: androidx.compose.ui.graphics.Color) =
    androidx.compose.ui.graphics.Brush.linearGradient(listOf(first, second))
