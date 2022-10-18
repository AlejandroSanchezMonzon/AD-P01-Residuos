package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.io.File

internal class ParsersTest {
    val string = ""
    val double = "2.1".toDouble()


    @Test
    @DisplayName("Formateando campos vacíos.")
    fun parseNullTest() {
        Assertions.assertAll (
            { assertEquals("null", parseNull(string), "El campo está vacío") },
            { assertEquals("", parseNull(string + "prueba"), "El campo no está vacío") },
            { assertNotEquals("", parseNull(string)) },
            { assertNotEquals("null", parseNull(string+ "prueba")) }
        )

    }

    @Test
    @DisplayName("Formateando decimales.")
    fun parseDoubleTest() {
        Assertions.assertAll (
            { assertEquals(double, parseDouble("2,1")) },
            { assertEquals(double, parseDouble("2.1")) },
            { assertNotEquals("2,1", parseDouble("2,1").toString()) },
            { assertNotEquals("2,1", parseDouble("2.1").toString()) }
        )

    }

    @Test
    @DisplayName("Formatenado distritos, quitando tilde y  poniendolos en mayúsculas.")
    fun parseDistritoTest() {
        val distritoTrue = "CHAMBERI"
        val distritoFalse1 = "Chamberi"
        val distritoFalse2 = "Chamberí"
        val distritoFalse3 = "CHAMBERÍ"

        Assertions.assertAll (
            { assertEquals(distritoTrue, parseDistrito("Chamberí"))},
            { assertNotEquals(distritoFalse1, parseDistrito("Chamberí"))},
            { assertNotEquals(distritoFalse2, parseDistrito("Chamberí"))},
            { assertNotEquals(distritoFalse3, parseDistrito("Chamberí"))}
        )
    }
}