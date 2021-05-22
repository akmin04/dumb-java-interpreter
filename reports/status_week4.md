# Status Report - Week 4

## DJI - Dumb Java Interpreter

Andy Min - 2021 May 21

*A simple interpreter for a small subset of syntax and features from Java.*

### Accomplishments

- Refractored and cleaned up many files
- Finished writing javadoc for all classes
- Setup logging system for easy debugging
- Abstract syntax tree and parser complete
  - Able to parse control statements (if/else/for/while/break/continue)
- Runtime engine almost complete
  - Variable set/get
  - Function calls
  - Able to compute math binary operations
  - Able to run loops and if statements (if/else/for/while)
  - Able to run control flow statements (break/continue/return)
    - Not very well tested yet

## Example

Test program that uses for loops, if statements, break/continue, variable assignments, simple math operations, and function calls with parameters.

```
int main() {
    int a = 0;
    for (int i = 0; i < 5; i += 1) {
        if (i == 3) break;
        for (int j = 0; j < 3; j += 1) {
            if (j == 1) {
                continue;
            }
            a = addOne(a);
        }
    }

    return a;
}

int addOne(int num) {
    return num + 1;
}
```

Full DJI log

```
> Task :cli:run
[INFO   ] parser.FunctionParser - Parsed function main
[INFO   ] parser.FunctionParser - Parsed function addOne


***** Abstract Syntax Tree *****
Program {
    functions: [
        Function {
            name: addOne
            return: int
            parameters: num(int)
            body: Statement.Return {
                expr: Expression.Binary {
                    operator: +
                    left: Expression.VariableReference {
                        name: num
                    }
                    right: Expression.Literal {
                        value: INT(1)
                    }
                }
            }
        }
        Function {
            name: main
            return: int
            parameters: []
            body: [
                Statement.VariableDeclaration {
                    name: a
                    type: INT
                    value: Expression.Literal {
                        value: INT(0)
                    }
                }
                Statement.For {
                    init: Statement.VariableDeclaration {
                        name: i
                        type: INT
                        value: Expression.Literal {
                            value: INT(0)
                        }
                    }
                    condition: Expression.Binary {
                        operator: <
                        left: Expression.VariableReference {
                            name: i
                        }
                        right: Expression.Literal {
                            value: INT(5)
                        }
                    }
                    post: Expression.Binary {
                        operator: +=
                        left: Expression.VariableReference {
                            name: i
                        }
                        right: Expression.Literal {
                            value: INT(1)
                        }
                    }
                    body: Statement.Block {
                        statements: [
                            Statement.If {
                                condition: Expression.Binary {
                                    operator: ==
                                    left: Expression.VariableReference {
                                        name: i
                                    }
                                    right: Expression.Literal {
                                        value: INT(3)
                                    }
                                }
                                body: Statement.Break
                                else: null
                            }
                            Statement.For {
                                init: Statement.VariableDeclaration {
                                    name: j
                                    type: INT
                                    value: Expression.Literal {
                                        value: INT(0)
                                    }
                                }
                                condition: Expression.Binary {
                                    operator: <
                                    left: Expression.VariableReference {
                                        name: j
                                    }
                                    right: Expression.Literal {
                                        value: INT(3)
                                    }
                                }
                                post: Expression.Binary {
                                    operator: +=
                                    left: Expression.VariableReference {
                                        name: j
                                    }
                                    right: Expression.Literal {
                                        value: INT(1)
                                    }
                                }
                                body: Statement.Block {
                                    statements: [
                                        Statement.If {
                                            condition: Expression.Binary {
                                                operator: ==
                                                left: Expression.VariableReference {
                                                    name: j
                                                }
                                                right: Expression.Literal {
                                                    value: INT(1)
                                                }
                                            }
                                            body: Statement.Block {
                                                statements: Statement.Continue
                                            }
                                            else: null
                                        }
                                        Statement.Expression {
                                            expr: Expression.Binary {
                                                operator: =
                                                left: Expression.VariableReference {
                                                    name: a
                                                }
                                                right: Expression.FunctionCall {
                                                    name: addOne
                                                    args: Expression.VariableReference {
                                                        name: a
                                                    }
                                                }
                                            }
                                        }
                                    ]
                                }
                            }
                        ]
                    }
                }
                Statement.Return {
                    expr: Expression.VariableReference {
                        name: a
                    }
                }
            ]
        }
    ]
}


***** Runtime *****
[INFO   ] runtime.Runtime - Running function main
[INFO   ] runtime.Runtime - Running function addOne
[INFO   ] runtime.Runtime - Running function addOne
[INFO   ] runtime.Runtime - Running function addOne
[INFO   ] runtime.Runtime - Running function addOne
[INFO   ] runtime.Runtime - Running function addOne
[INFO   ] runtime.Runtime - Running function addOne
Main returned: INT(6)

BUILD SUCCESSFUL in 1s
5 actionable tasks: 3 executed, 2 up-to-date
```

### Problems/Risks

- High Priority
  - Proper variable scoping is not completely working yet.
  - Math doesn't work between ints/doubles
- Medium Priority
  - Runtime engine doesn't have access to character file locations, so some interpreter errors don't have any line/col data
- Low Priority
  - No postfix ++ or -- implemented
  - No safeguard against infinite loops 

### Next Steps

- Work on high priority issues
- Prepare presentation