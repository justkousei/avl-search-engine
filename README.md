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
│
│── bin
│   └── (archivos compilados .class)
│
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
