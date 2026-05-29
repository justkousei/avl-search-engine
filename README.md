## Motor de Búsqueda con Árbol AVL

Este proyecto consiste en un motor de búsqueda desarrollado en Java que permite almacenar, organizar y consultar información de personas utilizando un árbol AVL. El sistema garantiza eficiencia en las operaciones gracias al balanceo automático del árbol, y además incluye persistencia de datos en archivos JSON y una interfaz gráfica para la interacción con el usuario.


## Características

Inserción de registros en un árbol AVL auto-balanceado
Búsqueda eficiente de datos
Eliminación de nodos manteniendo el balance
Persistencia de datos en archivos JSON
Interfaz gráfica para visualizar y gestionar la información
Representación visual del árbol


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

El sistema permite al usuario:

Agregar personas al sistema
Buscar registros almacenados
Eliminar datos existentes
Visualizar la estructura del árbol AVL
Guardar y cargar información desde un archivo JSON

La interacción se realiza mediante una interfaz gráfica que facilita la gestión de los datos.


## Ejemplo de funcionamiento

El usuario ingresa los datos de una persona.
El sistema inserta el registro en el árbol AVL.
El árbol se balancea automáticamente si es necesario.
El usuario puede buscar el registro y obtener el resultado de forma rápida.


## Estructura del proyecto

```
Motor AVL
│── src
│   ├── avl
│   │   ├── AVL.java
│   │   ├── Nodo.java
│   │   └── Main.java
│   ├── gestor
│   │   └── DatabaseManager.java
│   ├── modelo
│   │   └── Persona.java
│   ├── persistencia
│   │   └── JsonManager.java
│   ├── vista
│   │   ├── VentanaDB.java
│   │   └── panelArbol.java
│
│── personas.json
```

## Funcionamiento interno

El núcleo del sistema es un árbol AVL, que es un árbol binario de búsqueda auto-balanceado. Esto significa que, después de cada inserción o eliminación, el árbol realiza rotaciones para mantener su equilibrio.

Las operaciones principales incluyen:

El núcleo del sistema es un árbol AVL, que es un árbol binario de búsqueda auto-balanceado. Esto significa que, después de cada inserción o eliminación, el árbol realiza rotaciones para mantener su equilibrio y garantizar tiempos de respuesta eficientes.

El sistema funciona a partir de comandos similares a los de una base de datos, permitiendo gestionar colecciones de datos de manera estructurada. A continuación se describen los principales comandos soportados:

CREATE estudiantes
Crea una nueva colección (o estructura) llamada "estudiantes" donde se almacenarán los registros en un árbol AVL.

INSERT estudiantes 10 Juan Sistemas 20251020016 3 18
Inserta un nuevo registro en la colección. En este caso:

Clave: 10
Nombre: Juan 
Programa: Sistemas
Código: 20251020016
Semestre: 3
Edad: 18

El sistema ubica el nodo en el árbol AVL y realiza las rotaciones necesarias para mantener el balance.

FIND estudiantes 10
Busca un registro dentro del árbol AVL usando su clave. La búsqueda se realiza en tiempo O(log n).
UPDATE estudiantes 10 Pedro Electrica 20251020100 6 22
Modifica los datos de un registro existente identificado por su clave, manteniendo su posición en el árbol.
DELETE estudiantes 10
Elimina un nodo del árbol AVL. Posteriormente, el sistema re-balancea el árbol automáticamente.
RANGE estudiantes 1 50
Realiza una búsqueda por rango, mostrando todos los registros cuyas claves estén entre 1 y 50. Esta operación aprovecha el recorrido ordenado del árbol.
DROP estudiantes
Elimina completamente la colección, borrando todos los datos almacenados en el árbol.
EXIT
Finaliza la ejecución del sistema.

Además, el sistema utiliza un gestor de base de datos que permite guardar y cargar los datos en formato JSON, asegurando persistencia.
