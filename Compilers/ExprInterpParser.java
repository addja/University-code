// $ANTLR 3.4 ExprInterp.g 2015-02-20 17:55:48

import java.util.HashMap;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class ExprInterpParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "INT", "NEWLINE", "WS", "'!='", "'('", "')'", "'*'", "'+'", "'-'", "'/'", "'<'", "'<='", "'='", "'=='", "'>'", "'>='", "'AND'", "'NOT'", "'OR'"
    };

    public static final int EOF=-1;
    public static final int T__8=8;
    public static final int T__9=9;
    public static final int T__10=10;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__19=19;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int ID=4;
    public static final int INT=5;
    public static final int NEWLINE=6;
    public static final int WS=7;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public ExprInterpParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public ExprInterpParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return ExprInterpParser.tokenNames; }
    public String getGrammarFileName() { return "ExprInterp.g"; }


    /** Map variable name to Integer object holding value */
    HashMap memory = new HashMap();



    // $ANTLR start "prog"
    // ExprInterp.g:12:1: prog : ( stat )+ ;
    public final void prog() throws RecognitionException {
        try {
            // ExprInterp.g:12:5: ( ( stat )+ )
            // ExprInterp.g:12:9: ( stat )+
            {
            // ExprInterp.g:12:9: ( stat )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= ID && LA1_0 <= NEWLINE)||LA1_0==9||LA1_0==13||LA1_0==22) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ExprInterp.g:12:9: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_prog23);
            	    stat();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "prog"



    // $ANTLR start "stat"
    // ExprInterp.g:14:1: stat : ( nnexpr NEWLINE | ID '=' nnexpr NEWLINE | NEWLINE );
    public final void stat() throws RecognitionException {
        Token ID2=null;
        int nnexpr1 =0;

        int nnexpr3 =0;


        try {
            // ExprInterp.g:14:5: ( nnexpr NEWLINE | ID '=' nnexpr NEWLINE | NEWLINE )
            int alt2=3;
            switch ( input.LA(1) ) {
            case INT:
            case 9:
            case 13:
            case 22:
                {
                alt2=1;
                }
                break;
            case ID:
                {
                int LA2_2 = input.LA(2);

                if ( (LA2_2==17) ) {
                    alt2=2;
                }
                else if ( (LA2_2==NEWLINE||LA2_2==8||(LA2_2 >= 11 && LA2_2 <= 16)||(LA2_2 >= 18 && LA2_2 <= 21)||LA2_2==23) ) {
                    alt2=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 2, input);

                    throw nvae;

                }
                }
                break;
            case NEWLINE:
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }

            switch (alt2) {
                case 1 :
                    // ExprInterp.g:14:9: nnexpr NEWLINE
                    {
                    pushFollow(FOLLOW_nnexpr_in_stat50);
                    nnexpr1=nnexpr();

                    state._fsp--;


                    match(input,NEWLINE,FOLLOW_NEWLINE_in_stat52); 

                    System.out.println(nnexpr1);

                    }
                    break;
                case 2 :
                    // ExprInterp.g:15:9: ID '=' nnexpr NEWLINE
                    {
                    ID2=(Token)match(input,ID,FOLLOW_ID_in_stat64); 

                    match(input,17,FOLLOW_17_in_stat66); 

                    pushFollow(FOLLOW_nnexpr_in_stat68);
                    nnexpr3=nnexpr();

                    state._fsp--;


                    match(input,NEWLINE,FOLLOW_NEWLINE_in_stat70); 

                    memory.put((ID2!=null?ID2.getText():null), new Integer(nnexpr3));

                    }
                    break;
                case 3 :
                    // ExprInterp.g:17:9: NEWLINE
                    {
                    match(input,NEWLINE,FOLLOW_NEWLINE_in_stat90); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "stat"



    // $ANTLR start "nnexpr"
    // ExprInterp.g:20:1: nnexpr returns [int value] : (e= nexpr ( 'AND' e= nexpr | 'OR' e= nexpr )* | 'NOT' e= nexpr );
    public final int nnexpr() throws RecognitionException {
        int value = 0;


        int e =0;


        try {
            // ExprInterp.g:21:5: (e= nexpr ( 'AND' e= nexpr | 'OR' e= nexpr )* | 'NOT' e= nexpr )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0 >= ID && LA4_0 <= INT)||LA4_0==9||LA4_0==13) ) {
                alt4=1;
            }
            else if ( (LA4_0==22) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // ExprInterp.g:21:9: e= nexpr ( 'AND' e= nexpr | 'OR' e= nexpr )*
                    {
                    pushFollow(FOLLOW_nexpr_in_nnexpr115);
                    e=nexpr();

                    state._fsp--;


                    value = e;

                    // ExprInterp.g:22:9: ( 'AND' e= nexpr | 'OR' e= nexpr )*
                    loop3:
                    do {
                        int alt3=3;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==21) ) {
                            alt3=1;
                        }
                        else if ( (LA3_0==23) ) {
                            alt3=2;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // ExprInterp.g:22:13: 'AND' e= nexpr
                    	    {
                    	    match(input,21,FOLLOW_21_in_nnexpr131); 

                    	    pushFollow(FOLLOW_nexpr_in_nnexpr135);
                    	    e=nexpr();

                    	    state._fsp--;


                    	     value = value & e;

                    	    }
                    	    break;
                    	case 2 :
                    	    // ExprInterp.g:23:13: 'OR' e= nexpr
                    	    {
                    	    match(input,23,FOLLOW_23_in_nnexpr151); 

                    	    pushFollow(FOLLOW_nexpr_in_nnexpr155);
                    	    e=nexpr();

                    	    state._fsp--;


                    	     value = value | e;

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // ExprInterp.g:25:9: 'NOT' e= nexpr
                    {
                    match(input,22,FOLLOW_22_in_nnexpr178); 

                    pushFollow(FOLLOW_nexpr_in_nnexpr182);
                    e=nexpr();

                    state._fsp--;


                     value = ~e;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "nnexpr"



    // $ANTLR start "nexpr"
    // ExprInterp.g:29:1: nexpr returns [int value] : e= expr ( '>' e= expr | '<' e= expr | '==' e= expr | '!=' e= expr | '>=' e= expr | '<=' e= expr )* ;
    public final int nexpr() throws RecognitionException {
        int value = 0;


        int e =0;


        try {
            // ExprInterp.g:30:5: (e= expr ( '>' e= expr | '<' e= expr | '==' e= expr | '!=' e= expr | '>=' e= expr | '<=' e= expr )* )
            // ExprInterp.g:30:9: e= expr ( '>' e= expr | '<' e= expr | '==' e= expr | '!=' e= expr | '>=' e= expr | '<=' e= expr )*
            {
            pushFollow(FOLLOW_expr_in_nexpr210);
            e=expr();

            state._fsp--;


            value = e;

            // ExprInterp.g:31:9: ( '>' e= expr | '<' e= expr | '==' e= expr | '!=' e= expr | '>=' e= expr | '<=' e= expr )*
            loop5:
            do {
                int alt5=7;
                switch ( input.LA(1) ) {
                case 19:
                    {
                    alt5=1;
                    }
                    break;
                case 15:
                    {
                    alt5=2;
                    }
                    break;
                case 18:
                    {
                    alt5=3;
                    }
                    break;
                case 8:
                    {
                    alt5=4;
                    }
                    break;
                case 20:
                    {
                    alt5=5;
                    }
                    break;
                case 16:
                    {
                    alt5=6;
                    }
                    break;

                }

                switch (alt5) {
            	case 1 :
            	    // ExprInterp.g:31:13: '>' e= expr
            	    {
            	    match(input,19,FOLLOW_19_in_nexpr226); 

            	    pushFollow(FOLLOW_expr_in_nexpr230);
            	    e=expr();

            	    state._fsp--;


            	     if (value > e) value = 1; else value = 0;

            	    }
            	    break;
            	case 2 :
            	    // ExprInterp.g:32:13: '<' e= expr
            	    {
            	    match(input,15,FOLLOW_15_in_nexpr246); 

            	    pushFollow(FOLLOW_expr_in_nexpr250);
            	    e=expr();

            	    state._fsp--;


            	     if (value < e) value = 1; else value = 0;

            	    }
            	    break;
            	case 3 :
            	    // ExprInterp.g:33:13: '==' e= expr
            	    {
            	    match(input,18,FOLLOW_18_in_nexpr266); 

            	    pushFollow(FOLLOW_expr_in_nexpr270);
            	    e=expr();

            	    state._fsp--;


            	    if (value == e) value = 1; else value = 0;

            	    }
            	    break;
            	case 4 :
            	    // ExprInterp.g:34:13: '!=' e= expr
            	    {
            	    match(input,8,FOLLOW_8_in_nexpr286); 

            	    pushFollow(FOLLOW_expr_in_nexpr290);
            	    e=expr();

            	    state._fsp--;


            	    if (value != e) value = 1; else value = 0;

            	    }
            	    break;
            	case 5 :
            	    // ExprInterp.g:35:13: '>=' e= expr
            	    {
            	    match(input,20,FOLLOW_20_in_nexpr306); 

            	    pushFollow(FOLLOW_expr_in_nexpr310);
            	    e=expr();

            	    state._fsp--;


            	    if (value >= e) value = 1; else value = 0;

            	    }
            	    break;
            	case 6 :
            	    // ExprInterp.g:36:13: '<=' e= expr
            	    {
            	    match(input,16,FOLLOW_16_in_nexpr326); 

            	    pushFollow(FOLLOW_expr_in_nexpr330);
            	    e=expr();

            	    state._fsp--;


            	    if (value <= e) value = 1; else value = 0;

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "nexpr"



    // $ANTLR start "expr"
    // ExprInterp.g:41:1: expr returns [int value] : e= multExpr ( '+' e= multExpr | '-' e= multExpr )* ;
    public final int expr() throws RecognitionException {
        int value = 0;


        int e =0;


        try {
            // ExprInterp.g:42:5: (e= multExpr ( '+' e= multExpr | '-' e= multExpr )* )
            // ExprInterp.g:42:9: e= multExpr ( '+' e= multExpr | '-' e= multExpr )*
            {
            pushFollow(FOLLOW_multExpr_in_expr369);
            e=multExpr();

            state._fsp--;


            value = e;

            // ExprInterp.g:43:9: ( '+' e= multExpr | '-' e= multExpr )*
            loop6:
            do {
                int alt6=3;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==12) ) {
                    alt6=1;
                }
                else if ( (LA6_0==13) ) {
                    alt6=2;
                }


                switch (alt6) {
            	case 1 :
            	    // ExprInterp.g:43:13: '+' e= multExpr
            	    {
            	    match(input,12,FOLLOW_12_in_expr385); 

            	    pushFollow(FOLLOW_multExpr_in_expr389);
            	    e=multExpr();

            	    state._fsp--;


            	    value += e;

            	    }
            	    break;
            	case 2 :
            	    // ExprInterp.g:44:13: '-' e= multExpr
            	    {
            	    match(input,13,FOLLOW_13_in_expr405); 

            	    pushFollow(FOLLOW_multExpr_in_expr409);
            	    e=multExpr();

            	    state._fsp--;


            	    value -= e;

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "expr"



    // $ANTLR start "multExpr"
    // ExprInterp.g:48:1: multExpr returns [int value] : e= atom ( '*' e= atom | '/' e= atom )* ;
    public final int multExpr() throws RecognitionException {
        int value = 0;


        int e =0;


        try {
            // ExprInterp.g:49:5: (e= atom ( '*' e= atom | '/' e= atom )* )
            // ExprInterp.g:49:9: e= atom ( '*' e= atom | '/' e= atom )*
            {
            pushFollow(FOLLOW_atom_in_multExpr447);
            e=atom();

            state._fsp--;


            value = e;

            // ExprInterp.g:50:9: ( '*' e= atom | '/' e= atom )*
            loop7:
            do {
                int alt7=3;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==11) ) {
                    alt7=1;
                }
                else if ( (LA7_0==14) ) {
                    alt7=2;
                }


                switch (alt7) {
            	case 1 :
            	    // ExprInterp.g:50:13: '*' e= atom
            	    {
            	    match(input,11,FOLLOW_11_in_multExpr464); 

            	    pushFollow(FOLLOW_atom_in_multExpr468);
            	    e=atom();

            	    state._fsp--;


            	    value *= e;

            	    }
            	    break;
            	case 2 :
            	    // ExprInterp.g:51:13: '/' e= atom
            	    {
            	    match(input,14,FOLLOW_14_in_multExpr484); 

            	    pushFollow(FOLLOW_atom_in_multExpr488);
            	    e=atom();

            	    state._fsp--;


            	    value /= e;

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "multExpr"



    // $ANTLR start "atom"
    // ExprInterp.g:55:1: atom returns [int value] : ( INT | ID | '(' e= nexpr ')' | '-' e= atom );
    public final int atom() throws RecognitionException {
        int value = 0;


        Token INT4=null;
        Token ID5=null;
        int e =0;


        try {
            // ExprInterp.g:56:5: ( INT | ID | '(' e= nexpr ')' | '-' e= atom )
            int alt8=4;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt8=1;
                }
                break;
            case ID:
                {
                alt8=2;
                }
                break;
            case 9:
                {
                alt8=3;
                }
                break;
            case 13:
                {
                alt8=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;

            }

            switch (alt8) {
                case 1 :
                    // ExprInterp.g:56:9: INT
                    {
                    INT4=(Token)match(input,INT,FOLLOW_INT_in_atom525); 

                    value = Integer.parseInt((INT4!=null?INT4.getText():null));

                    }
                    break;
                case 2 :
                    // ExprInterp.g:57:9: ID
                    {
                    ID5=(Token)match(input,ID,FOLLOW_ID_in_atom537); 


                            Integer v = (Integer)memory.get((ID5!=null?ID5.getText():null));
                            if ( v!=null ) value = v.intValue();
                            else System.err.println("undefined variable "+(ID5!=null?ID5.getText():null));
                            

                    }
                    break;
                case 3 :
                    // ExprInterp.g:63:9: '(' e= nexpr ')'
                    {
                    match(input,9,FOLLOW_9_in_atom557); 

                    pushFollow(FOLLOW_nexpr_in_atom561);
                    e=nexpr();

                    state._fsp--;


                    match(input,10,FOLLOW_10_in_atom563); 

                    value = e;

                    }
                    break;
                case 4 :
                    // ExprInterp.g:64:9: '-' e= atom
                    {
                    match(input,13,FOLLOW_13_in_atom575); 

                    pushFollow(FOLLOW_atom_in_atom579);
                    e=atom();

                    state._fsp--;


                    value = e*(-1);

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "atom"

    // Delegated rules


 

    public static final BitSet FOLLOW_stat_in_prog23 = new BitSet(new long[]{0x0000000000402272L});
    public static final BitSet FOLLOW_nnexpr_in_stat50 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_NEWLINE_in_stat52 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stat64 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_stat66 = new BitSet(new long[]{0x0000000000402230L});
    public static final BitSet FOLLOW_nnexpr_in_stat68 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_NEWLINE_in_stat70 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nexpr_in_nnexpr115 = new BitSet(new long[]{0x0000000000A00002L});
    public static final BitSet FOLLOW_21_in_nnexpr131 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_nexpr_in_nnexpr135 = new BitSet(new long[]{0x0000000000A00002L});
    public static final BitSet FOLLOW_23_in_nnexpr151 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_nexpr_in_nnexpr155 = new BitSet(new long[]{0x0000000000A00002L});
    public static final BitSet FOLLOW_22_in_nnexpr178 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_nexpr_in_nnexpr182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_nexpr210 = new BitSet(new long[]{0x00000000001D8102L});
    public static final BitSet FOLLOW_19_in_nexpr226 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_expr_in_nexpr230 = new BitSet(new long[]{0x00000000001D8102L});
    public static final BitSet FOLLOW_15_in_nexpr246 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_expr_in_nexpr250 = new BitSet(new long[]{0x00000000001D8102L});
    public static final BitSet FOLLOW_18_in_nexpr266 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_expr_in_nexpr270 = new BitSet(new long[]{0x00000000001D8102L});
    public static final BitSet FOLLOW_8_in_nexpr286 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_expr_in_nexpr290 = new BitSet(new long[]{0x00000000001D8102L});
    public static final BitSet FOLLOW_20_in_nexpr306 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_expr_in_nexpr310 = new BitSet(new long[]{0x00000000001D8102L});
    public static final BitSet FOLLOW_16_in_nexpr326 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_expr_in_nexpr330 = new BitSet(new long[]{0x00000000001D8102L});
    public static final BitSet FOLLOW_multExpr_in_expr369 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_12_in_expr385 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_multExpr_in_expr389 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_13_in_expr405 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_multExpr_in_expr409 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_atom_in_multExpr447 = new BitSet(new long[]{0x0000000000004802L});
    public static final BitSet FOLLOW_11_in_multExpr464 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_atom_in_multExpr468 = new BitSet(new long[]{0x0000000000004802L});
    public static final BitSet FOLLOW_14_in_multExpr484 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_atom_in_multExpr488 = new BitSet(new long[]{0x0000000000004802L});
    public static final BitSet FOLLOW_INT_in_atom525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_9_in_atom557 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_nexpr_in_atom561 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_atom563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_13_in_atom575 = new BitSet(new long[]{0x0000000000002230L});
    public static final BitSet FOLLOW_atom_in_atom579 = new BitSet(new long[]{0x0000000000000002L});

}