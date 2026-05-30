## Motor de Búsqueda con Árboles AVL
## Descripción

Este proyecto consiste en un motor de búsqueda implementado en Java que utiliza árboles AVL como estructura principal para almacenar y gestionar datos de manera eficiente.

El sistema simula una base de datos ligera, permitiendo realizar operaciones como inserción, búsqueda, actualización y eliminación de registros, garantizando un rendimiento óptimo gracias al balance automático del árbol AVL.

## Características
Implementación de árboles AVL
Inserción, eliminación y búsqueda eficiente (O(log n))
Manejo de múltiples tablas
Persistencia de datos en archivos JSON
Interfaz gráfica (GUI) incluida
Consola interactiva con comandos

## Tecnologías utilizadas

Lenguaje: Java
Estructura de datos: Árbol AVL
Persistencia: Archivos JSON
Entorno de desarrollo: Eclipse

## Instalación

Clonar el repositorio:
git clone https://github.com/justkousei/Motor-de-B-squeda-AVL
Abrir el proyecto en Eclipse o cualquier IDE compatible con Java.
Asegurarse de que la carpeta src esté configurada correctamente.
Ejecutar la clase principal:
src/avl/Main.java

## Uso

Datasets de prueba y carga de datos

Este proyecto incluye conjuntos de datos de prueba y scripts para facilitar la carga de información en el sistema sin necesidad de ingresar registros manualmente.

Tipos de datasets

El proyecto contiene al menos dos datasets:

Dataset pequeño: usado para pruebas rápidas del sistema y validación de operaciones básicas.
Dataset mediano: contiene una mayor cantidad de registros para evaluar el rendimiento del árbol AVL.

Estos datasets están diseñados para probar operaciones como:

Inserción masiva de datos
Búsqueda eficiente
Eliminación de registros
Balanceo del árbol AVL

Scripts de carga de datos (LOAD DATASETS)

El sistema permite cargar automáticamente los datasets mediante scripts que ejecutan comandos predefinidos.

Ejemplo de uso

Para cargar un dataset desde un archivo:

LOAD datasets/pequeno.txt

o

LOAD datasets/mediano.txt
Funcionamiento del LOAD

El comando LOAD lee línea por línea el archivo y ejecuta los comandos como si fueran ingresados en consola.

Ejemplo de contenido del dataset:

CREATE estudiantes
INSERT estudiantes 1 Juan Electronica 20261
INSERT estudiantes 2 Maria Catastral 20261
INSERT estudiantes 3 Pedro Sistemas 20261
Ubicación de los archivos
Motor AVL
El sistema permite al usuario:

Agregar personas al sistema
Buscar registros almacenados
Eliminar datos existentes
Visualizar la estructura del árbol AVL
Especificar los espacios que tendra cada arbol
Guardar y cargar información desde un archivo JSON

La interacción se realiza mediante una interfaz gráfica que facilita la gestión de los datos.


## Estructura del Proyecto
```
Motor AVL
│── src
│   ├── avl
│   │   ├── AVL.java
│   │   ├── Nodo.java
│   │   ├── Main.java
│   │   ├── MainConsola.java
│   │   └── ConsolaDB.java
│   │
│   ├── gestor
│   │   └── DatabaseManager.java
│   │
│   ├── modelo
│   │   ├── Registro.java
│   │   └── Tabla.java
│   │
│   ├── persistencia
│   │   └── JsonManager.java
│   │
│   ├── vista
│   │   ├── VentanaDB.java
│   │   ├── VentanaCrearTabla.java
│   │   ├── VentanaInsertarRegistro.java
│   │   ├── VentanaBuscarRegistro.java
│   │   ├── VentanaBuscarRango.java
│   │   ├── VentanaActualizarRegistro.java
│   │   ├── VentanaEliminarRegistro.java
│   │   └── panelArbol.java
│── bin
│   └── (archivos compilados .class)
│── datasets
│   ├── data_pequeno.txt
│   ├── data_mediano.txt
│── scripts
│   ├── cargar_mediano.txt
│   ├── cargar_pequemo.txt
│── .project
│── .classpath
│── README.md
│── MotorDeBusqueda.exe
```

## Funcionamiento

El sistema permite interactuar mediante comandos en consola para gestionar tablas y registros.

Comandos disponibles
Comando	Descripción
-CREATE <tabla>	Crea una nueva tabla  
-INSERT <tabla> <id> <nombre> <programa> <codigo> <semestre> <edad>	Inserta un registro  
-FIND <tabla> <id>	Busca un registro por ID  
-UPDATE <tabla> <id> ...	Actualiza un registro  
-DELETE <tabla> <id>	Elimina un registro  
-RANGE <tabla> <inicio> <fin>	Busca registros en un rango  
-DROP <tabla>	Elimina una tabla  
-EXIT	Cierra el programa  

-Ejemplo de uso  
-CREATE estudiantes  
-INSERT estudiantes 10 Juan IA U001 5 20  
-FIND estudiantes 10  
-UPDATE estudiantes 10 Pedro Robotica U002 6 22  
-DELETE estudiantes 10  
-RANGE estudiantes 1 50  
-DROP estudiantes  
-EXIT  


## Requisitos
Java 16 o superior (IMPORTANTE: debe coincidir con la versión de compilación)
Sistema operativo Windows, Linux o Mac
Ejecución
Compilar
javac Main.java
Ejecutar
java Main

O ejecutar el .exe 

## Problemas comunes
Error de versión de Java

Si aparece un error como:

UnsupportedClassVersionError

Significa que el proyecto fue compilado con una versión más nueva de Java que la que estás usando.

Solución:

Actualizar Java
o
Compilar el proyecto con una versión más baja
