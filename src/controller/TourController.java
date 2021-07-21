package controller;

import util.*;
import view.TourRegister;

public class TourController {
	private TourController() {}
	private static TourController instance;
	public static TourController getInstance(){
		if(instance == null){
			instance = new TourController();
		}
		return instance;
	}
	
	private ScannerBuffer scanner = ScannerBuffer.getInstance();
	private TourRegister tourRegister = TourRegister.getInstance();
	
	public void tourInformation() {
		while(true) {
			System.out.println("\n\n 1.관광명소 등록 | 2.관광명소 삭제 | 3.목록 조회 | 4.뒤로 가기\n");
			System.out.print(" ✔ 번호를 입력해 주세요 › ");
			int input = scanner.nextInt();
			switch (input) {
				case 1: tourRegister.tourInsert(); break;
				case 2: tourRegister.tourDelete(); break;
				case 3: tourRegister.tourSearch(); break;
				case 4: return;
				default: System.out.println("\n [ 잘못 입력하였습니다 ]\n");
			}
		}
	}
}