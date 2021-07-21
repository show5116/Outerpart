package view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import dao.TourDAO;
import util.ScannerBuffer;
import vo.TourVO;

public class TourRegister {
	private TourRegister() {}
	private static TourRegister instance;
	public static TourRegister getInstance(){
		if(instance == null){
			instance = new TourRegister();
		}
		return instance;
	}
	
	private TourDAO tourDao = TourDAO.getInstance();
	private ScannerBuffer scanner = ScannerBuffer.getInstance();
	private DecimalFormat formatter = new DecimalFormat("###,###");
	
	public void tourInsert() {
		System.out.print("\n\n ✔ 관광명소 이름 › ");
		String tourName = scanner.next();
		System.out.print("\n ✔ 관광명소 시간 › ");
		String tourTime = scanner.next();
		System.out.print("\n ✔ 가격 › ");
		int tourPrice = scanner.nextInt();
		System.out.print("\n ✔ 지역 › ");
		String city = scanner.next();
		System.out.print("\n ✔ 설명 › ");
		String explanation = scanner.next();
		if (tourDao.insertTour(new TourVO(LoginService.loginId.getUserId(), tourName, tourTime, tourPrice, city, explanation)) == 1) {
			System.out.println("\n ◈◈ 등록 성공 ◈◈ \n");
		} else {
			System.out.println("\n [ 등록 실패 ]\n");
		};
	}
	
	public void tourDelete() {
		System.out.print("\n ✔ 삭제할 관광명소 번호 › ");
		int tourNumber = scanner.nextInt();
		if (tourDao.deleteTour(new TourVO(tourNumber)) == 1) {
			System.out.println("\n ◈◈ 삭제되었습니다 ◈◈\n");
		} else {
			System.out.println("\n [ 존재하지 않거나 본인이 등록한 관광명소가 아닙니다 ]\n");
		}
	}
	
	public void tourSearch() {
		ArrayList<HashMap<String, Object>> list = tourDao.select(LoginService.loginId);
		int size = list.size();
		System.out.println("\n\n   < 번호 >\t< 이름 >\t\t     < 시간 >     < 가격 >    < 지역 >   < 설명 >\n");
		for(int i=0; i<size;i++) {
			System.out.printf("     %s",list.get(i).get("TOUR_NUMBER")+"\t\t");
			System.out.print(list.get(i).get("TOUR_NAME")+"\t\t");
			System.out.print(list.get(i).get("TOUR_TIME")+"\t ");
			System.out.print(formatter.format(list.get(i).get("TOUR_PRICE"))+"\t     ");
			System.out.print(list.get(i).get("CITY")+"     ");
			System.out.print(list.get(i).get("EXPLANATION")+"\n\n");
		}
	}
}