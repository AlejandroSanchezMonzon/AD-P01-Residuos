package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.*
import java.time.Month

internal class FormattersTest {

    @Test
    @DisplayName("Formatear fecha a la zona horaria de España.")
    fun dateFormatterTest() {
        val fechaParseadaTrue = "miércoles, 12 de octubre de 2022"
        val fechaParseadaFalse1 = "lunes, 10 de diciembre de 2022"
        val fechaParseadaFalse2 = "lunes, 10 de diciembre de 2022, a las 12 am"
        //fecha = 2022-10-12T12:00
        val fecha = LocalDateTime.of(2022, Month.OCTOBER, 12, 12, 0)

        Assertions.assertAll (
            { assertEquals(fechaParseadaTrue, dateFormatter(fecha)) },
            { assertNotEquals(fechaParseadaFalse1, dateFormatter(fecha)) },
            { assertNotEquals(fechaParseadaFalse2, dateFormatter(fecha)) },
        )
    }
}



