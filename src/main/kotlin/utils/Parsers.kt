package utils

fun parseNull(s: String): String {
    var aux = ""
    if (s.isEmpty()) {
        aux = "null"
    }
    return aux
}

fun parseDouble(s: String): Double {
    return s.replace(",", ".").toDouble()
}