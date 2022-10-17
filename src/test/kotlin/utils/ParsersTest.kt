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
}