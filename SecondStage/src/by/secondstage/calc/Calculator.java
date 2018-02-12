package by.secondstage.calc;

/* Необходимо реализовать консольное приложение-калькулятор с поддержкой функционала поиска
 * суммы/ разности/ произведения/ частного/ возведения в целую степень.
 * Выводить корректное сообщения об ошибке в случае невалидных параметров (например, деление на 0).
 * Реализовать поддержку нескольких операций одновременно и приоритета операций с учётом скобок
 * ( () >> ^ >> * / >> ±)
*/

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class Calculator {
	private static int DECIMAL_ORDER = 10; //количество знаков после запятой
	private static ValueParser valueParser = new ValueParser();
	
	public static void main(String[] args) {
		String valueIn = "";
		System.out.println("For exit enter 'exit'");
		while (!valueIn.equals("exit")) {
			valueIn = inputValue();
			if(valueIn.equals("exit")) {
				continue;
			}
			
			//Check and parse input data
			List<String> expression = valueParser.parseRPN(valueIn);
			if(expression == null) {
				System.out.println("Data check: " + valueParser.getErrorMessage() + "\n");
			} else {
			//	System.out.println("Expression: " + expression);
				System.out.println("Result: " + calculate(expression) + "\n");
			}
		}
		
		System.out.println("Exit...");
	}

	public static String inputValue () {
		@SuppressWarnings("resource")
		Scanner keyIn = new Scanner(System.in);
		System.out.print("Enter value: ");	
		return keyIn.nextLine();
	}
	
	public static String calculate(List<String> expression) {
		Deque<BigDecimal> result = new ArrayDeque<BigDecimal>();
		BigDecimal tempValue;
		BigDecimal powNumber;
		for(String value: expression) {
			switch (value) {
			case "+": result.push(result.pop().add(result.pop())); break;
			case "-": result.push(result.pop().negate().add(result.pop())); break;
			case "*": result.push(result.pop().multiply(result.pop()));break;
			case "/": tempValue = result.pop(); 
					  if(tempValue.equals(BigDecimal.ZERO)) {
						  return "ERROR! Division by ZERO!";
					  }
					  result.push(result.pop().divide(tempValue, DECIMAL_ORDER, RoundingMode.HALF_UP));
					  break;
			case "^": tempValue = result.pop();
					  powNumber = result.pop();
					  if(powNumber.equals(BigDecimal.ZERO) && tempValue.intValue() < 0) {
						  return "ERROR! ZERO in negatibe power!";
					  }
					  result.push(powNumber.pow(tempValue.intValue(), MathContext.DECIMAL64)); break;
			case "neg": result.push(result.pop().negate()); break;
			default: result.push(new BigDecimal(value)); break;
			}
		}
		return result.pop().toString();
	}
}
