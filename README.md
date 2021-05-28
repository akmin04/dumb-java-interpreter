# DJI - Dumb Java Interpreter

2021 AP CS Final Project

Project Structure
```
.
├── cli                 # command line interface module
├── core                # core interpreter module
│   └── src/.../
│       ├── ast         # abstract syntax tree nodes
│       ├── exceptions  # user-side exceptions for simple error logging
│       ├── lexer       # lexical analyzer that splits the source file into tokens
│       ├── parser      # parser that constructs an abstract syntax tree
│       ├── runtime     # runtime engine
│       └── tokens      # tokens generated by the lexer and used by the parser
├── examples            # example dumb java programs
└── reports             # submission reports
```

Run
```bash
./gradlew :cli:run
```

Create native binary (after installing GraalVM with homebrew)
```bash
./native.sh
```

Generate javadocs (includes private fields/classes)
```bash
./gradlew :core:javadoc
```

Usage
```
Usage: dji [-hVv] [--tokens | --ast] <file>
Run the dumb java interpreter.
      <file>      The source file.
      --ast       Print the abstract syntax tree.
  -h, --help      Show this help message and exit.
      --tokens    Print the tokens.
  -v              Verbosity level (-v, -vv).
  -V, --version   Print version information and exit.

```