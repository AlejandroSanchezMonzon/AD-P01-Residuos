# AD-P01-Residuos

### 1. Autores

Trabajo realizado por Alejandro Sánchez Monzón y Mireya Sánchez Pinzón.

### 2. Enlace vídeo

> https://drive.google.com/file/d/1R94ihV63yQCSfh3NVbjUytQu_VBLNsOw/view?usp=sharing

### 3. Enlace repositorio

> https://github.com/AlejandroSanchezMonzon/AD-P01-Residuos

### 4. Enunciado

Tenemos unos ficheros con la información deseada que debemos procesar sobre Reciclaje y Limpieza dee Madrid.

Hay tres posibles opciones:

**PARSER**
> Debe tomar los ficheros csv del directorio origen y los trasformalos en JSON y XML en el directorio destino.
> En dicho directorio destino deberán estar las tres versiones: CSV, JSON y XML.

**RESUMEN GLOBAL**
> Debe tomar la información de los contenedores y de la recogida, independientemente de la extensión que tenga.

> Deberá procesarla generando en directorio_destino un resumen.html, con la siguiente información:

- Número de contenedores de cada tipo que hay en cada distrito.
- Media de contenedores de cada tipo que hay en cada distrito.
- Gráfico con el total de contenedores por distrito.
- Media de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito.
- Gráfico de media de toneladas mensuales de recogida de basura por distrito.
- Máximo, mínimo , media y desviación de toneladas anuales de recogidas por cada tipo de basura agrupadas por distrito.
- Suma de todo lo recogido en un año por distrito.
- Por cada distrito obtener para cada tipo de residuo la cantidad recogida.
- Tiempo de generación del mismo en milisegundos.

**RESUMEN DISTRITO**
> Debe tomar la información de los contenedores y de la recogida, independientemente de la extensión que tenga.

> Deberá procesarla generando en directorio_destino un resumen_distrito.html con la siguiente información:

- Número de contenedores de cada tipo que hay en este distrito.
- Total de toneladas recogidas en ese distrito por residuo.
- Gráfico con el total de toneladas por residuo en ese distrito.
- Máximo, mínimo , media y desviación por mes por residuo en dicho distrito. - Gráfica del máximo, mínimo y media por
  meses en dicho distrito.
- Tiempo de generación del mismo en milisegundos.

> Por cada ejecución debemos guardar un fichero bitacora.xml donde tengamos en este XML un listado de las ejecuciones.

