package utils

import models.TipoContenedor
import models.TipoResiduo
import models.TipoVia
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

//TODO: explicar por qúe las hacemos internas las clases de los tests
internal class ValidationsTest {
    val path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources"

    @Test
    @DisplayName("Comprobando extensiones (.csv, .json, .xml).")
    fun validateFileExtensionTest() {
        Assertions.assertAll (
            { assertTrue(validateFileExtension(path + File.separator + "contenedores_varios.csv")) },
            { assertTrue(validateFileExtension(path + File.separator + "contenedores_varios.json")) },
            { assertTrue(validateFileExtension(path + File.separator + "contenedores_varios.xml")) },
            { assertFalse(validateFileExtension(path)) },
            { assertFalse(validateFileExtension(path+ File.separator + "contenedores_varios.txt")) },
        )
    }

    @Test
    @DisplayName("Comprobar que existe ruta.")
    fun validatePathTest() {
        Assertions.assertAll (
            { assertTrue(validatePath(path + File.separator + "contenedores_varios.csv")) },
            { assertTrue(validatePath(path)) },
            { assertFalse(validatePath(path + File.separator + "carpetaInexistente")) }
        )
    }


    @Test
    @DisplayName("Comprobar que el numero de columnas sea 7")
    fun longitudCabeceraResiduosTest() {
        val cabeceraTrue = "anio;mes;lote;residuo;distrito;nombreDistrito;toneladas"
        val cabeceraFalse1 = "anio;mes;lote;residuo;distrito;nombreDistrito;toneladas;columnaExtra"
        val cabeceraFalse2 = "anio;mes;lote;residuo;distrito;nombreDistrito"
        Assertions.assertAll (
            { Assertions.assertTrue(longitudCabeceraResiduos(cabeceraTrue)) },
            { Assertions.assertFalse(longitudCabeceraResiduos(cabeceraFalse1)) },
            { Assertions.assertFalse(longitudCabeceraResiduos(cabeceraFalse2)) }
        )

    }

    @Test
    @DisplayName("Comprobar que el numero de columnas sea 16")
    fun longitudCabeceraContenedoresTest() {
        val cabeceraTrue = "codigo;contenedor;modelo;descripcion;cantidad;lote;distrito;barrio;via;nombre;numero;coordenadaX;coordenadaY;longitud;latitud;direccion"
        val cabeceraFalse1 = "codigo;contenedor;modelo;descripcion;cantidad;lote;distrito;barrio;via;nombre;numero;coordenadaX;coordenadaY;longitud;latitud;direccion;columnaExtra"
        val cabeceraFalse2 = "codigo;contenedor;modelo;descripcion;cantidad;lote;distrito;barrio;via;nombre;numero;coordenadaX;coordenadaY;longitud;latitud"

        Assertions.assertAll (
            { Assertions.assertTrue(longitudCabeceraContenedores(cabeceraTrue)) },
            { Assertions.assertFalse(longitudCabeceraContenedores(cabeceraFalse1)) },
            { Assertions.assertFalse(longitudCabeceraContenedores(cabeceraFalse2)) }
        )

    }

    @Test
    @DisplayName("Comprobar columnas separadas por punto y coma")
    fun separacionColumnasTest() {
        val cabeceraTrue = "anio;mes;lote;residuo;distrito;nombreDistrito;toneladas"
        val cabeceraFalse = "anio,mes,lote,residuo,distrito,nombreDistrito,toneladas"
        val nombreColumnas = listOf("anio", "mes", "lote", "residuo", "distrito", "nombreDistrito", "toneladas")

        Assertions.assertAll (
            { Assertions.assertEquals(nombreColumnas[0], separacionColumnas(cabeceraTrue)[0])},
            {Assertions.assertEquals(nombreColumnas[1], separacionColumnas(cabeceraTrue)[1])},
            {Assertions.assertEquals(nombreColumnas[2], separacionColumnas(cabeceraTrue)[2])},
            {Assertions.assertEquals(nombreColumnas[3], separacionColumnas(cabeceraTrue)[3])},
            {Assertions.assertEquals(nombreColumnas[4], separacionColumnas(cabeceraTrue)[4])},
            {Assertions.assertEquals(nombreColumnas[5], separacionColumnas(cabeceraTrue)[5])},
            {Assertions.assertNotEquals(nombreColumnas[0], separacionColumnas(cabeceraFalse)[0])}
        )


    }
}