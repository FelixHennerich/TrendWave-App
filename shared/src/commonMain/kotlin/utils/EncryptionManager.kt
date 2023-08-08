package utils

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.sqrt


internal object EncryptionManager {
    /**
     * Function to encrypt the String unsing a MATRIXTRANSPOSITION
      */
    fun encryption(s: String): String {
        val l = s.length
        var b: Int = ceil(sqrt(l.toDouble())).toInt() // untere zahl der wurzel
        var a: Int = floor(sqrt(l.toDouble())).toInt() // obere zahl der wurzel
        var encrypted = ""
        if (b * a < l) {
            if (min(b, a) == b) {
                b += 1
            } else {
                a += 1
            }
        }

        val arr = Array(a) { // 2D Matrix-Char-Array zum speichern der welte a und b in Matrixstruktur
            CharArray(
                b
            )
        }
        var k = 0

        for (j in 0 until a) {
            for (i in 0 until b) {
                if (k < l) {
                    arr[j][i] = s[k]
                }
                k++
            }
        }

        for (j in 0 until b) {
            for (i in 0 until a) {
                encrypted += arr[i][j]
            }
        }
        return encrypted
    }

    /**
     * Function to decrypt the String
     */
    fun decryption(s: String): String {
        val l = s.length
        val b: Int = ceil(sqrt(l.toDouble())).toInt()
        val a: Int = floor(sqrt(l.toDouble())).toInt()
        var decrypted = ""

        val arr = Array(a) {
            CharArray(
                b
            )
        }
        var k = 0
        for (j in 0 until b) {
            for (i in 0 until a) {
                if (k < l) {
                    arr[j][i] = s[k]
                }
                k++
            }
        }
        for (j in 0 until a) {
            for (i in 0 until b) {
                decrypted += arr[i][j]
            }
        }

        val lst: MutableList<Char> = mutableListOf()
        for(x in decrypted.indices){
            lst += decrypted[x]
        }
        lst.removeAt(decrypted.length-1)
        lst.removeAt(decrypted.length-2)

        var decryption = ""
        for(x in lst){
            decryption += x
        }
        return decryption
    }

    fun test(){
        var password: String = "2348u43hffbehf!32§&$/§)"
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