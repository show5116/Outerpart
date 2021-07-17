package view;

import java.util.ArrayList;
import java.util.HashMap;

import dao.RoomDAO;
import util.ScannerBuffer;
import vo.RoomVO;

public class RoomRegister {
	private RoomRegister() {}
	private static RoomRegister instance;
	public static RoomRegister getInstance() {
		if (instance == null) {
			instance = new RoomRegister();
		}
		return instance;
	}
	
	private ScannerBuffer scanner = ScannerBuffer.getInstance(); 
	private RoomDAO roomDao = RoomDAO.getInstance();
	
	public void roomInformation() {
		System.out.println("1.방 등록 2.방 삭제 3. 뒤로가기");
		System.out.print("번호를 입력해주세요> ");
	}
	
	public void reRoomInformation() {
		System.out.println("다시 입력해주세요.");
	}
	
	public void roomInsert() {
		System.out.print("방 번호> ");
		int roomNumber = scanner.nextInt();
		System.out.print("Host 아이디> ");
		String userId = scanner.next();
		System.out.print("방 이름> ");
		String roomName = scanner.next();
		System.out.print("수용인원> ");
		int maxCapacity = scanner.nextInt();
		System.out.print("방개수> ");
		int roomCount = scanner.nextInt();
		System.out.print("침대개수> ");
		int bedCount = scanner.nextInt();
		System.out.print("가격> ");
		int roomPrice = scanner.nextInt();
		System.out.print("지역> ");
		String city = scanner.next();
		System.out.print("설명> ");
		String explanation = scanner.next();
		if (roomDao.insertRoom(new RoomVO(roomNumber, userId, roomName, maxCapacity, roomCount, bedCount, roomPrice, city, explanation)) == 0) {
			System.out.println("등록에 실패했습니다.");
		} else {
			System.out.println("등록 성공");
		}
	}
	
	public void roomDelete() {
		System.out.print("방 번호> ");
		int roomNumber = scanner.nextInt();
		
		if (roomDao.deleteRoom(new RoomVO(roomNumber)) == 1) {
			System.out.println("삭제되었습니다.");
		} else {
			System.out.println("존재하지 않는 방입니다.");
		}
	}

	public void roomSearch() {
		ArrayList<HashMap<String, Object>> list = roomDao.select(LoginService.loginId);
		int size = list.size();
		String[] key = {"ROOM_NUMBER","ROOM_NAME","MAX_CAPACITY","ROOM_COUNT","BED_COUNT","ROOM_PRICE","CITY","EXPLANATION"};
		System.out.println("번호\t이름\t수용인원\t방 개수\t침대 개수\t가격\t지역\t설명");
		for(int i=0; i<size;i++) {
			for(int j=0; j<key.length; j++) {
				System.out.print(list.get(i).get(key[j])+"\t");
			}
			System.out.println();
		}
	}
}
