// $ANTLR 3.4 ExprInterp.g 2015-02-20 17:55:48

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class ExprInterpLexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public ExprInterpLexer() {} 
    public ExprInterpLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ExprInterpLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "ExprInterp.g"; }

    // $ANTLR start "T__8"
    public final void mT__8() throws RecognitionException {
        try {
            int _type = T__8;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:2:6: ( '!=' )
            // ExprInterp.g:2:8: '!='
            {
            match("!="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__8"

    // $ANTLR start "T__9"
    public final void mT__9() throws RecognitionException {
        try {
            int _type = T__9;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:3:6: ( '(' )
            // ExprInterp.g:3:8: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__9"

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:4:7: ( ')' )
            // ExprInterp.g:4:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:5:7: ( '*' )
            // ExprInterp.g:5:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:6:7: ( '+' )
            // ExprInterp.g:6:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:7:7: ( '-' )
            // ExprInterp.g:7:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:8:7: ( '/' )
            // ExprInterp.g:8:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:9:7: ( '<' )
            // ExprInterp.g:9:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:10:7: ( '<=' )
            // ExprInterp.g:10:9: '<='
            {
            match("<="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:11:7: ( '=' )
            // ExprInterp.g:11:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:12:7: ( '==' )
            // ExprInterp.g:12:9: '=='
            {
            match("=="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:13:7: ( '>' )
            // ExprInterp.g:13:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:14:7: ( '>=' )
            // ExprInterp.g:14:9: '>='
            {
            match(">="); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:15:7: ( 'AND' )
            // ExprInterp.g:15:9: 'AND'
            {
            match("AND"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:16:7: ( 'NOT' )
            // ExprInterp.g:16:9: 'NOT'
            {
            match("NOT"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:17:7: ( 'OR' )
            // ExprInterp.g:17:9: 'OR'
            {
            match("OR"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:68:5: ( ( 'a' .. 'z' | 'A' .. 'Z' )+ )
            // ExprInterp.g:68:9: ( 'a' .. 'z' | 'A' .. 'Z' )+
            {
            // ExprInterp.g:68:9: ( 'a' .. 'z' | 'A' .. 'Z' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= 'A' && LA1_0 <= 'Z')||(LA1_0 >= 'a' && LA1_0 <= 'z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // ExprInterp.g:
            	    {
            	    if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


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

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:69:5: ( ( '0' .. '9' )+ )
            // ExprInterp.g:69:9: ( '0' .. '9' )+
            {
            // ExprInterp.g:69:9: ( '0' .. '9' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0 >= '0' && LA2_0 <= '9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // ExprInterp.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:70:8: ( ( '\\r' )? '\\n' )
            // ExprInterp.g:70:9: ( '\\r' )? '\\n'
            {
            // ExprInterp.g:70:9: ( '\\r' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='\r') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ExprInterp.g:70:9: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }


            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // ExprInterp.g:71:5: ( ( ' ' | '\\t' )+ )
            // ExprInterp.g:71:9: ( ' ' | '\\t' )+
            {
            // ExprInterp.g:71:9: ( ' ' | '\\t' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='\t'||LA4_0==' ') ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ExprInterp.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            skip();

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // ExprInterp.g:1:8: ( T__8 | T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | ID | INT | NEWLINE | WS )
        int alt5=20;
        switch ( input.LA(1) ) {
        case '!':
            {
            alt5=1;
            }
            break;
        case '(':
            {
            alt5=2;
            }
            break;
        case ')':
            {
            alt5=3;
            }
            break;
        case '*':
            {
            alt5=4;
            }
            break;
        case '+':
            {
            alt5=5;
            }
            break;
        case '-':
            {
            alt5=6;
            }
            break;
        case '/':
            {
            alt5=7;
            }
            break;
        case '<':
            {
            int LA5_8 = input.LA(2);

            if ( (LA5_8=='=') ) {
                alt5=9;
            }
            else {
                alt5=8;
            }
            }
            break;
        case '=':
            {
            int LA5_9 = input.LA(2);

            if ( (LA5_9=='=') ) {
                alt5=11;
            }
            else {
                alt5=10;
            }
            }
            break;
        case '>':
            {
            int LA5_10 = input.LA(2);

            if ( (LA5_10=='=') ) {
                alt5=13;
            }
            else {
                alt5=12;
            }
            }
            break;
        case 'A':
            {
            int LA5_11 = input.LA(2);

            if ( (LA5_11=='N') ) {
                int LA5_24 = input.LA(3);

                if ( (LA5_24=='D') ) {
                    int LA5_27 = input.LA(4);

                    if ( ((LA5_27 >= 'A' && LA5_27 <= 'Z')||(LA5_27 >= 'a' && LA5_27 <= 'z')) ) {
                        alt5=17;
                    }
                    else {
                        alt5=14;
                    }
                }
                else {
                    alt5=17;
                }
            }
            else {
                alt5=17;
            }
            }
            break;
        case 'N':
            {
            int LA5_12 = input.LA(2);

            if ( (LA5_12=='O') ) {
                int LA5_25 = input.LA(3);

                if ( (LA5_25=='T') ) {
                    int LA5_28 = input.LA(4);

                    if ( ((LA5_28 >= 'A' && LA5_28 <= 'Z')||(LA5_28 >= 'a' && LA5_28 <= 'z')) ) {
                        alt5=17;
                    }
                    else {
                        alt5=15;
                    }
                }
                else {
                    alt5=17;
                }
            }
            else {
                alt5=17;
            }
            }
            break;
        case 'O':
            {
            int LA5_13 = input.LA(2);

            if ( (LA5_13=='R') ) {
                int LA5_26 = input.LA(3);

                if ( ((LA5_26 >= 'A' && LA5_26 <= 'Z')||(LA5_26 >= 'a' && LA5_26 <= 'z')) ) {
                    alt5=17;
                }
                else {
                    alt5=16;
                }
            }
            else {
                alt5=17;
            }
            }
            break;
        case 'B':
        case 'C':
        case 'D':
        case 'E':
        case 'F':
        case 'G':
        case 'H':
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'P':
        case 'Q':
        case 'R':
        case 'S':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        case 'Z':
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt5=17;
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt5=18;
            }
            break;
        case '\n':
        case '\r':
            {
            alt5=19;
            }
            break;
        case '\t':
        case ' ':
            {
            alt5=20;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 5, 0, input);

            throw nvae;

        }

        switch (alt5) {
            case 1 :
                // ExprInterp.g:1:10: T__8
                {
                mT__8(); 


                }
                break;
            case 2 :
                // ExprInterp.g:1:15: T__9
                {
                mT__9(); 


                }
                break;
            case 3 :
                // ExprInterp.g:1:20: T__10
                {
                mT__10(); 


                }
                break;
            case 4 :
                // ExprInterp.g:1:26: T__11
                {
                mT__11(); 


                }
                break;
            case 5 :
                // ExprInterp.g:1:32: T__12
                {
                mT__12(); 


                }
                break;
            case 6 :
                // ExprInterp.g:1:38: T__13
                {
                mT__13(); 


                }
                break;
            case 7 :
                // ExprInterp.g:1:44: T__14
                {
                mT__14(); 


                }
                break;
            case 8 :
                // ExprInterp.g:1:50: T__15
                {
                mT__15(); 


                }
                break;
            case 9 :
                // ExprInterp.g:1:56: T__16
                {
                mT__16(); 


                }
                break;
            case 10 :
                // ExprInterp.g:1:62: T__17
                {
                mT__17(); 


                }
                break;
            case 11 :
                // ExprInterp.g:1:68: T__18
                {
                mT__18(); 


                }
                break;
            case 12 :
                // ExprInterp.g:1:74: T__19
                {
                mT__19(); 


                }
                break;
            case 13 :
                // ExprInterp.g:1:80: T__20
                {
                mT__20(); 


                }
                break;
            case 14 :
                // ExprInterp.g:1:86: T__21
                {
                mT__21(); 


                }
                break;
            case 15 :
                // ExprInterp.g:1:92: T__22
                {
                mT__22(); 


                }
                break;
            case 16 :
                // ExprInterp.g:1:98: T__23
                {
                mT__23(); 


                }
                break;
            case 17 :
                // ExprInterp.g:1:104: ID
                {
                mID(); 


                }
                break;
            case 18 :
                // ExprInterp.g:1:107: INT
                {
                mINT(); 


                }
                break;
            case 19 :
                // ExprInterp.g:1:111: NEWLINE
                {
                mNEWLINE(); 


                }
                break;
            case 20 :
                // ExprInterp.g:1:119: WS
                {
                mWS(); 


                }
                break;

        }

    }


 

}