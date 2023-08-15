package account

import kotlin.random.Random

class UUID {

    /**
     * Function for 128Bit Unique ID
     *
     * @return -> 128Bit UUID
     */
    fun generate128BitUUID(): String {
        val randomBits = generateRandomBits(128)
        return formatBitsAsUUID(randomBits)
    }

    /**
     * Generating random bits for UUID
     *
     * @param bits -> length of UUID
     * @return -> Array of randomized Bytes
     */
    fun generateRandomBits(bits: Int): ByteArray {
        val byteArraySize = (bits + 7) / 8
        val random = Random.Default
        val randomBytes = ByteArray(byteArraySize)

        random.nextBytes(randomBytes)
        // Clear the unused bits in the last byte
        randomBytes[0] = (randomBytes[0].toInt() and (0xFF shl (8 - (bits % 8)))).toByte()

        return randomBytes
    }

    /**
     * Formatting the randomized Bytes to UUID
     *
     * @param bits -> Bytearray with randomized Bytes
     * @return -> finished UUID
     */
    fun formatBitsAsUUID(bits: ByteArray): String {
        val sb = StringBuilder()
        for (i in bits.indices) {
            val hex = (bits[i].toInt() and 0xFF).toString(16)
            if (hex.length == 1) sb.append('0') // be sure every byte has 2 chars
            sb.append(hex)
        }
        return sb.toString()
    }

}