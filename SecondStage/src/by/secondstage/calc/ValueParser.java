package by.secondstage.calc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class ValueParser {
	
	//Для проверки корректных символов
	private final String VALID_SYMBOL = "[\\d+|\\.|\\+|\\-|\\*|\\/|\\^|\\(|\\)]+";
	//для проверки дублирования символов операций
	private final String WRONG_TWO_OPERATOR = "([\\+|\\*|\\/|\\^]){2,}"; //от двух операций исключая '-' (унарный)
	private final String WRONG_OPERATOR = "([\\+|\\*|\\/|\\^|\\-]){3,}";
	//Для проверки корректности чисел
	private final String WRONG_NUMBER = "[0-9]+\\s+[0-9]+";
	private final String WRONG_DECIMAL = "\\.{2,}";
	//Для проверка знака перед '('
	private final String WRONG_OPERATOR_BEFORE_BRACKET = "[0-9]\\(";
	//Для проверка знака после ')'	
	private final String WRONG_OPERATOR_AFTER_BRACKET = "\\)[0-9]";
	
	//возможные операторы
	private final String OPERATOR = "^*/+-";
	//разделители (операторы и скобки)
	private final String SEPARATOR = " ()^*/+-";
	private String errorMessage = "";
	
	//Reverse Polish Notation
	public List<String> parseRPN(String valueIn) {
		
		//меняем запятые на точки для дробных чисел, удаляем пробелы в начале и конце строки
		valueIn = valueIn.replace(",",".").trim();

		if (!isCorrect(valueIn)) {
			return null;
		}
		
		List<String> valueOut = new ArrayList<String>();
		Deque<String> stackOperator = new ArrayDeque<String>();
		StringTokenizer valueToken = new StringTokenizer(valueIn, SEPARATOR, true);
		
		String currentToken = "";
		String previousToken = ""; //Храним предыдущее значение для унарного минуса
		
		while (valueToken.hasMoreTokens()) {
			currentToken = valueToken.nextToken();
			//Если после оператора больше нет значений - ошибка
			if (isOperator(currentToken) && !valueToken.hasMoreTokens()) {
				errorMessage = "ERROR! Not valid expression!";
				return null;
			}
			//Пропускаем символы пробела
			if (currentToken.equals(" ")) {
				continue;
			}
			if (isSeparator(currentToken)) {
				if (currentToken.equals("(")) {
					stackOperator.push(currentToken);
				} else { 
					if (currentToken.equals(")")) {
						while (!stackOperator.peek().equals("(")) {
							valueOut.add(stackOperator.pop());
							if(stackOperator.isEmpty()) {
								errorMessage = "ERROR! Brackets are not placed correctly.";
								return null;
							}
						}
						stackOperator.pop();
					} else {
						//проверка на унарный минус
						if(currentToken.equals("-") && 
								(previousToken.equals("") || (isSeparator(previousToken) && !previousToken.equals(")")))) {
							currentToken = "neg";
						}
						while (!stackOperator.isEmpty() &&
							   (operationPriority(currentToken) <= operationPriority(stackOperator.peek()))) {
							valueOut.add(stackOperator.pop());
						}
						stackOperator.push(currentToken);
					}
				}
			} else {
				valueOut.add(currentToken);
			}
			previousToken = currentToken;
		}
		
		while (!stackOperator.isEmpty()) {
			if (isOperator(stackOperator.peek())) {
				valueOut.add(stackOperator.pop());
			} else {
				errorMessage = "ERROR! Brackets are not placed correctly.";
				return null;
			}
		}
		return valueOut;
	}
	
	//Проверка на валидные символы и пробелы в числах
	private boolean isCorrect(String valueIn) {
		Pattern checkExpression = Pattern.compile(VALID_SYMBOL);
		if(!checkExpression.matcher(valueIn.replace(" ", "")).matches()) {
			errorMessage = "Not valid data! Illegal symbols.";
			return false;
		}		

		checkExpression = Pattern.compile(WRONG_NUMBER);
		if(checkExpression.matcher(valueIn.replace(".", "")).find()) {
			errorMessage = "Not valid data! Wrong number.";
			return false;
		}
		
		checkExpression = Pattern.compile(WRONG_DECIMAL);
		if(checkExpression.matcher(valueIn.replace(" ", "")).find()) {
			errorMessage = "Not valid data! Wrong decimal number.";
			return false;
		}
		
		checkExpression = Pattern.compile(WRONG_TWO_OPERATOR);
		if(checkExpression.matcher(valueIn.replace(" ", "")).find()) {
			errorMessage = "Not valid data! Wrong operator";
			return false;
		}
		checkExpression = Pattern.compile(WRONG_OPERATOR);
		if(checkExpression.matcher(valueIn.replace(" ", "")).find()) {
			errorMessage = "Not valid data! Wrong operator";
			return false;
		}
		
		checkExpression = Pattern.compile(WRONG_OPERATOR_BEFORE_BRACKET);
		if(checkExpression.matcher(valueIn.replace(" ", "")).find()) {
			errorMessage = "Not valid data! No operator before '('.";
			return false;
		}
		
		checkExpression = Pattern.compile(WRONG_OPERATOR_AFTER_BRACKET);
		if(checkExpression.matcher(valueIn.replace(" ", "")).find()) {
			errorMessage = "Not valid data! No operator after ')'.";
			return false;
		}
		return true;
	}
	
	//Проверка на оператор
	private boolean isOperator(String node) {
		if (node.equals("neg")) {
			return true;
		}
		return OPERATOR.contains(node);
	}
	
	//Проверка на разделитель (оператор или скобки)
	private boolean isSeparator(String node) {
		return SEPARATOR.contains(node);
	}
	
	//Приоритет операций
	private int operationPriority(String node) {
		switch (node) {
			case "(": return 1;
			case "+":
			case "-": return 2;
			case "*":
			case "/": return 3;
			case "^": return 4;
			default: return 5;
		}
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

}
