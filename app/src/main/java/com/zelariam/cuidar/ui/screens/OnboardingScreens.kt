package com.zelariam.cuidar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zelariam.cuidar.ui.components.CSerifText
import com.zelariam.cuidar.ui.components.CText
import com.zelariam.cuidar.ui.components.CuidarAvatar
import com.zelariam.cuidar.ui.components.CuidarButton
import com.zelariam.cuidar.ui.components.CuidarButtonStyle
import com.zelariam.cuidar.ui.components.CuidarCard
import com.zelariam.cuidar.ui.components.CuidarIcon
import com.zelariam.cuidar.ui.components.CuidarTone
import com.zelariam.cuidar.ui.theme.LocalCuidarPalette

@Composable
fun OnboardingScreen(onStart: () -> Unit) {
    val p = LocalCuidarPalette.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(p.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CuidarLogo()
            CuidarStepIndicator(activeStep = 2)
        }

        Spacer(Modifier.height(36.dp))
        FamilyIllustration()
        Spacer(Modifier.height(28.dp))

        CSerifText(
            text = "Cuidar dela em\nfamília, com calma.",
            size = 32.sp,
            lineHeight = 37.sp,
        )
        Spacer(Modifier.height(12.dp))
        CText(
            text = "Convide irmãos, filhos, netos e cuidadores. Cada um vê o que precisa, sem trocar mensagens infinitas no WhatsApp.",
            size = 15.sp,
            color = p.textSoft,
            lineHeight = 22.sp,
        )
        Spacer(Modifier.height(22.dp))

        FeatureLine(
            icon = "shield",
            title = "Dados protegidos",
            subtitle = "LGPD e criptografia ponta-a-ponta",
            tone = CuidarTone.Sage,
        )
        Spacer(Modifier.height(10.dp))
        FeatureLine(
            icon = "users",
            title = "Convide quem cuida",
            subtitle = "Família, médicos e cuidadores no mesmo lugar",
            tone = CuidarTone.Sage,
        )
        Spacer(Modifier.height(28.dp))

        CuidarButton(
            text = "Começar a cuidar",
            modifier = Modifier.fillMaxWidth(),
            height = 56.dp,
            icon = "arrowRight",
            onClick = onStart,
        )
        Spacer(Modifier.height(10.dp))
        CuidarButton(
            text = "Já tenho um convite",
            modifier = Modifier.fillMaxWidth(),
            style = CuidarButtonStyle.Ghost,
            height = 48.dp,
            onClick = onStart,
        )
    }
}

@Composable
private fun CuidarLogo() {
    val p = LocalCuidarPalette.current
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Brush.linearGradient(listOf(p.primary, p.primaryDeep))),
            contentAlignment = Alignment.Center,
        ) { CSerifText("+", size = 21.sp, color = Color(0xFFFFFEF8)) }
        Row(verticalAlignment = Alignment.CenterVertically) {
            CText("Cuidar", size = 17.sp, weight = FontWeight.Bold)
            CText("+", size = 17.sp, weight = FontWeight.Bold, color = p.primary)
        }
    }
}

@Composable
private fun CuidarStepIndicator(activeStep: Int) {
    val p = LocalCuidarPalette.current
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
        (1..4).forEach { step ->
            Box(
                modifier = Modifier
                    .width(if (step == activeStep) 22.dp else 8.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(if (step <= activeStep) p.primary else p.line),
            )
        }
    }
}

@Composable
private fun FamilyIllustration() {
    val p = LocalCuidarPalette.current
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Brush.radialGradient(listOf(p.goldTint, p.primaryTint, p.sageTint)))
            .border(1.dp, p.lineSoft, RoundedCornerShape(28.dp)),
    ) {
        val centerX = maxWidth / 2
        val centerY = 58.dp
        CuidarAvatar(
            initials = "C",
            size = 84.dp,
            tone = CuidarTone.Primary,
            modifier = Modifier.offset(x = centerX - 42.dp, y = centerY),
        )
        CuidarAvatar("M", size = 40.dp, tone = CuidarTone.Sky, modifier = Modifier.offset(x = 28.dp, y = 28.dp))
        CuidarAvatar("P", size = 38.dp, tone = CuidarTone.Sage, modifier = Modifier.offset(x = maxWidth - 78.dp, y = 22.dp))
        CuidarAvatar("B", size = 36.dp, tone = CuidarTone.Gold, modifier = Modifier.offset(x = 18.dp, y = 118.dp))
        CuidarAvatar("R", size = 42.dp, tone = CuidarTone.Amber, modifier = Modifier.offset(x = maxWidth - 70.dp, y = 130.dp))
    }
}

@Composable
private fun FeatureLine(
    icon: String,
    title: String,
    subtitle: String,
    tone: CuidarTone,
) {
    val p = LocalCuidarPalette.current
    val tint = when (tone) {
        CuidarTone.Sage -> p.sageTint
        else -> p.primaryTint
    }
    val foreground = when (tone) {
        CuidarTone.Sage -> p.sage
        else -> p.primary
    }
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(tint),
            contentAlignment = Alignment.Center,
        ) { CuidarIcon(icon, size = 17.dp, tint = foreground) }
        Column {
            CText(title, size = 13.5.sp, weight = FontWeight.SemiBold)
            CText(subtitle, size = 12.sp, color = p.textSoft)
        }
    }
}

