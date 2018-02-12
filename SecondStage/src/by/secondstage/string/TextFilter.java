package by.secondstage.string;

/* Необходимо реализовать консольную программу, которая бы фильтровала поток текстовой информации,
 * подаваемой на вход, и на выходе показывала лишь те строчки, которые содержат слово,
 * переданное программе на вход в качестве аргумента (ввод всех строк осуществляется последовательно,
 * одна за другой, окончание ввода обозначается вводом пустой строки).
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TextFilter {
	private static ArrayList<String> textData = new ArrayList<String>();
	
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Please, check programm arguments.");
			return;
		}
		
		inputText();
		
		System.out.println("Filter by values: " + Arrays.asList(args));

		System.out.println("Output data:");
		for (String strToCheck: textData) {
			if (checkString(strToCheck, args)) {
				System.out.println(strToCheck);
			}
		}

		System.out.println("Exit...");

	}
	
	//function for input text string
	public static void inputText () {	
		String inputStr;
		@SuppressWarnings("resource")
		Scanner keyIn = new Scanner(System.in);
		System.out.println("Enter strings... (for exit: enter empty line)");
		
		while(true) {
			inputStr = keyIn.nextLine();
			if(inputStr.equals("")) {
				break;
			}
			textData.add(inputStr);
		}
		
	}
	
	//function for check String
	public static boolean checkString (String strToCheck, String[] args) {
		
		Pattern regToCheck;
		
		for(int i = 0; i < args.length; i++) {
			try {
				regToCheck = Pattern.compile(args[i]);
				//Удаляем символы пунктцации и разбиваем строку на слова по пробелам
				String[] strToWord = strToCheck.replaceAll("\\pP", "").split(" ");
				for(int j = 0; j < strToWord.length; j++) {
					if (regToCheck.matcher(strToWord[j]).matches()) {
						return true;
					}
				}
			} catch (PatternSyntaxException e) {
				//Если получили исключение (невалидное регулярное выражение)
				//проверям есть ли искомое значение в строке
				if(strToCheck.contains(args[i])) {
					return true;
				}
			}		
		}
		
		return false;
	}

}