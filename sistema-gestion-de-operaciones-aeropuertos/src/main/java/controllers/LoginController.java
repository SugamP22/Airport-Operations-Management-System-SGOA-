package controllers;

import utils.BoxedMessageUtil;
import utils.LanguageUtil;

public class LoginController {

	private static final LanguageController langaueController = new LanguageController();

	public void iniciar() {
		langaueController.updateLanguage();
		System.out.println();
		int loginOption = login();
		if (loginOption == 1) {

		} else {
			System.out.println(LanguageUtil.get("system.exit"));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.err.println("\nSystem Closed Successfully");
		}

	}

	private int login() {
		BoxedMessageUtil.boxWithEvenSpacing(LanguageUtil.get("ui.header"), "*");
		System.out.println(LanguageUtil.get("ui.login.option"));
		System.out.println(LanguageUtil.get("ui.exit.option"));
		BoxedMessageUtil.horizontalRow("-");
		System.out.println(LanguageUtil.get("input.user"));
		return 0;// i need to add a method in validation to handle exception
	}

}
