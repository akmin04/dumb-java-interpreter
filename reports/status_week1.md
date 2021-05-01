# Status Report 

## Week 1

Andy Min - 2021 April 30

### Accomplishments

- Created Java project with Gradle
- Planned out what lexer tokens will be used
  - `IdentifierToken`
    - Function and variable names
  - `KeywordToken`
    - A subset of Java keywords that will be used understood by the interpreter
    - `if`, `else`, `for`, `while`, `do`, `break` `continue`, `return`
    - `boolean`, `char`, `int`, `double`, `void`
  - `SymbolToken`
    - A subset of Java symbols and operators
    - Organized simliarly to Go's compiler (https://golang.org/pkg/go/token/#Token)
    - ADD (`+`), SUB(`-`), MUL(`*`), QUO(`/`), REM(`%`), ADD_ASSIGN(`+=`), SUB_ASSIGN(`-=`), MUL_ASSIGN(`*=`), QUO_ASSIGN(`/=`), REM_ASSIGN(`%=`)
    - ASSIGN(`=`), NOT(`!`), INC(`++`), DEC(`--`), EQL(`==`), LSS(`<`), GTR(`>`), NEQ(`!=`), LEQ(`<=`), GEQ(`>=`), LAND(`&&`), LOR(`||`)
    - LPAREN(`(`), RPAREN(`)`), LBRACK(`[`), RBRACK(`]`), LBRACE(`{`), RBRACE(`}`), COMMA(`,`), PERIOD(`.`), SEMICOLON(`;`), COLON(`:`)
  - `LiteralToken`
    - A literal value
    - e.g. `true`, `5`, `5.0`, `'a'`, `"hello"`
    - `LiteralToken.Boolean`, `LiteralToken.Int`, `LiteralToken.Double`, `LiteralToken.Char`, `LiteralToken.String`
- Implemented the tokens and lexer
  - Basic tests work
- Implemented basic user-friendly errors
  - e.g. File not found, invalid integer/double literal, unknown character

### Problems/Risks

- User error row/column location in file not working
  - Errors should print friendly messages that include the row/column of the error in the source file
- Haven't thoroughly tested edge cases for the lexer identifying symbols
  - e.g. `++` vs `+`

### Next Steps

- Write unit tests for the lexer and fix any bugs
- Begin planning out the abstract syntax tree nodes and write the program parser