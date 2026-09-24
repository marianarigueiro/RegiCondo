package com.example.regicondo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = AzulInstitucional,
    onPrimary = Superficie,
    primaryContainer = AzulProfundo,
    secondary = DouradoSelo,
    onSecondary = Superficie,
    background = Papel,
    onBackground = TextoPrincipal,
    surface = Superficie,
    onSurface = TextoPrincipal,
    surfaceVariant = Papel,
    onSurfaceVariant = TextoSecundario,
    error = StatusRevogada
)

@Composable
fun RegiCondoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}