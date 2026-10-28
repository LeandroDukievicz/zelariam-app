package com.zelariam.cuidar.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FamilyRestroom
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocalDining
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.MicNone
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.PhoneIphone
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.Sick
import androidx.compose.material.icons.outlined.Snooze
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zelariam.cuidar.ui.theme.CuidarPalette
import com.zelariam.cuidar.ui.theme.LocalCuidarFonts
import com.zelariam.cuidar.ui.theme.LocalCuidarPalette

@Composable
fun CText(
    text: String,
    modifier: Modifier = Modifier,
    size: TextUnit = 14.sp,
    weight: FontWeight = FontWeight.Normal,
    color: Color? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    lineHeight: TextUnit = TextUnit.Unspecified,
) {
    val palette = LocalCuidarPalette.current
    val fonts = LocalCuidarFonts.current
    Text(
        text = text,
        modifier = modifier,
        color = color ?: palette.text,
        fontSize = size,
        fontWeight = weight,
        fontFamily = fonts.sans,
        maxLines = maxLines,
        overflow = overflow,
        lineHeight = lineHeight,
    )
}

@Composable
fun CSerifText(
    text: String,
    modifier: Modifier = Modifier,
    size: TextUnit = 30.sp,
    color: Color? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
) {
    val palette = LocalCuidarPalette.current
    val fonts = LocalCuidarFonts.current
    Text(
        text = text,
        modifier = modifier,
        color = color ?: palette.text,
        fontSize = size,
        fontFamily = fonts.serif,
        lineHeight = lineHeight,
    )
}

enum class CuidarTone { Primary, Sage, Sky, Gold, Amber, Coral, Neutral }

private fun CuidarTone.foreground(p: CuidarPalette): Color = when (this) {
    CuidarTone.Primary -> p.primary
    CuidarTone.Sage -> p.sage
    CuidarTone.Sky -> p.sky
    CuidarTone.Gold -> p.gold
    CuidarTone.Amber -> p.amber
    CuidarTone.Coral -> p.coral
    CuidarTone.Neutral -> p.textSoft
}

private fun CuidarTone.background(p: CuidarPalette): Color = when (this) {
    CuidarTone.Primary -> p.primaryTint
    CuidarTone.Sage -> p.sageTint
    CuidarTone.Sky -> p.skyTint
    CuidarTone.Gold -> p.goldTint
    CuidarTone.Amber -> p.amberTint
    CuidarTone.Coral -> p.coralTint
    CuidarTone.Neutral -> p.lineSoft
}

@Composable
fun CuidarIcon(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp = 22.dp,
    tint: Color? = null,
    contentDescription: String? = null,
) {
    val p = LocalCuidarPalette.current
    Icon(
        imageVector = iconFor(name),
        contentDescription = contentDescription,
        modifier = modifier.size(size),
        tint = tint ?: p.text,
    )
}

private fun iconFor(name: String): ImageVector = when (name) {
    "home" -> Icons.Outlined.Home
    "sun" -> Icons.Outlined.WbSunny
    "heart" -> Icons.Outlined.FavoriteBorder
    "calendar" -> Icons.Outlined.CalendarMonth
    "more" -> Icons.Outlined.MoreHoriz
    "pill" -> Icons.Outlined.Medication
    "plus" -> Icons.Outlined.Add
    "check" -> Icons.Outlined.Check
    "bell" -> Icons.Outlined.NotificationsNone
    "chev" -> Icons.Outlined.ChevronRight
    "back" -> Icons.Outlined.ArrowBack
    "search" -> Icons.Outlined.Search
    "user" -> Icons.Outlined.PersonOutline
    "users", "family" -> Icons.Outlined.FamilyRestroom
    "clock" -> Icons.Outlined.AccessTime
    "note" -> Icons.Outlined.Edit
    "chart" -> Icons.Outlined.ShowChart
    "droplet" -> Icons.Outlined.WaterDrop
    "thermo" -> Icons.Outlined.Thermostat
    "weight" -> Icons.Outlined.MonitorWeight
    "food" -> Icons.Outlined.Restaurant
    "moon" -> Icons.Outlined.Nightlight
    "sparkle" -> Icons.Outlined.Mood
    "download" -> Icons.Outlined.Download
    "phone" -> Icons.Outlined.Call
    "info" -> Icons.Outlined.Info
    "shield" -> Icons.Outlined.Shield
    "settings" -> Icons.Outlined.Settings
    "sos", "alert" -> Icons.Outlined.LocalHospital
    "play" -> Icons.Outlined.Checklist
    "mic" -> Icons.Outlined.MicNone
    "camera" -> Icons.Outlined.CameraAlt
    "handoff" -> Icons.Outlined.Group
    "qr" -> Icons.Outlined.QrCode2
    "print" -> Icons.Outlined.Print
    "arrowRight" -> Icons.Outlined.ArrowForward
    "arrowUp" -> Icons.Outlined.ArrowUpward
    "arrowDown" -> Icons.Outlined.ArrowDownward
    "minus" -> Icons.Outlined.Close
    "x" -> Icons.Outlined.Close
    "walk" -> Icons.Outlined.Sick
    "meal" -> Icons.Outlined.LocalDining
    "snooze" -> Icons.Outlined.Snooze
    "phoneSmall" -> Icons.Outlined.PhoneIphone
    else -> Icons.Outlined.Info
}

