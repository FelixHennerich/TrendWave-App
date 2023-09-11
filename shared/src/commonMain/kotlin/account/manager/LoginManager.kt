package account.manager

import managers.exceptions.NException

class LoginManager {

    /**
     * Method called when user tries to login within the app
     *
     * @param email -> of account
     * @param password -> not encrypted
     * @return -> Error Handeling
     */
    fun login(email: String, password: String): NException{

        return NException.SUCCESS001
    }

}