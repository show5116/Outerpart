package controller;

import view.LoginService;

public class LoginController {
	public static void main(String[] args) {
		new LoginController().start();
	}
	
	private LoginService loginservice = LoginService.getInstance();
	
	public void start() {
		int view = 0;
		
		while (true) {
			switch (view) {
				case 0: view = loginservice.main(); break;
				case 1: view = loginservice.login(); break;
				case 2:	view = loginservice.signUp(); break;
				case 3: view = loginservice.idFound(); break;
				case 4: view = loginservice.temporary(); break;
				case 5: loginservice.exit(); 
				default:
					System.out.println("\n [ 잘못 입력하였습니다 ]\n");
					view = 0;
					break;
			}	
		}
	}
}