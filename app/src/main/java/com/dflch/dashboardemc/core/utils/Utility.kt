package com.dflch.dashboardemc.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Utility {

    companion object {

        // Función para calcular la diferencia en días entre dos fechas
        @RequiresApi(Build.VERSION_CODES.O)
        fun calculateDaysBetween(startDate: LocalDate, endDate: LocalDate): Long {
            return ChronoUnit.DAYS.between(startDate, endDate)
        }

        // Otras funciones que podrías agregar a esta clase
        @RequiresApi(Build.VERSION_CODES.O)
        fun isLeapYear(year: Int): Boolean {
            return LocalDate.of(year, 1, 1).isLeapYear
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentDate(): LocalDate {
            return LocalDate.now()
        }

        //Calcula dias a fecha de hoy
        @RequiresApi(Build.VERSION_CODES.O)
        fun calculateDaysToTargetDate(targetDate: LocalDateTime): Long {
            val now = LocalDateTime.now()
            return ChronoUnit.DAYS.between(targetDate, now)
        }

        fun extractTimeAmPm(dateString: String): String {
            // Define el formato de entrada según la convención colombiana
            val inputFormat = SimpleDateFormat("MMM/dd/yyyy HH:mm", Locale("es", "CO"))

            // Define el formato de salida (hora en AM-PM)
            val outputFormat = SimpleDateFormat("hh:mm a", Locale("es", "CO"))

            return try {
                // Convierte el String de entrada a un Date
                val date = inputFormat.parse(dateString)

                // Formatea el Date a solo hora en AM-PM
                outputFormat.format(date!!)
            } catch (e: Exception) {
                // En caso de error, retorna un mensaje de error o un valor predeterminado
                "Formato inválido"
            }
        }

        fun extractDateDay(dateString: String): String {
            // Define el formato de entrada según la convención colombiana
            val inputFormat = SimpleDateFormat("MMM/dd/yyyy HH:mm", Locale("es", "CO"))

            // Define el formato de salida (hora en AM-PM)
            val outputFormat = SimpleDateFormat("MMM dd", Locale("es", "CO"))

            return try {
                // Convierte el String de entrada a un Date
                val date = inputFormat.parse(dateString)

                // Formatea el Date a solo hora en AM-PM
                outputFormat.format(date!!)
            } catch (e: Exception) {
                // En caso de error, retorna un mensaje de error o un valor predeterminado
                "Formato inválido"
            }
        }

        fun extractDateTimePartsRegex(text: String): Pair<String?, String?> {
            val pattern = Regex("(.*?/\\d{1,2}).*?(\\d{1,2}:\\d{2})") // Allow 1 or 2 digits
            val matchResult = pattern.find(text)

            return if (matchResult != null) {
                val datePart = matchResult.groupValues[1]
                val timePart = matchResult.groupValues[2]
                Pair(datePart, timePart)
            } else {
                Pair(null, null) //Handle cases with no match
            }
        }

        fun extractDateTimeParts(text: String): Pair<String?, String?> {
            val parts = text.split("/", " ")
            if (parts.size >= 4) {
                val datePart = parts[0] + "/" + parts[1]
                val timePart = convertToAmPm(parts[3]) // Convert to a.m./p.m.
                return Pair(datePart, timePart)
            } else {
                return Pair(null, null)
            }
        }

        fun convertToAmPm(militaryTime: String): String? {
            return try {
                val (hour, minute) = militaryTime.split(":").map { it.toInt() }
                val amPm = if (hour < 12 || hour == 24) "a.m." else "p.m."
                val displayHour =
                    if (hour == 0 || hour == 24) 12 else if (hour > 12) hour - 12 else hour
                String.format("%02d:%02d %s", displayHour, minute, amPm)
            } catch (e: Exception) {
                null // Handle invalid time format
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun calcularDiferenciaTiempo(fechaStr: String): String {
            // Mapeo de nombres de meses en español a números
            val meses = mapOf(
                "ene" to "01", "feb" to "02", "mar" to "03", "abr" to "04",
                "may" to "05", "jun" to "06", "jul" to "07", "ago" to "08",
                "sep" to "09", "oct" to "10", "nov" to "11", "dic" to "12"
            )

            // Extraemos el mes abreviado (primeros 3 caracteres)
            val mesAbreviado = fechaStr.substring(0, 3).lowercase()

            // Reemplazamos el mes en español por su número
            val fechaNumerica = if (mesAbreviado in meses) {
                fechaStr.replaceFirst(mesAbreviado, meses[mesAbreviado]!!)
            } else {
                return "Error: Formato de fecha incorrecto"
            }

            // Formato correcto de la fecha después del reemplazo
            val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")

            // Convertir el string a LocalDateTime
            val fechaIngresada = LocalDateTime.parse(fechaNumerica, formatter)

            // Obtener la fecha y hora actual
            val ahora = LocalDateTime.now()

            // Calcular la diferencia entre ambas fechas
            val diferencia = Duration.between(fechaIngresada, ahora)

            // Extraer días, horas y minutos
            val dias = diferencia.toDays()
            val horas = diferencia.toHours() % 24
            val minutos = diferencia.toMinutes() % 60

            // Construir el resultado legible
            val resultado = StringBuilder()
            if (dias > 0) resultado.append("$dias días ")
            if (horas > 0) resultado.append("$horas horas ")
            if (minutos > 0) resultado.append("$minutos minutos")

            return resultado.toString().trim()
        }
    }
}