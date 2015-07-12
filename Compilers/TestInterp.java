import org.antlr.runtime.*;

////////////////////////////////////////////////////////////
//                                                        //
//                 ---------                ----------    //
//   System.in --> | lexer | --> tokens --> | parser |    //
//                 ---------                ----------    //
//                                                        //
////////////////////////////////////////////////////////////

public class TestInterp {
    
    public static void main(String[] args) throws Exception {
        
        // create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        
        // create a lexer that feeds off of input CharStream
        ExprInterpLexer lexer = new ExprInterpLexer(input);
        
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        // create a parser that feeds off the tokens buffer
        ExprInterpParser parser = new ExprInterpParser(tokens);
        
        // begin parsing at rule prog
        parser.prog();
    }
}
