# Interpreter Console
The continuation of the project with a GUI implementation: [Intepreter JavaFX](https://github.com/antoniacatrinel/Interpreter-Java/tree/main/Interpreter%20JavaFX)

#
Implement an interpreter for a toy language. You must use the model-view-controller architectural pattern and object-oriented concepts.

Statements:
- an `IStatement` interface that represents a statement in the language
- a `CompoundStatement` class that implements the `IStatement` interface
- a `PrintStatement` class that implements the `IStatement` interface
- an `AssignmentStatement` class that implements the `IStatement` interface
- an `IfStatement` class that implements the `IStatement` interface
- a `VariableDeclarationStatement` class that implements the `IStatement` interface
- a `NoOperationStatement` class that implements the `IStatement` interface

Types:
- an `IType` interface that represents a type in the language
- an `IntegerType` class that implements the `IType` interface
- a `BooleanType` class that implements the `IType` interface

Values:
- an `IValue` interface that represents a value in the language
- an `IntegerValue` class that implements the `IValue` interface
- a `BooleanValue` class that implements the `IValue` interface

Expressions:
- an `IExpression` interface that represents an expression in the language
- an `ArithmeticExpression` class that implements the `IExpression` interface
- a `ValueExpression` class that implements the `IExpression` interface
- a `LogicExpression` class that implements the `IExpression` interface
- a `VariableExpression` class that implements the `IExpression` interface

ADT:
Implement an Execution Stack, an Output List, and a Symbol Table. Create your own interfaces to wrap around generics for the ADTs.
- an `IStack` interface and a `Stack` class that implements the `IStack` interface
- an `IList` interface and a `List` class that implements the `IList` interface
- an `IDictionary` interface and a `Dictionary` class that implements the `IDictionary` interface

Program State:
- a `ProgramState` class that represents the state of the program at a given moment

Repository:
- an `IRepository` interface and a `Repository` class that implements the `IRepository` interface. The `Repository` class will store the list of `ProgramState` objects

Controller:
- contains a reference to an `IRepository` object
- has a `oneStep` method that executes one step of the currently selected program
- has an `allSteps` method that executes all steps of the currently selected program
- has a flag for displaying the execution of the program step by step

View:
- supports the selection of a pre-defined program
- can execute the selected program

Exceptions:
- define an ADT exception
- define an exception for `IExpression` evaluation
- define an exception for `IStatement` execution

#
Repository:
- add a new `logProgramStateExecution` method to the `IRepository` interface. Create the implementation using a `PrintWriter` in append mode

Controller:
- modify the `allSteps` method to log the state of the program after each step

File Operations:
- implement a new `FileTable` that manages files opened by the programs
- add a new `defaultValue` method to the `IType` interface
- modify each type to implement the `defaultValue` method
- modify the `VariableDeclarationStatement` class to use the `defaultValue` method
- add a new `StringType` class that implements the `IType` interface
- add a new `StringValue` class that implements the `IValue` interface
- override the `equals` method in all classes that extend the `IValue` interface
- add a new `OpenFileStatement` class that implements the `IStatement` interface
- add a new `ReadFileStatement` class that implements the `IStatement` interface
- add a new `CloseFileStatement` class that implements the `IStatement` interface

View:
Implement the View part of the MVC architecture
- add a new `TextMenu` class
- add a new abstract `Command` class
- add a new `ExitCommand` class that extends the `Command` class
- add a new `RunExample` class that extends the `Command` class
- more commands can be added if needed
- add a new `Interpreter` class that will be the main class of the application
    
IExpression:
- add a new `RelationalExpression` class that implements the `IExpression` interface

#
Heap Management:
- Add a new `ReferenceType` class that implements the `IType` interface
- Add a new `ReferenceValue` class that implements the `IValue` interface
- Implement a Heap Table to manage the heap memory. An address of 0 is considered an invalid (null) address
- Add a new `NewStatement` class that implements the `IStatement` interface. The `NewStatement` statement creates a new object in the heap and returns the address of the new object
- Define a new `HeapReadExpression` class that implements the `IExpression` interface
- Define a new `HeapWriteStatement` class that implements the `IStatement` interface

Garbage Collector:
- Add an unsafe garbage collector that removes all Heap Table entries that are not directly referenced by any `ReferenceValue` entry in the Symbol Table
- Call the garbage collector in the `allSteps` method of the `Controller` class, after each execution of a program
- Log the state of the program before and after the garbage collector is called
- Implement a safe garbage collector that also checks references to other Heap Table entries

IStatement:
- Define a new `WhileStatement` class that implements the `IStatement` interface

#
Implement support for concurrent programming. You must do the following modifications:

Repository:
- the repository must store a list of `ProgramState` objects, with each `ProgramState` object representing a thread
- add a new `getProgramList` method to the `IRepository` interface
- add a new `setProgramList` method to the `IRepository` interface
- remove the `getCurrentProgram` method from the `IRepository` interface
- change the `logProgramStateExecution` method to take a `ProgramState` as a parameter

ProgramState:
- add a new `isNotCompleted` method to the `ProgramState` class
- move the `oneStep` method from the `Controller` class to the `ProgramState` class
- add a new `id` field to the `ProgramState` class. The `id` field must be unique for each `ProgramState` object. You can use a static counter to generate unique IDs.

IStatement:
- define a new `ForkStatement` class that implements the `IStatement` interface. The Heap, FileTable and Output are shared between the parent and the child. The SymTable is not shared. The child has a copy of the SymTable of the parent. The child starts executing the statement that follows the `ForkStatement` statement.

Controller:
- add a new `getUnfinishedPrograms` method to the `Controller` class that returns all unfinished `ProgramState` objects from the repository
- implement an `ExecutorService` in the `Controller` class that uses a fixed thread pool with 2 threads
- implement a new `oneStep` method that concurrently executes the `oneStep` method of each `ProgramState` object in the list of `ProgramState` objects
- update the `allSteps` method to use the new `oneStep` method and the executor service created previously
- implement a new Conservative Garbage Collector that works with concurrent programs. It must take into account the fact that the Heap Table is shared between programs and each program has its own SymTable

#
Implement a Type Checker for your programs. You have to do the following modifications:
- add a new `typeCheck` method to the `IExpression` interface
- implement the `typeCheck` method for each expression
- add a new `typeCheck` method to the `IStatement` interface
- implement the `typeCheck` method for each statement
- call the `typeCheck` method for the selected program before creating the associated `ProgramState`. If the type check fails, the program should not be executed.
