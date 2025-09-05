package com.example.lab07

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Data classes
data class Notification(
    val id: Int,
    val type: NotificationType,
    val title: String,
    val description: String,
    val time: String
)

enum class NotificationType {
    INFORMATIVE,
    TRAINING
}

// Custom Theme with Dark Mode support
@Composable
fun Lab07Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF26A69A), // Teal
            onPrimary = Color.White,
            primaryContainer = Color(0xFF004D40),
            onPrimaryContainer = Color.White,
            error = Color(0xFFFF7043), // Orange
            errorContainer = Color(0xFF3E2723),
            onError = Color.White,
            onErrorContainer = Color.White,
            background = Color(0xFF121212),
            onBackground = Color.White,
            surface = Color(0xFF1E1E1E),
            onSurface = Color.White,
            onSurfaceVariant = Color(0xFFB0B0B0)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF00695C), // Teal Dark
            onPrimary = Color.White,
            primaryContainer = Color(0xFFB2DFDB),
            onPrimaryContainer = Color(0xFF00695C),
            error = Color(0xFFFF5722), // Orange
            errorContainer = Color(0xFFFFF3E0),
            onError = Color.White,
            onErrorContainer = Color(0xFFFF5722),
            background = Color.White,
            onBackground = Color.Black,
            surface = Color.White,
            onSurface = Color.Black,
            onSurfaceVariant = Color(0xFF666666)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab07Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotificationCenterApp()
                }
            }
        }
    }
}

// Data source function - simplified
fun generateFakeNotifications(): List<Notification> {
    return listOf(
        Notification(
            id = 1,
            type = NotificationType.INFORMATIVE,
            title = "Nueva versión disponible",
            description = "La aplicación ha sido actualizada a v1.1.0. Ve a la PlayStore y actualízala!",
            time = "19 ago • 2:30 p. m."
        ),
        Notification(
            id = 2,
            type = NotificationType.TRAINING,
            title = "Nueva capacitación",
            description = "El día Martes 21 de Agosto tendremos una nueva capacitación en el INTECAP no faltes!",
            time = "18 ago • 3:00 p. m."
        ),
        Notification(
            id = 3,
            type = NotificationType.TRAINING,
            title = "¡Última capacitación de ICTA!",
            description = "No te olvides de asistir a esta capacitación mañana, a las 6am, en el Intecap",
            time = "5 ago • 11:30 a. m."
        ),
        Notification(
            id = 4,
            type = NotificationType.INFORMATIVE,
            title = "Nueva versión disponible",
            description = "La aplicación ha sido actualizada a v1.0.2. Ve a la PlayStore y actualízala!",
            time = "15 jul • 2:30 p. m."
        ),
        Notification(
            id = 5,
            type = NotificationType.INFORMATIVE,
            title = "Actualización de sistema",
            description = "Sistema actualizado correctamente",
            time = "1 día ago • 10:00 a. m."
        ),
        Notification(
            id = 6,
            type = NotificationType.TRAINING,
            title = "Curso de Android",
            description = "Nuevo curso disponible en la plataforma",
            time = "2 días ago • 2:15 p. m."
        ),
        Notification(
            id = 7,
            type = NotificationType.INFORMATIVE,
            title = "Mantenimiento",
            description = "Mantenimiento programado para el fin de semana",
            time = "3 días ago • 5:30 p. m."
        ),
        Notification(
            id = 8,
            type = NotificationType.TRAINING,
            title = "Taller de desarrollo",
            description = "Taller especial para desarrolladores móviles",
            time = "1 sem ago • 9:00 a. m."
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationCenterApp() {
    var selectedFilter by remember { mutableStateOf<NotificationType?>(null) }
    val notifications = remember { generateFakeNotifications() }

    val filteredNotifications = remember(selectedFilter) {
        if (selectedFilter == null) {
            notifications
        } else {
            notifications.filter { it.type == selectedFilter }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Notificaciones",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            navigationIcon = {
                IconButton(onClick = { /* Handle back navigation */ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )

        // Content
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Filters Section
            item {
                FilterSection(
                    selectedFilter = selectedFilter,
                    onFilterChanged = { newFilter ->
                        selectedFilter = if (selectedFilter == newFilter) null else newFilter
                    }
                )
            }

            // Notifications Header
            item {
                Text(
                    text = "Tipos de notificaciones",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Notifications List
            items(
                items = filteredNotifications,
                key = { notification -> notification.id }
            ) { notification ->
                NotificationItem(notification = notification)
            }

            // Results count
            item {
                Text(
                    text = "Mostrando ${filteredNotifications.size} de ${notifications.size} notificaciones",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun FilterSection(
    selectedFilter: NotificationType?,
    onFilterChanged: (NotificationType) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "Filtros",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Debe contar con filtros por tipo de notificación. Solo podrá filtrar por un tipo de notificación a la vez",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Tipos de notificaciones",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChip(
                    selected = selectedFilter == NotificationType.INFORMATIVE,
                    onClick = { onFilterChanged(NotificationType.INFORMATIVE) },
                    label = { Text("Informativas") },
                    leadingIcon = if (selectedFilter == NotificationType.INFORMATIVE) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else null
                )

                FilterChip(
                    selected = selectedFilter == NotificationType.TRAINING,
                    onClick = { onFilterChanged(NotificationType.TRAINING) },
                    label = { Text("Capacitaciones") },
                    leadingIcon = if (selectedFilter == NotificationType.TRAINING) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else null
                )
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    val (backgroundColor, iconColor, icon) = when (notification.type) {
        NotificationType.INFORMATIVE -> Triple(
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.error,
            Icons.Default.Warning
        )
        NotificationType.TRAINING -> Triple(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.primary,
            Icons.Default.Info
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = notification.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notification.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun NotificationCenterPreviewLight() {
    Lab07Theme(darkTheme = false) {
        NotificationCenterApp()
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
fun NotificationCenterPreviewDark() {
    Lab07Theme(darkTheme = true) {
        NotificationCenterApp()
    }
}