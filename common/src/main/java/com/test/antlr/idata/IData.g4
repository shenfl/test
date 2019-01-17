grammar IData; // run: com.test.antlr.idata.IData file -tree /Users/shenfl/IdeaProjects/test/common/src/main/java/com/test/antlr/idata/idata.txt


file : group+ ;

group: INT sequence[$INT.int] ;

sequence[int n]
locals [int i = 1;]
     : ( {$i<=$n}? INT {$i++;} )*    // match n integers
     ;

INT  : [0-9]+ ;  // match integers
WS   : [ \t\n\r]+ -> skip ;    // toss out all whitespace