# Status Report 3

**DJI - Dumb Java Interpreter**

*A simple interpreter for a small subset of syntax and features from Java.*

Andy Min - 2021 May 14

### Accomplishments

- Finished most elements of the parser
  - Can parse a basic program with top-level functions
  - Fixed many bugs
- Added pretty printing for the Abstract Syntax Tree
  - Prints in a nice tree for easy debugging
- Started writing a basic interpreter runtime
  -  `RuntimeState`
    - Contains runtime state information such as the current for/while loop, function return values, etc.
  - `RuntimeStore`
    - Contains the variable map and stack
    - Retrieve variable values in O(1)
    - Add variable values in O(1)
    - Create a new variable scope in O(1)
    - Pop a variable scope in O(n)
  - `Runtime`
    - Handles the `RuntimeState` and `RuntimeStore` while traversing the abstract syntax tree
    - Currently only implemented running the `main` function and the `return` statement

## Example

Input

```
int main() {
    int a = foo(10);
    return 5;
}

int foo(double n) {
    return n + 5 * (1 / -5);
}
```

Full DJI log

```
***** Tokens *****
Type(int)
Identifier(main)
Symbol(()
Symbol())
Symbol({)
Type(int)
Identifier(a)
Symbol(=)
Identifier(foo)
Symbol(()
IntLiteral(10)
Symbol())
Symbol(;)
Keyword(return)
IntLiteral(5)
Symbol(;)
Symbol(})
Type(int)
Identifier(foo)
Symbol(()
Type(double)
Identifier(n)
Symbol())
Symbol({)
Keyword(return)
Identifier(n)
Symbol(+)
IntLiteral(5)
Symbol(*)
Symbol(()
IntLiteral(1)
Symbol(/)
Symbol(-)
IntLiteral(5)
Symbol())
Symbol(;)
Symbol(})


***** Abstract Syntax Tree *****
Program {
    functions: [
        Function {
            name: foo
            return: int
            parameters: n(double)
            statements: Statement.Return {
                expr: Expression.Binary {
                    operator: +
                    left: Expression.VariableReference {
                        name: n
                    }
                    right: Expression.Binary {
                        operator: *
                        left: Expression.Literal {
                            value: INT(5)
                        }
                        right: Expression.Binary {
                            operator: /
                            left: Expression.Literal {
                                value: INT(1)
                            }
                            right: Expression.Unary {
                                operator: -
                                expression: Expression.Literal {
                                    value: INT(5)
                                }
                            }
                        }
                    }
                }
            }
        }
        Function {
            name: main
            return: int
            parameters: [
            ]
            statements: [
                Statement.VariableDeclaration {
                    name: a
                    value: Expression.FunctionCall {
                        name: foo
                        args: Expression.Literal {
                            value: INT(10)
                        }
                    }
                }
                Statement.Return {
                    expr: Expression.Literal {
                        value: INT(5)
                    }
                }
            ]
        }
    ]
}


***** Runtime *****
Main returned: INT(5)

BUILD SUCCESSFUL in 3s
4 actionable tasks: 2 executed, 2 up-to-date
9:32:04 PM: Task execution finished 'run'.
```

Notes

- The parser was able to do parse variable declarations, function calls, function arguments and parameters, return statements, proper order of operations, parenthesis, unary operation (-5)
- The runtime properly returned 5
  - Since I've only implemented the `return` statement in the runtime, the rest of the program was simply ignored

### Problems/Risks

- After quickly writing lot's of code, some parts of it are not very well organized and are missing documentation
- Implementing control flow statements (for, while) and "jump" statements (break, continue, return) runtime elements seems difficult

### Next Steps

- Reorganize classes and files and cleanup messy code
- Finish implementing the runtime