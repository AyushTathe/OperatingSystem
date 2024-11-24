# OS Simulation Project

This project simulates a simple operating system that reads and executes programs from an input file, manages memory, performs basic I/O operations, and writes the output to an output file. The program is built in Java and demonstrates fundamental concepts of operating system design, including memory management, process scheduling, and I/O handling.

## Features

- **Memory Management**: A fixed-size memory array simulates an OS's memory space.
- **Program Execution**: The OS reads and executes a sequence of instructions in memory.
- **Input/Output Handling**: Handles reading data from an input file and writing results to an output file.
- **Instruction Set**: Supports basic instructions like load, store, compare, branch, and I/O operations.

## Technologies

- Java (JDK 18 or higher)
- File I/O (BufferedReader, FileReader, FileWriter)

## Setup Instructions

To run this project locally, follow the steps below:

### Prerequisites

- **Java 18** (or higher) installed on your system.
- A text editor or IDE (e.g., IntelliJ IDEA, Eclipse).
- Git to clone the repository.

### Steps

1. **Clone the Repository**

   Clone the repository to your local machine using Git:

   ```bash
   git clone https://github.com/yourusername/OsProject.git
   cd OsProject
2. **Place Input File**

   The program requires an input file Input.txt for simulation. Ensure the file is placed in the root directory of the project.

3. **Compile the Project**
  If using IntelliJ IDEA, simply build the project. Alternatively, you can compile the Java code using the command:
   ```bash
     javac -d target/classes src/org/osproject/*.java
4. **Run the Program**
5. **Check the Output** :

   The program will output results to a file called **Output.txt**.

## Input File Format

The input file should be structured as follows:

### Program Card (`$AMJ`)

The program starts with a program card (`$AMJ`) indicating the beginning of the program.

### Data Card (`$DATA`)

The data card (`$DATA`) indicates that data operations (e.g., reading and writing) should begin.

### End Card (`$END`)

The end card (`$END`) signifies the end of the program execution.

### Instructions

Each instruction follows a specific format. Here's a list of supported instructions:

- **Load Register (`LR address`)**: Load data from memory at the specified address into the register `R`.
- **Store Register (`SR address`)**: Store the data in register `R` into memory at the specified address.
- **Compare Register (`CR address`)**: Compare the data in register `R` with the data at the specified memory address.
- **Branch on True (`BT address`)**: If the comparison (`T`) was true, set the instruction counter (`IC`) to the specified address.
- **Get Data (`GD`)**: Read data from the input file and store it in memory.
- **Put Data (`PD`)**: Write data from memory to the output file.
- **Halt (`H`)**: Halt the execution of the program.

 ### Example Input File
```txt
    $AMJ
    LR 12
    SR 15
    CR 18
    BT 10
    $DATA
    GD
    PD
    $END
