import java.util.*;

public class main {
    public static void main(String[] args) {
        String input = "(2*3)+1";
        Calculator c = new Calculator();
        System.out.println(c.evaluate("4"));
    }
}

class Calculator{
    Stack<Double> number = new Stack<Double>();
    Stack<Character> op = new Stack<Character>();


    public double evaluate(String input){
        for(int i = 0; i < input.length(); i++) {
            //if its a blank space
            if (input.charAt(i) == ' ') continue;
            //if its a (
            else if(input.charAt(i)=='(') op.push(input.charAt(i));
            //if its a number
            else if((input.charAt(i)>='0'&&input.charAt(i)<='9')||input.charAt(i)=='.'){
                int j = i;
                while((input.charAt(i)>='0'&&input.charAt(i)<='9')||input.charAt(i)=='.'){
                    j++;
                }
                number.push(Double.parseDouble(input.substring(i,j++)));
            }
            //if its a )
            else if(input.charAt(i)==')'){
                while(op.size()!=0 && op.get(-1)!='('){
                    double n2 = number.pop();
                    double n1 = number.pop();
                    char op = this.op.pop();
                    number.push(calc(n1,n2,op));
                }
                op.pop();
            }
            //if its another operation
            else{
                while(op.size()!=0 && precedence(op.peek(),input.charAt(i))){
                    double n2 = number.pop();
                    double n1 = number.pop();
                    char op = this.op.pop();
                    number.push(calc(n1,n2,op));
                }
                op.push(input.charAt(i));
            }
        }
        while(op.size()!=0){
            double n2 = number.pop();
            double n1 = number.pop();
            char op = this.op.pop();
            number.push(calc(n1,n2,op));
        }
        return number.peek();
    }

    public double calc(double num1, double num2, char op){
        switch(op){
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if(num2 == 0){
                    throw new UnsupportedOperationException("you cannot divide by zero");
                }
                return num1 / num2;
        }
        return 0;
    }

    public boolean precedence(char op1, char op2){
        if((op1=='*'||op1=='/') && (op2=='+'||op2=='-')) return true;
        return false;
    }
}















class Eval{
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            //        | functionName `(` expression `)` | functionName factor
            //        | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers    checking for decimal or just normal nmber
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                    } else {
                        x = parseFactor();
                    }
                    if (func.equals("sqrt")) x = x*x;
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

}
