package utils

class EncryptionTest {

    fun test(){
        var password: String = "2348u43hffbehf!32§&$/§)"
        println(password)
        val cryp = EncryptionManager
        var newpw = cryp.encryption(password)
        println(newpw)
        println(cryp.encryption(newpw))


    }
}