package view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import dao.*;
import util.ScannerBuffer;

public class Payment {
	private Payment() {}
	private static Payment instance;
	public static Payment getInstance(){
		if(instance == null){
			instance = new Payment();
		}
		return instance;
	}
	
	private PaymentDAO paydao = PaymentDAO.getInstance();
	private ScannerBuffer scanner = ScannerBuffer.getInstance();
	private DecimalFormat formatter = new DecimalFormat("###,###");
	
	public void paymentHistory() {
		ArrayList<HashMap<String, Object>> list = paydao.selectPayment(LoginService.loginId.getUserId());
		int size = list.size();
		System.out.println("\n\n < 번호 >\t\t< 수정날짜 >\t\t< 상태 >\t  < 가격 >\n");
		for(int i=0; i<size;i++) {
			System.out.print("    " + (i + 1) + "\t  ");
			System.out.print(list.get(i).get("PAY_DATE")+"\t\t ");
			System.out.print(list.get(i).get("STATE")+"\t  ");
			System.out.print(formatter.format(list.get(i).get("PRICE"))+"\n\n");
		}
		System.out.println("\n ✔ 뒤로 가시려면 0을,");
		System.out.print("   결제 내역을 수정하거나, 자세히 보려면 결제 번호를 입력해 주세요 › ");
		int input = scanner.nextInt();
		if(input==0) return;
		String cartNumber = list.get(input-1).get("CART_NUMBER") + "";
		String state = list.get(input-1).get("STATE")+"";
		paymentDetails(cartNumber);
		paymentStatement(cartNumber,state);
	}
	
	public void paymentDetails(String cartNumber) {
		ArrayList<HashMap<String, Object>> listRoom = RoomDAO.getInstance().roomDetail(cartNumber);
		ArrayList<HashMap<String, Object>> listCar = CarDAO.getInstance().carDetail(cartNumber);
		ArrayList<HashMap<String, Object>> listTour = TourDAO.getInstance().tourDetail(cartNumber);
		ArrayList<HashMap<String, Object>> listCart = PaymentDAO.getInstance().selectDetail(cartNumber);
		System.out.printf("\n\n 🤍 방 정보 🤍\n\n 방 이름 : %s\n\n 방 개수 : %s개\n\n 침대 개수 : %s개\n\n 방 인원 : %s명\n\n 방 설명 : %s\n\n"
				,listRoom.get(0).get("ROOM_NAME"),listRoom.get(0).get("ROOM_COUNT")
				,listRoom.get(0).get("BED_COUNT"),listRoom.get(0).get("MAX_CAPACITY"),listRoom.get(0).get("EXPLANATION"));
		System.out.printf("\n\n 🤍 차정보 🤍\n\n 차 종류 : %s\n\n 차 좌석 : %s인승\n\n",
				listCar.get(0).get("CAR_KIND"),listCar.get(0).get("CAR_SEATS"));
		System.out.printf("\n\n 🤍 투어정보 🤍\n\n 투어 이름 : %s\n\n 투어 시간 : %s시간 \n\n 투어 설명 : %s\n\n"
				,listTour.get(0).get("TOUR_NAME"),listTour.get(0).get("TOUR_TIME"),listTour.get(0).get("EXPLANATION"));
		System.out.printf("\n\n 🤍 지역 : %s 🤍\n\n 선택인원 : %s명\n\n 가격 : %s원\n\n 날짜 : %s ~ %s\n\n"
				,listCart.get(0).get("CITY"),listCart.get(0).get("PEOPLE"),formatter.format(listCart.get(0).get("PRICE"))
				,(listCart.get(0).get("START_DATE")+"").substring(0,10),(listCart.get(0).get("END_DATE")+"").substring(0, 10));
	}
	
	public void paymentStatement(String cartNumber, String state) {
		if(state.equals("결제대기")) {
			while(true) {
				System.out.print("\n ✔ 결제 하시겠습니까? (1: yes  2: no) › ");
				switch (scanner.nextInt()) {
					case 1: 
						PaymentDAO.getInstance().updateState("결제완료", cartNumber);
						System.out.println("\n ◈◈ 결제가 완료되었습니다 ◈◈\n\n");
						return;
					case 2: return;
					default: break;
				}
			}
		}else if(state.equals("결제완료")) {
			while(true) {
				System.out.print("\n ✔ 환불 하시겠습니까? (1: yes  2: no) › ");
				switch (scanner.nextInt()) {
					case 1: 
						PaymentDAO.getInstance().updateState("환불완료", cartNumber);
						System.out.println("\n ◈◈ 환불이 완료되었습니다 ◈◈\n\n");
						return;
					case 2: return;
					default: break;
				}
			}
		}else {
			System.out.println("");
		}
	}
}