@Composable
fun CuidarCard(
    modifier: Modifier = Modifier,
    padding: Dp = 16.dp,
    radius: Dp = 20.dp,
    color: Color? = null,
    border: Color? = null,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val p = LocalCuidarPalette.current
    val shape = RoundedCornerShape(radius)
    Surface(
        modifier = modifier
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        shape = shape,
        color = color ?: p.card,
        border = BorderStroke(1.dp, border ?: p.lineSoft),
        shadowElevation = 1.dp,
    ) {
        Box(Modifier.padding(padding)) { content() }
    }
}

@Composable
fun CuidarAvatar(
    initials: String,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    tone: CuidarTone = CuidarTone.Primary,
    description: String? = null,
) {
    val p = LocalCuidarPalette.current
    val first = tone.foreground(p)
    val second = when (tone) {
        CuidarTone.Primary -> p.primaryDeep
        CuidarTone.Sage -> Color(0xFF5A8068)
        CuidarTone.Sky -> Color(0xFF456E8B)
        CuidarTone.Gold -> Color(0xFFB8853D)
        CuidarTone.Amber -> Color(0xFFB66A26)
        CuidarTone.Coral -> Color(0xFF9B3E2A)
        CuidarTone.Neutral -> p.textMute
    }
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Brush.linearGradient(listOf(first, second)))
            .then(if (description != null) Modifier.semantics { contentDescription = description } else Modifier),
        contentAlignment = Alignment.Center,
    ) {
        CText(initials, size = (size.value * .36f).sp, weight = FontWeight.SemiBold, color = Color.White)
    }
}

@Composable
fun CuidarBadge(
    text: String,
    modifier: Modifier = Modifier,
    tone: CuidarTone = CuidarTone.Primary,
    dot: Boolean = false,
    small: Boolean = false,
) {
    val p = LocalCuidarPalette.current
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(tone.background(p))
            .padding(horizontal = if (small) 8.dp else 10.dp, vertical = if (small) 4.dp else 5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (dot) Box(Modifier.size(6.dp).clip(CircleShape).background(tone.foreground(p)))
        CText(text, size = if (small) 10.5.sp else 11.5.sp, weight = FontWeight.SemiBold, color = tone.foreground(p), maxLines = 1)
    }
}

enum class CuidarButtonStyle { Primary, Soft, Ghost, Outline, Sage, Coral }

@Composable
fun CuidarButton(
    text: String,
    modifier: Modifier = Modifier,
    style: CuidarButtonStyle = CuidarButtonStyle.Primary,
    height: Dp = 48.dp,
    icon: String? = null,
    onClick: (() -> Unit)? = null,
) {
    val p = LocalCuidarPalette.current
    val (fill, foreground, stroke) = when (style) {
        CuidarButtonStyle.Primary -> Triple(p.primary, Color(0xFFFFFEF8), null)
        CuidarButtonStyle.Soft -> Triple(p.primaryTint, p.primaryDeep, null)
        CuidarButtonStyle.Ghost -> Triple(Color.Transparent, p.text, p.line)
        CuidarButtonStyle.Outline -> Triple(Color.Transparent, p.primary, p.primary)
        CuidarButtonStyle.Sage -> Triple(p.sage, Color(0xFFFFFEF8), null)
        CuidarButtonStyle.Coral -> Triple(p.coral, Color(0xFFFFFEF8), null)
    }
    val shape = RoundedCornerShape(if (height >= 60.dp) 18.dp else 14.dp)
    Surface(
        modifier = modifier
            .height(height)
            .defaultMinSize(minWidth = 44.dp)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        shape = shape,
        color = fill,
        border = stroke?.let { BorderStroke(1.5.dp, it) },
        shadowElevation = if (style == CuidarButtonStyle.Primary || style == CuidarButtonStyle.Sage || style == CuidarButtonStyle.Coral) 2.dp else 0.dp,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = if (height >= 60.dp) 24.dp else 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                CuidarIcon(icon, size = if (height >= 60.dp) 22.dp else 19.dp, tint = foreground)
                Spacer(Modifier.width(8.dp))
            }
            CText(text, size = if (height >= 60.dp) 18.sp else 14.sp, weight = FontWeight.SemiBold, color = foreground, maxLines = 1)
        }
    }
}

@Composable
fun CuidarSectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    action: String? = null,
    onAction: (() -> Unit)? = null,
) {
    val p = LocalCuidarPalette.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CText(title.uppercase(), size = 12.sp, weight = FontWeight.SemiBold, color = p.textSoft)
        if (action != null) CText(action, size = 13.sp, weight = FontWeight.SemiBold, color = p.primary, modifier = Modifier.clickable { onAction?.invoke() }.padding(4.dp))
    }
}

@Composable
fun CuidarPageTitle(
    title: String,
    modifier: Modifier = Modifier,
    eyebrow: String? = null,
    trailing: @Composable (() -> Unit)? = null,
) {
    val p = LocalCuidarPalette.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            if (eyebrow != null) CText(eyebrow, size = 13.sp, weight = FontWeight.Medium, color = p.textSoft)
            CText(title, size = 26.sp, weight = FontWeight.Bold, color = p.text, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        trailing?.invoke()
    }
}

@Composable
fun CuidarIconButton(
    icon: String,
    modifier: Modifier = Modifier,
    filled: Boolean = false,
    tint: Color? = null,
    onClick: (() -> Unit)? = null,
) {
    val p = LocalCuidarPalette.current
    val color = tint ?: if (filled) Color(0xFFFFFEF8) else p.text
    Box(
        modifier = modifier
            .size(44.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (filled) p.primary else p.card)
            .then(if (filled) Modifier else Modifier.border(1.dp, p.lineSoft, RoundedCornerShape(14.dp)))
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        contentAlignment = Alignment.Center,
    ) { CuidarIcon(icon, size = 21.dp, tint = color) }
}
