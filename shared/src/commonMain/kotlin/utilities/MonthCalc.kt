package utilities

internal object MonthCalc {

    /**
     * Month to Number (August to 8)
     *
     * @param month -> Written month
     * @return Number month
     */
    fun numberOfMonth(month: String): String {
        var num: String = ""
        when(month){
            "JAN", "Januar", "januar", "jan", "JANUARY"         -> num = "01"
            "FEB", "Februar", "februar", "feb", "FEBRUARY"      -> num = "02"
            "MÄR", "März", "märu", "mär", "MARCH"               -> num = "03"
            "APR", "April", "april", "apr", "APRIL"             -> num = "04"
            "MAI", "Mai", "mai", "MAY"                          -> num = "05"
            "JUN", "Juni", "juni", "jun", "JUNE"                -> num = "06"
            "JUL", "Juli", "juli", "jul", "JULY"                -> num = "07"
            "AUG", "August", "august", "aug", "AUGUST"          -> num = "08"
            "SEP", "September", "september", "sep", "SEPTEMBER" -> num = "09"
            "OKT", "Oktober", "oktober", "okt", "OCTOBER"       -> num = "10"
            "NOV", "November", "november", "nov", "NOVEMBER"    -> num = "11"
            "DEZ", "Dezember", "dezember", "dez", "DECEMBER"    -> num = "12"

        }

        return num
    }

}