# Interpreter JavaFX
This project is the GUI continuation of the console version: [Interpreter Console](https://github.com/antoniacatrinel/Interpreter-Java/tree/main/Interpreter%20Console)

#
Implement a GUI for the Interpreter project using JavaFX. The GUI must support the following operations:
- A window to select the program that will be executed. Display the list of possible programs as a ListView. Each item of the ListView is the string representation of a possible program (IStatement).
- A window that displays the following information:
    - the number of Program States as a TextField
    - the Heap Table as a TableView with two columns: Address and Value
    - the Output as a ListView
    - the File Table as a ListView
    - the list of Program State identifiers as a ListView
    - a TableView with two columns: Name and Value, which displays the Sympol Table of the chosen Program State ID that has been selected from the list described previously
    - a ListView which displays the Execution Stack of the chosen PrgState ID that has been selected from the list described previously. The first element of the ListView is a string represenatation of the top of the Execution Stack, the second element represents the second element from the Execution Stack and so on.
    - A "One Step" button that runs the `oneStep` function of the `Controller` class. The displayed information is updated after each run. You may want to write a wrapper service for the repository and signal any change in the list of Program States.

Addionally, implement the following:

Statements:
- a `ConditionalAssignmentStatement` class that implements the `IStatement` interface
- a `DoWhileStatement` class that implements the `IStatement` interface
- a `ForStatement` class that implements the `IStatement` interface
- a `RepeatUntilStatement` class that implements the `IStatement` interface
- a `SleepStatement` class that implements the `IStatement` interface
- a `SwitchStatement` class that implements the `IStatement` interface
- a `WaitStatement` class that implements the `IStatement` interface

Expressions:
- a `NotExpression` class that implements the `IExpression` interface
- a `MULExpression` class that implements the `IExpression` interface
