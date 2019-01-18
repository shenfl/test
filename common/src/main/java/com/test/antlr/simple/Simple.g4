grammar Simple;//com.test.antlr.simple.Simple prog

prog: classDef+;

classDef
    : 'class' ID '{' member+ '}'
        {System.out.println("class " + $ID.getText());}
    ;

member
    : 'int' ID ';'
        {System.out.println("var " + $ID.getText());}
    | 'int' f=ID '(' ID ')' '{' stat '}'
        {System.out.println("method: " + $f.getText());}
    ;

stat: expr ';'
        {System.out.println("found expr: " + $ctx.getText());}
    | ID '=' expr ';'
        {System.out.println("found assign: " + $ID.getText() + ":" + $ctx.getText());}
    ;

expr: INT
    | ID '(' INT ')'
    ;

INT: [0-9]+;
ID: [a-zA-Z];
WS: [ \t\n\r]+ -> skip;