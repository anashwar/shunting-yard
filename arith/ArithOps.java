package co.test.arith;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Stack;

/**
 * Created by Anashwar Prasad on 2/27/2017.
 */
public class ArithOps {

    public static void main(String[] args) {
        String exp = "((4+(5*(2^2)*2))/11)-2";
        ArithOps ops = new ArithOps();
        System.out.println("Script output - ");
        ops.evalScript(exp);
        System.out.println("Function out");
        ops.eval(exp);
    }


    public void evalScript(String a){
        try {
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("js");
            System.out.println(engine.eval(a));
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public void eval(String a){
        Stack<Integer> value = new Stack<Integer>();
        Stack<Character> oper = new Stack<Character>();
        char[] exp = a.toCharArray();
        for (int i = 0; i < exp.length; i++){
            if (exp[i] == '(' || exp[i] == '[' || exp[i] == '{'){
                oper.push(exp[i]);
            }
            else if(exp[i] >= '0' && exp[i] <= '9'){
                StringBuffer num = new StringBuffer();
                while(i < exp.length && exp[i] >= '0' && exp[i] <= '9'){
                    num.append(exp[i++]);
                }
                i--;
                Integer val = Integer.parseInt(num.toString());
                value.push(val);
            }

            else if(exp[i] == '+' || exp[i] == '-' || exp[i] == '*' || exp[i] == '/' || exp[i] == '^'){
                while(!oper.empty() && isOfHigherPrcedence(exp[i], oper.peek())){
                    Integer val = compute(oper.pop(), value.pop(), value.pop());
                    value.push(val);
                }
                oper.push(exp[i]);
            }

            else if(exp[i] == ')'){
                while (oper.peek() != '('){
                    Integer val = compute(oper.pop(),value.pop(), value.pop());
                    value.push(val);
                }
                oper.pop();
            }
            else if(exp[i] == ']'){
                while (oper.peek() != '['){
                    Integer val = compute(oper.pop(),value.pop(), value.pop());
                    value.push(val);
                }
                oper.pop();
            }

            else if(exp[i] == '}'){
                while (oper.peek() != '{'){
                    Integer val = compute(oper.pop(),value.pop(), value.pop());
                    value.push(val);
                }
                oper.pop();
                i++;
            }

        }
        while(!oper.empty()){
            Integer val = compute(oper.pop(), value.pop(), value.pop());
            value.push(val);
        }
        System.out.println(value.pop());
    }

    public boolean isOfHigherPrcedence(char a, char b){
        if(b == '(' || b == ')' || b == '[' || b == ']' || b == '{' || b == '}')
            return false;
        else if((a == '*' || a == '/') && (b == '+' || b == '-'))
            return  false;
        else if(a == '^')
            return false;
        else
            return true;
    }

    public Integer compute(char x, Integer b, Integer a){
        switch (x) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            case '^':
                return new Double(Math.pow(new Double(a),new Double(b))).intValue();
            default:
                return 0;
        }

    }
}
