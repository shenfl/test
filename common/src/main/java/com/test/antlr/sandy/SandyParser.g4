grammar SandyParser;

sandyFile : lines=line+ ;

line      : statement (NEWLINE | EOF) ;

statement : varDeclaration # varDeclarationStatement
          | assignment     # assignmentStatement ;

varDeclaration : VAR assignment ;

print : PRINT LPAREN expression RPAREN ;

assignment : ID ASSIGN expression ;

expression : left=expression operator=(DIVISION|ASTERISK) right=expression # binaryOperation
           | left=expression operator=(PLUS|MINUS) right=expression        # binaryOperation
           | value=expression AS targetType=type                           # typeConversion
           | LPAREN expression RPAREN # parenExpression
           | ID                #varReference
           | MINUS expression  #minusExpression
           | INTLIT # intLiteral
           | DECLIT # decimalLiteral ;



// Whitespace
NEWLINE            : '\r\n' | 'r' | '\n' ;
WS                 : [\t ]+ -> skip;

// Keywords
VAR                : 'var' ;
PRINT              : 'print';
AS                 : 'as';
INT                : 'Int';
DECIMAL            : 'Decimal';

// Literals
INTLIT             : '0'|[1-9][0-9]* ;
DECLIT             : '0'|[1-9][0-9]* '.' [0-9]+ ;

// Operators
PLUS               : '+' ;
MINUS              : '-' ;
ASTERISK           : '*' ;
DIVISION           : '/' ;
ASSIGN             : '=' ;
LPAREN             : '(' ;
RPAREN             : ')' ;

// Identifiers
ID                 : [_]*[a-z][A-Za-z0-9_]* ;