package com.umg.programacioniiiproyectoiii.model;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Armectic {
    public static double calculate(Double num1, Double num2, String operating) {
        switch (operating) {
            case "+" -> {
                return num1 + num2;
            }
            case "-" -> {
                return num1 - num2;
            }
            case "*" -> {
                return num1 * num2;
            }
            case "/" -> {
                return num1 / num2;
            }
            case "^" -> {
                return Math.pow(num1, num2);
            }

            default ->
                throw new AssertionError();
        }
    };
    
    /**
     * @param c - caracter
     * @return true si es un character del alfaneto . false si no lo es
     */
    public static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * @param str - numero en texto
     * @return true si es un numero . false si no lo es
     */
    public static boolean isNumeric(String str) {
        try {
            Double.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param c caracter a evaluar
     * @return retorna la jeracquia de un operador o operando , en tal caso de
     * no ser un signo matematico retorna -1
     */
    public static int getHierarchy(String c) {
        return switch (c) {
            case "^" ->
                3;
            case "/", "*" ->
                2;
            case "+", "-" ->
                1;
            default ->
                -1;
        };
    }

    /**
     * @param c caracter a evaluar
     * @return retorna la jeracquia de un operador o operando , en tal caso de
     * no ser un signo matematico retorna -1
     */
    public static int getHierarchy(char c) {
        return switch (c) {
            case '^' ->
                3;
            case '/', '*' ->
                2;
            case '+', '-' ->
                1;
            default ->
                -1;
        };
    }

    /**
     * @param s operacion matematica
     * @return String como notacion polaca
     */
    public static String[] operationToPostfix(String s) {
        String[] infixOperation = Armectic.operationToArray(s);
        Stack<String> postfixOperation = new Stack<>();
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < infixOperation.length; i++) {
            String c = infixOperation[i];

            // si el caracter es una variable o un numero agregarlo al texto resultado
            if (isAlpha(c.charAt(0)) || isNumeric(c)) {
                postfixOperation.push(c);
            } // si el caracter es un ‘(‘, colocarlo en el stack.
            else if (c.charAt(0) == '(') {
                stack.push(c);
            } // si el caracter es un ‘)’, sacar de la pila un character y colocarlo en el texto resultado
            // hasta encontrar un ‘(‘
            else if (c.charAt(0) == ')') {
                while (!stack.isEmpty() && stack.peek().charAt(0) != '(') {
                    postfixOperation.push(stack.pop());
                }
                stack.pop();
            } // si el caracter es un operando
            else {
                while (!stack.isEmpty()
                        && (getHierarchy(infixOperation[i]) <= getHierarchy(stack.peek()))
                        && (infixOperation[i] != "^")) {
                    postfixOperation.push(stack.pop());

                }
                stack.push(c);
            }
        }

        // Pop all the remaining elements from the stack
        while (!stack.isEmpty()) {
            postfixOperation.push(stack.pop());

        }

        return (String[]) postfixOperation.toArray(String[]::new);
    }

    /**
     *
     * @param operation operacion en texto para separar los caracteres de los
     * digitos
     * @return lista de operadores, digitos y variables
     */
    public static String[] operationToArray(String operation) {
        //(?<=[-+*/^]) el previo caracter es un operador
        //(?=[-+*/^]) el siguiente caracter no es un operador
        //(?<=[a-zA-z]) el previo caracter es una variable
        //(?=[a-zA-z]) el siguiente caracter no es una variable

        return operation.trim().split("(?<=[-+*/^])|(?=[-+*/^])|(?=[a-zA-z])|(?<=[a-zA-z])|(?<=[()])|(?=[()])");
    }

    /**
     *
     * @param operation operacion en texto a evaluar
     * @return nulo si la operacion no tiene errrores
     */
    static public String evalOperation(String operation) {
        Matcher verificator;

        // valida que haya la mis cantidad de "(" y de ")"
        if ((operation.length() - operation.replace("(", "").length())
                != operation.length() - operation.replace(")", "").length()) {
            return "***Hay un un parentensis incompleto";
        }

        // valida que dentro de la operacion solo haya numeros, letras,signos operacio=
        if (!operation.matches("^[a-zA-Z0-9()+*\\-/^]*$")) {
            return "***has ingresado un caracter no operable";
        }

        // valida que la operacion no empieze ni termine con signos
        verificator = Pattern.compile("^[-+*/]|[-+*/]$").matcher(operation);

        if (verificator.find()) {
            return "***las operaciones no pueden terminar ni comenzar en signo";
        }

        // valida que no haya letras o numeros a la par de letra
        verificator = Pattern.compile("(?<=[a-zA-Z0-9])[a-zA-Z]|(?<=[a-zA-Z])[a-zA-Z0-9]").matcher(operation);

        if (verificator.find()) {
            return "***no puedes poner variables a par de otras variables";
        }

        // validad que antes de abri un parentesis haya un signo seguido de una variable
        // y al cerrar antes no haya un signo y despues haya un signo
        verificator = Pattern.compile("(?<=[(])[-+*/^]|(?<=[a-zA-Z0-9])[(]|(?<=[)])[a-zA-Z0-9]|(?<=[-+*/^])[)]").matcher(operation);

        if (verificator.find()) {
            return "***estas mal usando los parentesis";
        }

        return null;

    }

    public static String getEvaluatedOperation(String operation, Map<String, Double> variables) {
        String text = "";
        String[] splitOperation = Armectic.operationToArray(operation);

        for (String str : splitOperation) {
            if (Armectic.isAlpha(str.charAt(0))) {
                text += String.valueOf(variables.get(str.toUpperCase()));
            } else {
                text += str;
            }
        }

        return text;
    }
}
