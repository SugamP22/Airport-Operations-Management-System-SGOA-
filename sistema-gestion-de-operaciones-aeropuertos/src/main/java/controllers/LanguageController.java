package controllers;

import java.util.Scanner;

import utils.BoxedMessageUtil;
import utils.LanguageUtil;

public class LanguageController {
	public void updateLanguage() {
		Scanner sc = new Scanner(System.in);
		BoxedMessageUtil.boxWithOutEvenSpacing(LanguageUtil.get("ui.lang.menu.title"), "=");
		System.out.println(LanguageUtil.get("ui.lang.option.en"));
		System.out.println(LanguageUtil.get("ui.lang.option.es"));
		BoxedMessageUtil.horizontalRow("-");
		System.out.print(LanguageUtil.get("input.user"));
		int numero = sc.nextInt();
		if (numero == 1) {
			LanguageUtil.setLanguage("en");
		} else {
			LanguageUtil.setLanguage("es");

		}
		sc.close();

	}
}
