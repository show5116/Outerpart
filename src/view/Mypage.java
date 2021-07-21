package view;

import dao.OuterparkUserDAO;
import util.*;

public class Mypage {
	private Mypage() {}
	private static Mypage instance;
	public static Mypage getInstance(){
		if(instance == null){
			instance = new Mypage();
		}
		return instance;
	}
	
	private ScannerBuffer scanner = ScannerBuffer.getInstance();
	private OuterparkUserDAO userDao = OuterparkUserDAO.getInstance();
	private SHA256Util sha = SHA256Util.getInstance();
	private PatternCheckUtil pattern = PatternCheckUtil.getInstance();
		
	public void updatePassword() {
		String changePassword = "";
		while(!pattern.patternCheck(changePassword, pattern.passwordPattern)) {
			System.out.println("\n [비밀번호는 문자, 숫자, 특수문자가 포함된 8자 이상이어야 합니다 ]");
			System.out.print("\n ✔ 변경할 비밀번호를 입력하세요 › ");
			changePassword = scanner.next();			
		}
		changePassword = sha.encrypt(changePassword);
		
		if (userDao.updateUser(LoginService.loginId,"USER_PASSWORD",changePassword)==1) {
			System.out.println("\n ◈◈ 비밀번호를 변경하였습니다 ◈◈\n\n\n");
		} else {
			System.out.println("\n [ 비밀번호를 변경할 수 없습니다 ]\n\n\n");
		}
	}
	
	public void updateNickname() {
		System.out.print("\n ✔ 변경할 닉네임을 입력하세요 › ");
		String userNickname = scanner.next();
		
		if (userDao.updateUser(LoginService.loginId,"USER_NICKNAME",userNickname)==1) {
			System.out.println("\n ◈◈ 닉네임을 변경하였습니다 ◈◈\n");
		} else {
			System.out.println("\n [ 닉네임을 변경할 수 없습니다 ]\n");
		}
	}
}