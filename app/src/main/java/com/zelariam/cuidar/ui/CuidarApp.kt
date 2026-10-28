package com.zelariam.cuidar.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zelariam.cuidar.ui.components.CuidarBottomBar
import com.zelariam.cuidar.ui.screens.AlertsScreen
import com.zelariam.cuidar.ui.screens.AgendaScreen
import com.zelariam.cuidar.ui.screens.CaregiverModeScreen
import com.zelariam.cuidar.ui.screens.CaregiversScreen
import com.zelariam.cuidar.ui.screens.CadastroScreen
import com.zelariam.cuidar.ui.screens.DashboardScreen
import com.zelariam.cuidar.ui.screens.DoseScreen
import com.zelariam.cuidar.ui.screens.ElderModeScreen
import com.zelariam.cuidar.ui.screens.FamilyScreen
import com.zelariam.cuidar.ui.screens.IncidentsScreen
import com.zelariam.cuidar.ui.screens.MedicationsScreen
import com.zelariam.cuidar.ui.screens.OnboardingScreen
import com.zelariam.cuidar.ui.screens.ReportScreen
import com.zelariam.cuidar.ui.screens.RoutineScreen
import com.zelariam.cuidar.ui.screens.VitalsScreen
import com.zelariam.cuidar.ui.theme.CuidarTheme
import com.zelariam.cuidar.ui.theme.LocalCuidarPalette

object CuidarRoute {
    const val Onboarding = "onboarding"
    const val Cadastro = "cadastro"
    const val Today = "hoje"
    const val Routine = "rotina"
    const val Medications = "medicamentos"
    const val Dose = "dose"
    const val Incidents = "ocorrencias"
    const val Vitals = "vitais"
    const val Agenda = "agenda"
    const val Caregivers = "cuidadores"
    const val Family = "familia"
    const val Alerts = "alertas"
    const val Report = "relatorio"
    const val CaregiverMode = "modo-cuidador"
    const val ElderMode = "modo-idoso"
}

@Composable
fun CuidarApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route ?: CuidarRoute.Onboarding
    val palette = LocalCuidarPalette.current
    val showBottomBar = route !in setOf(
        CuidarRoute.Onboarding,
        CuidarRoute.Cadastro,
        CuidarRoute.CaregiverMode,
        CuidarRoute.ElderMode,
    )

    Scaffold(
        containerColor = palette.background,
        bottomBar = {
            if (showBottomBar) {
                CuidarBottomBar(activeRoute = route) { destination ->
                    navController.navigate(destination) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CuidarRoute.Onboarding,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(CuidarRoute.Onboarding) {
                OnboardingScreen(onStart = { navController.navigate(CuidarRoute.Cadastro) })
            }
            composable(CuidarRoute.Cadastro) {
                CadastroScreen(
                    onBack = { navController.popBackStack() },
                    onContinue = { navController.navigate(CuidarRoute.Today) { popUpTo(CuidarRoute.Onboarding) { inclusive = true } } },
                )
            }
            composable(CuidarRoute.Today) {
                DashboardScreen(
                    onDose = { navController.navigate(CuidarRoute.Dose) },
                    onRoutine = { navController.navigate(CuidarRoute.Routine) },
                    onCaregivers = { navController.navigate(CuidarRoute.Caregivers) },
                    onAgenda = { navController.navigate(CuidarRoute.Agenda) },
                    onAlerts = { navController.navigate(CuidarRoute.Alerts) },
                    onElderMode = { navController.navigate(CuidarRoute.ElderMode) },
                )
            }
            composable(CuidarRoute.Routine) {
                RoutineScreen(onMedication = { navController.navigate(CuidarRoute.Dose) })
            }
            composable(CuidarRoute.Medications) {
                MedicationsScreen(
                    onDose = { navController.navigate(CuidarRoute.Dose) },
                    onIncidents = { navController.navigate(CuidarRoute.Incidents) },
                    onVitals = { navController.navigate(CuidarRoute.Vitals) },
                )
            }
            composable(CuidarRoute.Dose) {
                DoseScreen(onClose = { navController.popBackStack() })
            }
            composable(CuidarRoute.Incidents) {
                IncidentsScreen(
                    onMedications = { navController.navigate(CuidarRoute.Medications) },
                    onVitals = { navController.navigate(CuidarRoute.Vitals) },
                )
            }
            composable(CuidarRoute.Vitals) {
                VitalsScreen(
                    onMedications = { navController.navigate(CuidarRoute.Medications) },
                    onIncidents = { navController.navigate(CuidarRoute.Incidents) },
                )
            }
            composable(CuidarRoute.Agenda) {
                AgendaScreen(onCaregivers = { navController.navigate(CuidarRoute.Caregivers) })
            }
            composable(CuidarRoute.Caregivers) {
                CaregiversScreen(
                    onFamily = { navController.navigate(CuidarRoute.Family) },
                    onMode = { navController.navigate(CuidarRoute.CaregiverMode) },
                )
            }
            composable(CuidarRoute.Family) {
                FamilyScreen(onCaregivers = { navController.navigate(CuidarRoute.Caregivers) })
            }
            composable(CuidarRoute.Alerts) {
                AlertsScreen(
                    onDose = { navController.navigate(CuidarRoute.Dose) },
                    onReport = { navController.navigate(CuidarRoute.Report) },
                    onCaregivers = { navController.navigate(CuidarRoute.Caregivers) },
                    onFamily = { navController.navigate(CuidarRoute.Family) },
                )
            }
            composable(CuidarRoute.Report) {
                ReportScreen(onBack = { navController.popBackStack() })
            }
            composable(CuidarRoute.CaregiverMode) {
                CaregiverModeScreen(
                    onDose = { navController.navigate(CuidarRoute.Dose) },
                    onFamily = { navController.navigate(CuidarRoute.Family) },
                )
            }
            composable(CuidarRoute.ElderMode) {
                ElderModeScreen(
                    onDose = { navController.navigate(CuidarRoute.Dose) },
                    onHelp = { navController.navigate(CuidarRoute.Alerts) },
                )
            }
        }
    }
}

@Composable
fun CuidarRoot() {
    CuidarTheme {
        CuidarApp()
    }
}
