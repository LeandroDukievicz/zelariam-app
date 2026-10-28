package com.zelariam.cuidar.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.zelariam.cuidar.ui.components.CuidarTone

enum class DemoDoseStatus { Pending, Taken, Snoozed, NotTaken }

data class DemoOccurrence(
    val id: Long,
    val whenText: String,
    val who: String,
    val initials: String,
    val tag: String,
    val tone: CuidarTone,
    val title: String,
    val body: String,
)

/** Session-only demo state. It intentionally has no disk or network persistence. */
class CareDemoState {
    var doseStatus by mutableStateOf(DemoDoseStatus.Pending)
        private set

    var completedRoutine by mutableStateOf(
        setOf("wake", "breakfast", "stretch", "walk", "lunch"),
    )
        private set

    var occurrences by mutableStateOf(
        listOf(
            DemoOccurrence(1, "hoje · 13:42", "Roseli", "RS", "humor", CuidarTone.Sage, "Almoçou bem e está animada", "Aceitou todo o prato e pediu para ligar para a Marina depois."),
            DemoOccurrence(2, "hoje · 11:15", "Roseli", "RS", "movimento", CuidarTone.Sky, "Caminhou 12 min no quarteirão", "Reclamou um pouco do joelho direito perto do fim, mas voltou bem."),
            DemoOccurrence(3, "ontem · 22:40", "Vanderlei", "VL", "sono", CuidarTone.Gold, "Demorou a pegar no sono", "Ficou inquieta até umas 23h30. Tomou um chá de camomila."),
            DemoOccurrence(4, "ontem · 16:08", "Roseli", "RS", "queda leve", CuidarTone.Amber, "Tropeçou no tapete da sala", "Sem batida na cabeça. Examinei o tornozelo, sem inchaço. Recolhi o tapete."),
            DemoOccurrence(5, "2 dias · 09:20", "Marina", "MA", "consulta", CuidarTone.Primary, "Cardio — Dr. Henrique", "Trocou Captopril por Atenolol 25mg. Próximo retorno em 60 dias."),
        ),
    )
        private set

    var canUndoLastOccurrence by mutableStateOf(false)
        private set

    private var lastOccurrenceId: Long? = null
    private var nextOccurrenceId = 6L

    fun recordDose(status: DemoDoseStatus) {
        doseStatus = status
        completedRoutine = if (status == DemoDoseStatus.Taken) {
            completedRoutine + "atenolol"
        } else {
            completedRoutine - "atenolol"
        }
    }

    fun toggleRoutine(id: String) {
        val willComplete = id !in completedRoutine
        completedRoutine = if (willComplete) completedRoutine + id else completedRoutine - id
        if (id == "atenolol") doseStatus = if (willComplete) DemoDoseStatus.Taken else DemoDoseStatus.Pending
    }

    fun addOccurrence(title: String, body: String, tag: String) {
        val (who, initials, tone) = Triple("Marina", "MA", toneFor(tag))
        val item = DemoOccurrence(
            id = nextOccurrenceId++,
            whenText = "agora · 14:48",
            who = who,
            initials = initials,
            tag = tag.lowercase(),
            tone = tone,
            title = title.trim(),
            body = body.trim().ifBlank { "Registro demonstrativo criado nesta sessão." },
        )
        occurrences = listOf(item) + occurrences
        lastOccurrenceId = item.id
        canUndoLastOccurrence = true
    }

    fun undoLastOccurrence() {
        val id = lastOccurrenceId ?: return
        occurrences = occurrences.filterNot { it.id == id }
        lastOccurrenceId = null
        canUndoLastOccurrence = false
    }

    private fun toneFor(tag: String) = when (tag.lowercase()) {
        "sono", "alimentação" -> CuidarTone.Gold
        "movimento" -> CuidarTone.Sky
        "queda", "dor" -> CuidarTone.Amber
        else -> CuidarTone.Sage
    }
}
