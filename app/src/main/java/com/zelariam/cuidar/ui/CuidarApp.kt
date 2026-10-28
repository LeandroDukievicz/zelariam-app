package com.zelariam.cuidar.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.launch
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
import com.zelariam.cuidar.ui.state.CareDemoState
import com.zelariam.cuidar.ui.state.DemoDoseStatus

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
    val demoState = remember { CareDemoState() }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    fun announceDemoChange(message: String) {
        coroutineScope.launch { snackbarHostState.showSnackbar("$message · alteração só nesta sessão") }
    }
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                    doneTasks = demoState.completedRoutine,
                    doseStatus = demoState.doseStatus,
                    onToggleTask = {
                        demoState.toggleRoutine(it)
                        announceDemoChange("Rotina atualizada")
                    },
                )
            }
            composable(CuidarRoute.Routine) {
                RoutineScreen(
                    onMedication = { navController.navigate(CuidarRoute.Dose) },
                    completedRoutine = demoState.completedRoutine,
                    onToggleRoutine = {
                        demoState.toggleRoutine(it)
                        announceDemoChange("Rotina atualizada")
                    },
                )
            }
            composable(CuidarRoute.Medications) {
                MedicationsScreen(
                    onDose = { navController.navigate(CuidarRoute.Dose) },
                    onIncidents = { navController.navigate(CuidarRoute.Incidents) },
                    onVitals = { navController.navigate(CuidarRoute.Vitals) },
                    doseStatus = demoState.doseStatus,
                )
            }
            composable(CuidarRoute.Dose) {
                DoseScreen(
                    status = demoState.doseStatus,
                    onClose = { navController.popBackStack() },
                    onConfirm = {
                        demoState.recordDose(DemoDoseStatus.Taken)
                        announceDemoChange("Dose confirmada")
                        navController.popBackStack()
                    },
                    onSnooze = {
                        demoState.recordDose(DemoDoseStatus.Snoozed)
                        announceDemoChange("Dose adiada por 30 minutos")
                        navController.popBackStack()
                    },
                    onNotTaken = {
                        demoState.recordDose(DemoDoseStatus.NotTaken)
                        announceDemoChange("Dose marcada como não tomada")
                        navController.popBackStack()
                    },
                    onCorrect = {
                        demoState.recordDose(DemoDoseStatus.Pending)
                        announceDemoChange("Registro de dose reaberto para correção")
                    },
                )
            }
            composable(CuidarRoute.Incidents) {
                IncidentsScreen(
                    onMedications = { navController.navigate(CuidarRoute.Medications) },
                    onVitals = { navController.navigate(CuidarRoute.Vitals) },
                    occurrences = demoState.occurrences,
                    canUndoLastOccurrence = demoState.canUndoLastOccurrence,
                    onAddOccurrence = { title, body, tag ->
                        demoState.addOccurrence(title, body, tag)
                        announceDemoChange("Ocorrência demonstrativa adicionada")
                    },
                    onUndoLastOccurrence = {
                        demoState.undoLastOccurrence()
                        announceDemoChange("Adição desfeita")
                    },
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
                    doseStatus = demoState.doseStatus,
                )
            }
            composable(CuidarRoute.Report) {
                ReportScreen(onBack = { navController.popBackStack() })
            }
            composable(CuidarRoute.CaregiverMode) {
                CaregiverModeScreen(
                    onDose = { navController.navigate(CuidarRoute.Dose) },
                    onFamily = { navController.navigate(CuidarRoute.Family) },
                    doseStatus = demoState.doseStatus,
                )
            }
            composable(CuidarRoute.ElderMode) {
                ElderModeScreen(
                    onDose = { navController.navigate(CuidarRoute.Dose) },
                    onHelp = { navController.navigate(CuidarRoute.Alerts) },
                    doseStatus = demoState.doseStatus,
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
