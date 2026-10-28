package com.zelariam.cuidar.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.zelariam.cuidar.R

data class CuidarFonts(
    val sans: FontFamily,
    val serif: FontFamily,
)

val LocalCuidarPalette = staticCompositionLocalOf { CuidarDarkPalette }
val LocalCuidarFonts = staticCompositionLocalOf {
    CuidarFonts(FontFamily.Default, FontFamily.Serif)
}

private val CuidarSans = FontFamily(
    Font(R.font.dm_sans_regular, FontWeight.Normal),
    Font(R.font.dm_sans_medium, FontWeight.Medium),
    Font(R.font.dm_sans_semibold, FontWeight.SemiBold),
    Font(R.font.dm_sans_bold, FontWeight.Bold),
)
private val CuidarSerif = FontFamily(Font(R.font.dm_serif_display_regular, FontWeight.Normal))

@Composable
fun CuidarTheme(
    dark: Boolean = true,
    content: @Composable () -> Unit,
) {
    val palette = if (dark) CuidarDarkPalette else CuidarLightPalette
    val colors = if (dark) {
        darkColorScheme(
            primary = palette.primary,
            onPrimary = palette.background,
            secondary = palette.sage,
            background = palette.background,
            surface = palette.card,
            onSurface = palette.text,
            surfaceVariant = palette.cardAlt,
            onSurfaceVariant = palette.textSoft,
            outline = palette.line,
            error = palette.coral,
        )
    } else {
        lightColorScheme(
            primary = palette.primary,
            onPrimary = palette.card,
            secondary = palette.sage,
            background = palette.background,
            surface = palette.card,
            onSurface = palette.text,
            surfaceVariant = palette.cardAlt,
            onSurfaceVariant = palette.textSoft,
            outline = palette.line,
            error = palette.coral,
        )
    }

    CompositionLocalProvider(
        LocalCuidarPalette provides palette,
        LocalCuidarFonts provides CuidarFonts(CuidarSans, CuidarSerif),
    ) {
        MaterialTheme(colorScheme = colors, content = content)
    }
}
