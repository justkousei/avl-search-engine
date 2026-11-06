# AVL Tree Search Engine

![Java](https://img.shields.io/badge/Java-16%2B-orange?logo=openjdk&logoColor=white)
![Data Structure](https://img.shields.io/badge/Data%20Structure-AVL%20Tree-green)
![Persistence](https://img.shields.io/badge/Persistence-JSON-blue)

A search engine written in **Java** that uses **AVL trees** as its core data structure to store and manage data efficiently.

The system works like a lightweight database: it lets you insert, search, update, and delete records, with consistently fast performance thanks to the AVL tree's automatic balancing.

<!-- Once you have screenshots, save them in docs/ and uncomment:
![Graphical interface](docs/gui_screenshot.png)
-->

## Features

- Custom AVL tree implementation with automatic balancing.
- Insertion, deletion, and search in **O(log n)**.
- Range search by ID.
- Support for multiple tables.
- Data persistence in JSON files.
- Graphical user interface (GUI) with AVL tree visualization.
- Interactive command-line console.
- Bulk data loading through scripts and sample datasets.

## Tech Stack

| Component               | Technology        |
| ----------------------- | ----------------- |
| Language                | Java              |
| Data structure          | AVL Tree          |
| Persistence             | JSON files        |
| GUI                     | Java Swing        |
| Development environment | Eclipse / VS Code |

## Requirements

- **Java 16 or higher** (must match the version used to compile).
- Windows, Linux, or macOS.

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/justkousei/Motor_de_Busqueda_AVL.git
   cd Motor_de_Busqueda_AVL
   ```

2. Compile the project:

   ```bash
   javac -d bin -sourcepath src src/avl/Main.java
   ```

3. Run it:

   ```bash
   # Graphical interface
   java -cp bin avl.Main

   # Interactive console
   java -cp bin avl.MainConsola
   ```

You can also open the project in Eclipse (or any Java IDE) and run `src/avl/Main.java`, or simply use the `MotorDeBusqueda.exe` executable on Windows.

## Usage

### Console commands

| Command                           | Description                               |
| --------------------------------- | ----------------------------------------- |
| `CREATE <table>`                  | Creates a new table                       |
| `INSERT <table> <fields...>`      | Inserts a record                          |
| `FIND <table> <id>`               | Finds a record by ID                      |
| `UPDATE <table> <id> <fields...>` | Updates a record                          |
| `DELETE <table> <id>`             | Deletes a record                          |
| `RANGE <table> <start> <end>`     | Finds records within an ID range          |
| `LOAD <file>`                     | Runs the commands in a file, line by line |
| `DROP <table>`                    | Deletes a table                           |
| `EXIT`                            | Closes the program                        |

### Example

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

### Graphical interface

From the GUI you can:

- Create tables and define the size of each one.
- Insert, search (by ID or by range), update, and delete records.
- Visualize the AVL tree structure.
- Save and load data from a JSON file.

## Sample Datasets

The project includes test data so you don't have to enter records by hand:

| Dataset | File                        | Purpose                                              |
| ------- | --------------------------- | ---------------------------------------------------- |
| Small   | `datasets/data_pequeno.txt` | Quick tests and validation of basic operations       |
| Medium  | `datasets/data_mediano.txt` | Larger number of records to evaluate AVL performance |

They are designed to test bulk insertion, efficient search, record deletion, and AVL tree balancing.

The `LOAD` command reads a file line by line and runs each command as if it had been typed into the console:

```text
LOAD datasets/data_pequeno.txt
LOAD datasets/data_mediano.txt
```

Example dataset content:

```text
CREATE estudiantes
INSERT estudiantes 1 Juan Electronica 20261
INSERT estudiantes 2 Maria Catastral 20261
INSERT estudiantes 3 Pedro Sistemas 20261
```

Quick-load scripts are in the `scripts/` folder.

## Project Structure

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

## Documentation

The full technical write-up of the design and algorithms is in [`Documentación_Técnica.pdf`](Documentación_Técnica.pdf) (in Spanish).

## Troubleshooting

**`UnsupportedClassVersionError`**

The project was compiled with a newer Java version than the one you have installed.

Fix: update Java, or recompile the project with your installed version.

## Author

Final project for Computer Science, developed by [@justkousei](https://github.com/justkousei).
