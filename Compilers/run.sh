./antlr3.sh ExprInterp.g
javac -cp antlr-3.4-complete.jar ExprInterpLexer.java ExprInterpParser.java TestInterp.java
java -cp .:antlr-3.4-complete.jar TestInterp