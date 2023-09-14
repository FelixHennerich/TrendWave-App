package managers.exceptions

class ExceptionHandler {

    /**
     * Get Error name by the given Exception
     *
     * @param nexception -> Exception thrown by given program look NException.kt
     * @return -> Exception name + code
     */
    fun fetchError(nexception: NException): String{
        return nexception.toString()
    }

    /**
     * Get Error code by the given Exception
     *
     * @param nexception -> Exception thrown by given program look NException.kt
     * @return -> Number of Error
     */
    fun fetchErrorCode(nexception: NException): Int{
        var letterarray = mutableListOf<Char>()
        for(i in 0 until nexception.toString().length){
            letterarray.add(nexception.toString()[i])
        }
        val size = letterarray.size
        return ("" + letterarray[size-3] + letterarray[size-2] + letterarray[size-1]).toInt()
    }

    /**
     * Get the Message of each Error code
     *
     * @param nexception -> Exception thrown by given program look NException.kt
     * @return -> Whole error message
     */
    fun fetchErrorMessage(nexception: NException): String{
        var message: String? = null
        when(nexception){
            NException.SUCCESS001             -> message = "Your action was successful."
            /**
             * 100
             */
            NException.Emailwrong100          -> message = "You've entered an invalid email."
            NException.PasswordToWeak101      -> message = "Your password must be longer than 8 letters."
            NException.UsernameLength102      -> message = "Your username has an invalid length."
            NException.UsernameExists103      -> message = "Your username is already used."
            NException.EmailExists104         -> message = "Your Email is already used."
            NException.UnallowedCharacters105 -> message = "There is a character that isnt allowed"
            NException.BirthdayWrong106       -> message = "Enter your birthday like that: dd.mm.yyyy"
            NException.WrongPassword107       -> message = "You've entered a wrong password"
            /**
             * 200
             */
            /**
             * 300
             */
            /**
             * 400
             */
            NException.HTTPPosting400         -> message = "Error while posting the HTTP request."
            NException.DatabaseCreation401    -> message = "Error while creating user account in database"
            else -> {}
        }

        return message ?: "Error while loading message"
    }

}