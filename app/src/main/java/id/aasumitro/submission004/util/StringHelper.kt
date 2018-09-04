package id.aasumitro.submission004.util

object StringHelper {

    fun splitString(string: String?): List<String> = string?.split(";") as List<String>

    fun implodeExplode(string: String?, withSpace: Boolean): String {
        val split = (string as String)
                .split((if (withSpace) "; " else ";")
                        .toRegex())
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