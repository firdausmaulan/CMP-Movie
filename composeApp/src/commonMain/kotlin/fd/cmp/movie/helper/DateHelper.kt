package fd.cmp.movie.helper

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

object DateHelper {
    fun formatDate(date: String?): LocalDate {
        if (date.isNullOrEmpty()) return Clock.System.todayIn(TimeZone.currentSystemDefault())
        val dateParts = date.split("-")
        return LocalDate(dateParts[2].toInt(), dateParts[1].toInt(), dateParts[0].toInt())
    }

    fun formatDate(date: LocalDate): String {
        return "${date.dayOfMonth}-${date.monthNumber}-${date.year}"
    }
}