package id.aasumitro.submission002.util

object StringHelper {

    fun splitString(string: String?): List<String> {
        return string!!.split(";")
    }

    fun implodeExplode(string: String?): String {
        val split = string!!
                .split(";".toRegex())
                .dropLastWhile {
                    it.isEmpty()
                }.toTypedArray()
        val sb = StringBuilder()
        for (i in split.indices) {
            sb.append(split[i])
            if (i != split.size - 1) {
                sb.append("\n")
            }
        }
        return sb.toString()
    }

}