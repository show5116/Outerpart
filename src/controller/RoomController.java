package controller;

import util.*;
import view.RoomRegister;

public class RoomController {
	private RoomController() {}
	private static RoomController instance;
	public static RoomController getInstance(){
		if(instance == null){
			instance = new RoomController();
		}
		return instance;
	}
	
	private ScannerBuffer scanner = ScannerBuffer.getInstance();
	private RoomRegister roomRegister = RoomRegister.getInstance();
	
	public void roomInformation() {
		while(true) {
			System.out.println("\n\n 1.방 등록 | 2.방 삭제 | 3.목록 조회 | 4.뒤로가기\n");
			System.out.print(" ✔ 번호를 입력해 주세요 › ");
			int input = scanner.nextInt();
			switch (input) {
				case 1: roomRegister.roomInsert(); break;
				case 2: roomRegister.roomDelete(); break;
				case 3: roomRegister.roomSearch(); break;
				case 4: return;
				default: System.out.println("\n [ 잘못 입력하였습니다 ]\n");
			}
		}
	}
}