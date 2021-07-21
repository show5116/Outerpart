package view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import dao.CarDAO;
import util.ScannerBuffer;
import vo.CarVO;

public class CarRegister {
	private CarRegister() {}
	private static CarRegister instance;
	public static CarRegister getInstance(){
		if(instance == null){
			instance = new CarRegister();
		}
		return instance;
	}
	
	private CarDAO carDao = CarDAO.getInstance();
	private ScannerBuffer scanner = ScannerBuffer.getInstance();
	private DecimalFormat formatter = new DecimalFormat("###,###");
	
	public void carInsert() {
		System.out.print("\n\n ✔ 차 번호 › ");
		String carNumber = scanner.next();
		System.out.print("\n ✔ 차 종류 › ");
		String carKind = scanner.next();
		System.out.print("\n ✔ 가격 › ");
		int carPrice = scanner.nextInt();
		System.out.print("\n ✔ 인승 › ");
		int carSeats = scanner.nextInt();
		System.out.print("\n ✔ 지역 › ");
		String city = scanner.next();
		System.out.print("\n ✔ 차 색상 › ");
		String carColor = scanner.next();
		if (carDao.insertCar(new CarVO(carNumber, LoginService.loginId.getUserId(), carKind, carPrice, carSeats, city, carColor)) == 1) {
			System.out.println("\n ◈◈ 등록 성공 ◈◈ \n");
		} else {
			System.out.println("\n [ 등록 실패 ]\n");
		}
	}
	
	public void carDelete() {
		System.out.print("\n ✔ 삭제할 차 번호 › ");
		String carNumber = scanner.next(); 
		if (carDao.deleteCar(new CarVO(carNumber)) == 1) {
			System.out.println("\n ◈◈ 삭제되었습니다 ◈◈\n");
		} else {
			System.out.println("\n [ 존재하지 않거나 본인이 등록한 차가 아닙니다 ]\n");
		}
	}
	
	public void carSearch() {
		ArrayList<HashMap<String, Object>> list = carDao.select(LoginService.loginId);
		int size = list.size();
		System.out.println("\n\n  < 번호 >\t< 종류 >\t\t< 가격 >\t\t< 인승 >\t\t< 지역 >\t\t< 색상 >\n");
		for(int i=0; i<size;i++) {
			System.out.printf(" %s",list.get(i).get("CAR_NUMBER")+"\t");
			System.out.printf("%s\t",list.get(i).get("CAR_KIND"));
			System.out.print(formatter.format(list.get(i).get("CAR_PRICE"))+"\t\t   ");
			System.out.print(list.get(i).get("CAR_SEATS")+"\t\t  ");
			System.out.print(list.get(i).get("CITY")+"\t\t ");
			System.out.print(list.get(i).get("CAR_COLOR")+"\n\n");
		}
	}
}