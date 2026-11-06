# Motor de Búsqueda con Árboles AVL

![Java](https://img.shields.io/badge/Java-16%2B-orange?logo=openjdk&logoColor=white)
![Estructura](https://img.shields.io/badge/Estructura-%C3%81rbol%20AVL-green)
![Persistencia](https://img.shields.io/badge/Persistencia-JSON-blue)

Motor de búsqueda implementado en **Java** que utiliza **árboles AVL** como estructura principal para almacenar y gestionar datos de manera eficiente.

El sistema simula una base de datos ligera que permite insertar, buscar, actualizar y eliminar registros, garantizando un rendimiento óptimo gracias al balanceo automático del árbol AVL.

<!-- Cuando tengas capturas, guárdalas en docs/ y descomenta:
![Interfaz gráfica](docs/captura_gui.png)
-->

## Características

- Implementación propia de árboles AVL con balanceo automático.
- Inserción, eliminación y búsqueda en **O(log n)**.
- Búsqueda por rango de IDs.
- Manejo de múltiples tablas.
- Persistencia de datos en archivos JSON.
- Interfaz gráfica (GUI) con visualización del árbol.
- Consola interactiva con comandos.
- Carga masiva de datos mediante scripts y datasets de prueba.

## Tecnologías utilizadas

| Componente            | Tecnología        |
| --------------------- | ----------------- |
| Lenguaje              | Java              |
| Estructura de datos   | Árbol AVL         |
| Persistencia          | Archivos JSON     |
| Interfaz gráfica      | Java Swing        |
| Entorno de desarrollo | Eclipse / VS Code |

## Requisitos

- **Java 16 o superior** (debe coincidir con la versión con la que se compila).
- Windows, Linux o macOS.

## Instalación

1. Clona el repositorio:

   ```bash
   git clone https://github.com/justkousei/Motor_de_Busqueda_AVL.git
   cd Motor_de_Busqueda_AVL
   ```

2. Compila el proyecto:

   ```bash
   javac -d bin -sourcepath src src/avl/Main.java
   ```

3. Ejecútalo:

   ```bash
   # Interfaz gráfica
   java -cp bin avl.Main

   # Consola interactiva
   java -cp bin avl.MainConsola
   ```

También puedes abrir el proyecto en Eclipse (o cualquier IDE para Java) y ejecutar `src/avl/Main.java`, o simplemente usar el ejecutable `MotorDeBusqueda.exe` en Windows.

## Uso

### Comandos de consola

| Comando                           | Descripción                                        |
| --------------------------------- | -------------------------------------------------- |
| `CREATE <tabla>`                  | Crea una nueva tabla                               |
| `INSERT <tabla> <campos...>`      | Inserta un registro                                |
| `FIND <tabla> <id>`               | Busca un registro por ID                           |
| `UPDATE <tabla> <id> <campos...>` | Actualiza un registro                              |
| `DELETE <tabla> <id>`             | Elimina un registro                                |
| `RANGE <tabla> <inicio> <fin>`    | Busca registros dentro de un rango de IDs          |
| `LOAD <archivo>`                  | Ejecuta los comandos de un archivo línea por línea |
| `DROP <tabla>`                    | Elimina una tabla                                  |
| `EXIT`                            | Cierra el programa                                 |

### Ejemplo

```text
CREATE estudiantes
INSERT estudiantes 10 Juan IA U001 5 20
FIND estudiantes 10
UPDATE estudiantes 10 Pedro Robotica U002 6 22
RANGE estudiantes 1 50
DELETE estudiantes 10
DROP estudiantes
EXIT
```

### Interfaz gráfica

Desde la GUI puedes:

- Crear tablas y definir el tamaño de cada una.
- Insertar, buscar (por ID o por rango), actualizar y eliminar registros.
- Visualizar la estructura del árbol AVL.
- Guardar y cargar la información desde un archivo JSON.

## Datasets de prueba

El proyecto incluye datos de prueba para no tener que ingresar registros a mano:

| Dataset | Archivo                     | Uso                                                             |
| ------- | --------------------------- | --------------------------------------------------------------- |
| Pequeño | `datasets/data_pequeno.txt` | Pruebas rápidas y validación de operaciones básicas             |
| Mediano | `datasets/data_mediano.txt` | Mayor cantidad de registros para evaluar el rendimiento del AVL |

Sirven para probar inserción masiva, búsqueda eficiente, eliminación de registros y balanceo del árbol.

El comando `LOAD` lee el archivo línea por línea y ejecuta cada comando como si se hubiera escrito en la consola:

```text
LOAD datasets/data_pequeno.txt
LOAD datasets/data_mediano.txt
```

Ejemplo del contenido de un dataset:

```text
CREATE estudiantes
INSERT estudiantes 1 Juan Electronica 20261
INSERT estudiantes 2 Maria Catastral 20261
INSERT estudiantes 3 Pedro Sistemas 20261
```

Los scripts de carga rápida están en la carpeta `scripts/`.

## Estructura del proyecto

```text
Motor_de_Busqueda_AVL
├── src
│   ├── avl
│   │   ├── AVL.java
│   │   ├── Nodo.java
│   │   ├── Main.java
│   │   ├── MainConsola.java
│   │   └── ConsolaDB.java
│   ├── gestor
│   │   └── DatabaseManager.java
│   ├── modelo
│   │   ├── Registro.java
│   │   └── Tabla.java
│   ├── persistencia
│   │   └── JsonManager.java
│   └── vista
│       ├── VentanaDB.java
│       ├── VentanaCrearTabla.java
│       ├── VentanaInsertarRegistro.java
│       ├── VentanaBuscarRegistro.java
│       ├── VentanaBuscarRango.java
│       ├── VentanaActualizarRegistro.java
│       ├── VentanaEliminarRegistro.java
│       └── panelArbol.java
├── datasets
├── scripts
├── Documentación_Técnica.pdf
├── MotorDeBusqueda.exe
└── README.md
```

## Documentación

La explicación técnica completa del diseño y los algoritmos está en [`Documentación_Técnica.pdf`](Documentación_Técnica.pdf).

## Problemas comunes

**`UnsupportedClassVersionError`**

El proyecto fue compilado con una versión de Java más nueva que la que tienes instalada.

Solución: actualiza Java o vuelve a compilar el proyecto con tu versión.

## Autor

Proyecto final de Ciencias de la Computación, desarrollado por [@justkousei](https://github.com/justkousei).V
