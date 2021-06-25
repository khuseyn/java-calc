import java.util.*;

public class Program {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите вычисление: ");
        String operation = in.nextLine();
        if (!CalcParser.isValid(operation)) throw new Exception("Invalid input!!!");
        boolean isRoman = RomanConverter.isRoman(operation.split(" ")[0]);
        int result = CalcParser.calculateString(operation);

        if (isRoman) {
            System.out.println(RomanConverter.convertToRoman(result));
        } else {
            System.out.println(result);
        }
    }
}

class CalcParser {
    public static int calculateString(String operation) {
        String[] operationParts = operation.split(" ");
        int a = 0;
        int b = 0;

        if (RomanConverter.isRoman(operationParts[0])) {
            a = RomanConverter.convertToArabic(operationParts[0]);
            b = RomanConverter.convertToArabic(operationParts[2]);
        } else {
            a = Integer.parseInt(operationParts[0]);
            b = Integer.parseInt(operationParts[2]);
        }

        return parseOperation(operationParts[1], a, b);
    }
    
    private static int parseOperation(String operation, int a, int b) {
        switch (operation) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                // TODO: throw exeption
                return 0;
        }
    }

    public static boolean isValid(String operation) {
        String[] operArr = operation.split(" ");
        // TODO: стороны должны быть разделены пробелами
        if (operArr.length != 3) {
            System.out.printf("стороны должны быть разделены пробелами");
            return false;
        }
        // TODO: не может принимать одновременно разные форматы
        if (
            (!RomanConverter.isRoman(operArr[0]) && RomanConverter.isRoman(operArr[2])) ||
            (RomanConverter.isRoman(operArr[0]) && !RomanConverter.isRoman(operArr[2]))
            ) {
            System.out.printf("не может принимать одновременно разные форматы");
            return false;
        }
        // TODO: не может принимать ничего кроме цифр
        if (!RomanConverter.isRoman(operArr[0]) && !RomanConverter.isRoman(operArr[2])) {
            try {
                Integer.parseInt(operArr[0]);
                Integer.parseInt(operArr[2]);
            } catch (Exception e) {
                System.out.printf("не может принимать ничего кроме цифр");
                return false;
            }

            // TODO: не больше 10 и не меньше 1
            if (
                !(1 <= Integer.parseInt(operArr[0]) &&  Integer.parseInt(operArr[0]) <= 10) ||
                !(1 <= Integer.parseInt(operArr[2]) &&  Integer.parseInt(operArr[2]) <= 10)
                ) {
                    System.out.print("не больше 10 и не меньше 1 - arab  ");
                    return false;
                }
        } else if (RomanConverter.isRoman(operArr[0]) && RomanConverter.isRoman(operArr[2])) {
            int a = RomanConverter.convertToArabic(operArr[0]);
            int b = RomanConverter.convertToArabic(operArr[2]);
            if (!(1 <= a && a <= 10)) {
                System.out.printf("не больше 10 и не меньше 1");
                return false;
            }
            if (!(1 <= b && b <= 10)) {
                System.out.printf("не больше 10 и не меньше 1");
                return false;
            }
        }
        
        // TODO: не может принимать знаки кроме: + - * /
        if (!operArr[1].equals("+") && !operArr[1].equals("-") && !operArr[1].equals("*") && !operArr[1].equals("/")) {
            System.out.printf("принимает только основные арифметические знаки");
            return false;
        }

        return true;
    }
}

class RomanConverter {
    private static LinkedHashMap<String, Integer> ROMAN_DIGITS = new LinkedHashMap<String, Integer>()
    {{
        put("M", 1000);
        put("CM", 900);
        put("D", 500);
        put("CD", 400);
        put("C", 100);
        put("XC", 90);
        put("L", 50);
        put("XL", 40);
        put("X", 10);
        put("IX", 9);
        put("V", 5);
        put("IV", 4);
        put("I", 1);
    }};
    
    public static boolean isRoman(String digit) {
        return ROMAN_DIGITS.containsKey(digit.split("")[0]);
    }
    public static int convertToArabic(String digit) {
        int result = 0;
        String convertedDigits = digit
            .replaceAll("IV", "4")
            .replaceAll("IX", "9")
            .replaceAll("I", "1")
            .replaceAll("V", "5")
            .replaceAll("X", "55");

        for (int i = 0; i < convertedDigits.length(); i++) {
            result += Character.getNumericValue(convertedDigits.charAt(i));
        }

        return result;
    }
    public static String convertToRoman(int digit) {
        String res = "";
        for (Map.Entry<String, Integer> entry : ROMAN_DIGITS.entrySet()){
            int matches = digit/entry.getValue();
            res += repeat(entry.getKey(), matches);
            digit = digit % entry.getValue();
        }
        return res;
    }

    private static String repeat(String s, int n) {
        if(s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
