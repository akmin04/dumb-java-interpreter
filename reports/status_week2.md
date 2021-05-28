# Status Report 2

**DJI - Dumb Java Interpreter**

*A simple interpreter for a small subset of syntax and features from Java.*

Andy Min - 2021 May 7

### Accomplishments

- Began to work on the parser
- Created the abstract syntax tree structure with "algebraic data type" classes
  - `ProgramNode`
    - Contains a map of function names to `FunctionNode`s
  - `FunctionNode`
    - Contains function info (name, parameters) and a `StatementNode`
  - `StatementNode`
    - `Compound`
      - A list of statements in curly brackets
    - `If`
    - `For`
    - `While`
    - `Break`
    - `Continue`
    - `Return`
    - `VariableDeclaration`
    - `Expression`
      - An expression followed by a semicolon
  - `ExpressionNode`
    - `Literal`
      - e.g. 123, 12.3, "abcd"
    - `VariableReference`
    - `FunctionCall`
    - `BinaryExpression`
      - 2 `ExpressionNode`s separated by an operator
    - `UnaryExpression`
      - An `ExpressionNode` prefixed by an operator

### Problems/Risks

- Parsing mathematical operations with order of operations using Shunting-yard algorithm is not working yet

### Next Steps

- Finish the parser and test