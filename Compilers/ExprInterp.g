grammar ExprInterp;

@header {
import java.util.HashMap;
}

@members {
/** Map variable name to Integer object holding value */
HashMap memory = new HashMap();
}

prog:   stat+ ;
                
stat:   nnexpr NEWLINE {System.out.println($nnexpr.value);}
    |   ID '=' nnexpr NEWLINE
        {memory.put($ID.text, new Integer($nnexpr.value));}
    |   NEWLINE
    ;

nnexpr returns [int value]
    :   e=nexpr {$value = $e.value;}
        (   'AND' e=nexpr { $value = $value & $e.value;}
        |   'OR' e=nexpr { $value = $value | $e.value;}
        )*
    |   'NOT' e=nexpr { $value = ~$e.value;}
    ;


nexpr returns [int value]
    :   e=expr {$value = $e.value;}
        (   '>' e=expr { if ($value > $e.value) $value = 1; else $value = 0;}
        |   '<' e=expr { if ($value < $e.value) $value = 1; else $value = 0;}
        |   '==' e=expr {if ($value == $e.value) $value = 1; else $value = 0;}
        |   '!=' e=expr {if ($value != $e.value) $value = 1; else $value = 0;}
        |   '>=' e=expr {if ($value >= $e.value) $value = 1; else $value = 0;}
        |   '<=' e=expr {if ($value <= $e.value) $value = 1; else $value = 0;}
        )*
    ;


expr returns [int value]
    :   e=multExpr {$value = $e.value;}
        (   '+' e=multExpr {$value += $e.value;}
        |   '-' e=multExpr {$value -= $e.value;}
        )*
    ;

multExpr returns [int value]
    :   e=atom {$value = $e.value;} 
        (   '*' e=atom {$value *= $e.value;}
        |   '/' e=atom {$value /= $e.value;}
        )*
    ; 

atom returns [int value]
    :   INT {$value = Integer.parseInt($INT.text);}
    |   ID
        {
        Integer v = (Integer)memory.get($ID.text);
        if ( v!=null ) $value = v.intValue();
        else System.err.println("undefined variable "+$ID.text);
        }
    |   '(' e=nexpr ')' {$value= $e.value;}
    |   '-' e=atom {$value= $e.value*(-1);}
    ;


ID  :   ('a'..'z'|'A'..'Z')+ ;
INT :   '0'..'9'+ ;
NEWLINE:'\r'? '\n' ;
WS  :   (' '|'\t')+ {skip();} ;
