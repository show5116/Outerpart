package view;

import controller.CustomerController;
import controller.HostController;
import dao.OuterparkUserDAO;
import util.*;
import vo.OuterparkUserVO;

public class LoginService {
	private LoginService(){}
	private static LoginService instance;
	public static LoginService getInstance(){
		if(instance == null){
			instance = new LoginService();
		}
		return instance;
	}
	
	private OuterparkUserDAO userDao = OuterparkUserDAO.getInstance();
	private ScannerBuffer scanner = ScannerBuffer.getInstance();

	private SHA256Util sha = SHA256Util.getInstance();
	private PatternCheckUtil pattern = PatternCheckUtil.getInstance();
	private SendMail send = SendMail.getInstance();
	private TemporaryPasswordUtil tem = TemporaryPasswordUtil.getInstance();
	
	public static OuterparkUserVO loginId; 
	
	public int main() {
		System.out.println(" \n ──────────────────────────────────────────────────────────────────────────────");
		System.out.println(" \t\t\t 어서오세요. OuterPark입니다. \t\t\t\t");
		System.out.println(" ──────────────────────────────────────────────────────────────────────────────\n");
		System.out.println(" 1.로그인 | 2.회원가입 | 3.아이디 찾기 | 4.임시 비밀번호 발급 | 5.프로그램 종료\n");
		System.out.print(" ✔ 번호를 입력해주세요 › ");
		int input = scanner.nextInt();
		return input;
	}
	
	public int login() {
		System.out.print("\n ✔ 아이디 › ");
		String userId = scanner.next();
		System.out.print(" ✔ 비밀번호 › ");
		String userPassword = scanner.next();
		userPassword = sha.encrypt(userPassword);
		
		if (userDao.loginUser(new OuterparkUserVO(userId, userPassword))) {
			System.out.println("\n ◈◈ 로그인 성공 ◈◈\n\n\n");
			loginId = userDao.selectid(new OuterparkUserVO(userId));
			if (loginId.getUsertype().equals("host")) {
				HostController.getInstance().hostMenu();
				return 0;
			} else {
				CustomerController.getInstance().customerMenu();
				return 0;
			}
		} else {
			System.out.println("\n [ 아이디 혹은 비밀번호를 잘못 입력하였습니다 ]\n\n");
			return 0;
		}
	}

	public int signUp() {
		String userId = "";
		while(!pattern.patternCheck(userId,pattern.idPattern)) {
			System.out.println("\n\n [ 아이디는 첫문자 영어, 6자 이상이어야 합니다 ]");
			System.out.print(" ✔ 아이디 › ");
			userId = scanner.next();
		}
		String userPassword = "";
		while(!pattern.patternCheck(userPassword, pattern.passwordPattern)) {
			System.out.println("\n [비밀번호는 문자, 숫자, 특수문자가 포함된 8자 이상이어야 합니다 ]");
			System.out.print(" ✔ 비밀번호 › ");
			userPassword = scanner.next();
		}
		userPassword = sha.encrypt(userPassword);
		System.out.print("\n ✔ 이름 › ");
		String userName = scanner.next();
		System.out.print("\n ✔ 닉네임 › ");
		String userNickname = scanner.next();
		String userMail = "";
		while(!pattern.patternCheck(userMail, pattern.mailPattern)) {
			System.out.println("\n [ 메일은 올바른 메일 형식으로 입력해야 합니다 ] ");
			System.out.print(" ✔ 메일 › ");
			userMail = scanner.next();
		}
		String userType = "";
		while (userType.isEmpty()) {
			System.out.print("\n ✔ 타입 (host: 1, customer: 2) › ");
			int userTypei = scanner.nextInt();
			switch(userTypei) {
				case 1: userType = "host"; break;
				case 2: userType = "customer"; break;
				default: System.out.println("\n      [ 다시 입력하세요 ]");
			}
		}
		
		if (userDao.insertUser(new OuterparkUserVO(userId, userPassword, userName, userNickname, userMail, userType)) == 1) {
			System.out.println("\n ◈◈ 회원가입 성공 ◈◈\n\n\n");
		} else {
			System.out.println("\n ◈◈ 회원가입 실패 ◈◈\n\n\n");
		}
		return 0;
	}

	public int idFound() {
		System.out.print("\n\n ✔ 이름 › ");
		String userName = scanner.next();
		System.out.print("\n ✔ 메일 › ");
		String userMail = scanner.next();
		System.out.print("\n ✔ 닉네임 › ");
		String userNickname = scanner.next();
		if (userDao.idfoundUser(new OuterparkUserVO(userName, userMail, userNickname)) == null) {
			System.out.println("\n [ 아이디를 찾을 수 없습니다 ]\n\n\n");
		} else {
			System.out.print("\n ✔ 아이디 › ");
			System.out.println(userDao.idfoundUser(new OuterparkUserVO(userName, userMail, userNickname)));
			System.out.println("\n\n\n");
		}
		return 0;
	}
	
	public int temporary() {
		System.out.print("\n\n ✔ 아이디 › ");
		String userId = scanner.next();
		System.out.print("\n ✔ 이름 › ");
		String userName = scanner.next();
		System.out.print("\n ✔ 메일 › ");
		String userMail = scanner.next();
		System.out.print("\n ✔ 닉네임 › ");
		String userNickname = scanner.next();
		if (userDao.idfoundUser(new OuterparkUserVO(userName, userMail, userNickname)) == null) {
			System.out.println("\n [ 계정을 찾을 수 없습니다] \n\n\n");
		}else {
			String temporary =tem.getTemporaryPassword();
			send.ToSendMail(userMail, temporary);
			temporary = sha.encrypt(temporary);
			if(userDao.tempPassword(new OuterparkUserVO(userId, userName, userMail, userNickname), temporary)==1) {
				System.out.println("\n ◈◈ 임시 비밀번호를 메일로 보내드렸습니다 ◈◈\n\n\n");
			}
		}
		return 0;
	}

	public void exit() {
		System.out.println("\n\n 🎉 이용해 주셔서 감사합니다 🎉");
		System.exit(0);
	}
}