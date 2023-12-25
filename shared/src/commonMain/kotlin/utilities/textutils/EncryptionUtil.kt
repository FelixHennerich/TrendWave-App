package utilities.textutils

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.sqrt


internal object EncryptionUtil {

    /**
     * Function to encrypt the String unsing a MATRIXTRANSPOSITION
     *
     * @param s -> String that is about to get encrypted
     * @return -> encrypted String
      */
    fun encryption(s: String): String {
        val l = s.length
        var b: Int = ceil(sqrt(l.toDouble())).toInt()
        var a: Int = floor(sqrt(l.toDouble())).toInt()
        var encrypted = ""
        if (b * a < l) {
            if (min(b, a) == b) {
                b += 1
            } else {
                a += 1
            }
        }

        // Matrix to generate the Encrypted String
        val arr = Array(a) {
            CharArray(b){
                ' '
            }
        }
        var k = 0

        // Fill the matrix row-wise
        for (j in 0 until a) {
            for (i in 0 until b) {
                if (k < l) {
                    arr[j][i] = s[k]
                }
                k++
            }
        }

        // Loop to generate encrypted String
        for (j in 0 until b) {
            for (i in 0 until a) {
                encrypted += arr[i][j]
            }
        }
        return encrypted
    }

    /**
     * Function to decrypt the String
     *
     * @param s -> Encrypted string that will be decrypted
     * @return -> decrypted String
     */
    fun decryption(s: String): String {
        val l = s.length
        val b: Int = ceil(sqrt(l.toDouble())).toInt()
        val a: Int = floor(sqrt(l.toDouble())).toInt()
        var decrypted = ""

        // Matrix to generate the Encrypted String
        val arr = Array(a) {
            CharArray(b) { ' ' }
        }
        var k = 0

        // Fill the matrix column-wise
        for (j in 0 until b) {
            for (i in 0 until a) {
                if (k < l) {
                    arr[i][j] = s[k]
                    k++
                }
            }
        }

        // Loop to generate decrypted String
        for (i in 0 until a) {
            for (j in 0 until b) {
                if (arr[i][j] != ' ') {
                    decrypted += arr[i][j]
                }
            }
        }

        return decrypted
    }
}