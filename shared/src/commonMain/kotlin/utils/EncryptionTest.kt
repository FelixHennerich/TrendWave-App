package utils

class EncryptionTest {

    fun test(){

        var password: String = "MeinSuperstar//($%&/()))(/&%$%%$$§§§$!%&"
        println(password)
        val cryp = EncryptionManager
        var newpw = cryp.encryption(password)
        println(newpw)
        println(cryp.decryption(newpw))

        if(password == cryp.decryption(newpw)){
            println("Password successful encrypted and decrypted")
        }
    }
}