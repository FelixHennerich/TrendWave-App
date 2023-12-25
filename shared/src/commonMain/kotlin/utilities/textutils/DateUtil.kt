package utilities.textutils

import io.ktor.util.date.GMTDate
import utilities.MonthCalc

class DateUtil {

    /**
     * Takes the current date of the day
     *
     * @return Date of Day
     * @sample (dd.mm.yyyy)
     */
    fun getCurrentDate(): String {
        val day = GMTDate().dayOfMonth
        val month = MonthCalc.numberOfMonth(GMTDate().month.toString())
        val year = GMTDate().year
        val date = "$day.$month.$year"
        return date
    }

}