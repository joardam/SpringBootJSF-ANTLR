grammar EntityDef;

entityFile : entityDef+ ;

entityDef : 'entity' ID '{' fieldDef+ '}' ;

fieldDef : type ID ;

type : 'String' | 'Long' | 'Integer' | 'BigDecimal' | 'Boolean' ;

ID : [a-zA-Z_][a-zA-Z_0-9]* ;

WS : [ \t\r\n]+ -> skip ;
