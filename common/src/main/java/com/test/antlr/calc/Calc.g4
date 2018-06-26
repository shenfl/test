grammar Calc;                            // 文法的名字为Calc

// 以下以小写字母开头的文法表示为语法元素
// 由大写字母开头的文法表示为词法元素
// 词法元素的表示类似于正则表示式
// 语法元素的表示类似于BNF

exprs
    : setExpr
    | calcExpr
    ;
set
setExpr : 'set' agmts ;
agmts : agmt (';' agmts)? ';'? ;
agmt : id=ID '=' num=NUMBER ;
calcExpr: 'calc' expr ;

// expr可能由多个产生式
// 在前面的产生式优先于在后面的产生式
// 这样来解决优先级的问题

expr: expr op=(MUL | DIV) expr
    | expr op=(ADD | SUB) expr
    | factor
    ;

factor: (sign=(ADD | SUB))? num=NUMBER
    | '(' expr ')'
    | id=ID
    | funCall
    ;

funCall: name=ID '(' params ')' ;
params : expr (',' params)? ;

WS : [ \t\n\r]+ -> skip ;
ID : [a-z]+ ;
NUMBER : [0-9]+('.'([0-9]+)?)? ;
ADD : '+' ;
SUB : '-' ;
MUL : '*' ;
DIV : '/' ;