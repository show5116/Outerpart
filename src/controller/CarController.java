package controller;

import util.ScannerBuffer;
import view.CarRegister;

public class CarController {
	private CarController() {}
	private static CarController instance;
	public static CarController getInstance(){
		if(instance == null){
			instance = new CarController();
		}
		return instance;
	}
	
	private ScannerBuffer scanner = ScannerBuffer.getInstance();
	private CarRegister carRegister = CarRegister.getInstance();
	
	public void carInformation() {
		while (true) {
			System.out.println("\n\n 1.렌트카 등록 | 2.렌트카 삭제 | 3.목록 조회 | 4.뒤로 가기\n");
			System.err.print(" ✔ 번호를 입력해 주세요 › ");
			int input = scanner.nextInt();
			switch (input) {
				case 1: carRegister.carInsert(); break;
				case 2: carRegister.carDelete(); break;
				case 3: carRegister.carSearch(); break;
				case 4: return;
				default: System.out.println("\n [ 잘못 입력하였습니다 ]\n");
			}
		}
	}
}