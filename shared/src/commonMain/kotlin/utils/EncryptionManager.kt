package utils

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.sqrt

// https://www.geeksforgeeks.org/encryption-and-decryption-of-string-according-to-given-technique/

internal object EncryptionManager {
    /**
     * Function to encrypt the String
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

        val arr = Array(a) {
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
    fun decryption(s: CharArray): String {
        val l = s.size
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
        return decrypted
    }

}