@Composable
fun CadastroScreen(
    onBack: () -> Unit,
    onContinue: () -> Unit,
) {
    val p = LocalCuidarPalette.current
    Box(Modifier.fillMaxSize().background(p.background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 86.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(p.card)
                        .border(1.dp, p.lineSoft, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center,
                ) { CuidarIcon("back", modifier = Modifier.padding(8.dp), size = 20.dp, tint = p.text) }
                CuidarStepIndicator(activeStep = 3)
                CText("3 de 4", size = 13.sp, weight = FontWeight.SemiBold, color = p.textSoft)
            }

            Column(Modifier.padding(horizontal = 20.dp).padding(top = 12.dp, bottom = 24.dp)) {
                CSerifText("Quem vamos cuidar?", size = 28.sp, lineHeight = 33.sp)
                Spacer(Modifier.height(8.dp))
                CText("Esses dados ficam só com a família. Você pode editar depois.", size = 14.5.sp, color = p.textSoft, lineHeight = 21.sp)
                Spacer(Modifier.height(18.dp))

                CuidarCard(padding = 20.dp, radius = 22.dp) {
                    Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .background(Brush.linearGradient(listOf(p.primaryTint, p.goldTint)))
                                .border(1.dp, p.primary.copy(alpha = .35f), RoundedCornerShape(24.dp)),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            CuidarIcon("camera", size = 22.dp, tint = p.primary)
                            CText("foto", size = 9.5.sp, weight = FontWeight.SemiBold, color = p.primary)
                        }
                        Column(Modifier.weight(1f)) {
                            CText("NOME COMPLETO", size = 11.sp, weight = FontWeight.SemiBold, color = p.textMute)
                            CText("Cecília Almeida", size = 17.sp, weight = FontWeight.SemiBold, modifier = Modifier.padding(top = 4.dp))
                            CText("\"Dona Cecília\" · 78 anos", size = 13.sp, color = p.textSoft)
                        }
                    }
                }
                Spacer(Modifier.height(14.dp))

                listOf(
                    Triple("Data de nascimento", "14/03/1947", "calendar"),
                    Triple("Endereço", "R. das Hortênsias, 217 — Pinheiros, SP", "home"),
                    Triple("Telefone", "(11) 99412-7708", "phone"),
                ).forEach { (label, value, icon) ->
                    FormRow(label, value, icon)
                    Spacer(Modifier.height(10.dp))
                }

                CText("CONDIÇÕES DE SAÚDE", size = 12.sp, weight = FontWeight.SemiBold, color = p.textSoft, modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
                CuidarCard(padding = 14.dp, radius = 16.dp) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            com.zelariam.cuidar.ui.components.CuidarBadge("Hipertensão", small = true)
                            com.zelariam.cuidar.ui.components.CuidarBadge("Diabetes tipo 2", small = true)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            com.zelariam.cuidar.ui.components.CuidarBadge("Artrose nos joelhos", small = true)
                            com.zelariam.cuidar.ui.components.CuidarBadge("Alérgica: dipirona", tone = CuidarTone.Coral, small = true)
                        }
                    }
                }

                CText("EM CASO DE EMERGÊNCIA", size = 12.sp, weight = FontWeight.SemiBold, color = p.textSoft, modifier = Modifier.padding(top = 20.dp, bottom = 10.dp))
                CuidarCard(padding = 14.dp, radius = 16.dp) {
                    CText("Hospital Sírio-Libanês", size = 14.sp, weight = FontWeight.SemiBold)
                    CText("Bradesco Saúde · Top Nacional", size = 12.5.sp, color = p.textSoft, modifier = Modifier.padding(top = 2.dp))
                }
            }
        }

        Row(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().background(p.background).padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            CuidarButton("Voltar", modifier = Modifier.width(90.dp), style = CuidarButtonStyle.Ghost, height = 56.dp, onClick = onBack)
            CuidarButton("Continuar", modifier = Modifier.weight(1f), height = 56.dp, icon = "arrowRight", onClick = onContinue)
        }
    }
}

@Composable
private fun FormRow(label: String, value: String, icon: String) {
    val p = LocalCuidarPalette.current
    CuidarCard(padding = 14.dp, radius = 16.dp) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(p.backgroundSoft), contentAlignment = Alignment.Center) {
                CuidarIcon(icon, size = 18.dp, tint = p.primary)
            }
            Column(Modifier.weight(1f)) {
                CText(label, size = 11.sp, weight = FontWeight.SemiBold, color = p.textMute)
                CText(value, size = 14.sp, weight = FontWeight.Medium, modifier = Modifier.padding(top = 1.dp), maxLines = 1)
            }
            CuidarIcon("note", size = 16.dp, tint = p.textMute)
        }
    }
}
