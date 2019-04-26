package main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.ScoreDAO;
import dto.ScoreDTO;
import util.DbUtil;
/**
 * @author IT-College
 * @veresion 1.0
 */
public class Main {
	static Scanner sc = new Scanner(System.in);
	/**
	 * テーブル切り替え用変数 0：scoreテーブル 1：Departmentテーブル
	 */
	static int tblFlg = 0;

	/**
	 * 処理開始位置
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("システムを開始します");
		while (true) {
			try {
				new Main().start();
			} catch (Exception e) {
				System.out.println("予期せぬエラーが発生しました");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 処理選択を行う
	 *
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void start() throws ClassNotFoundException, SQLException {
		System.out.println();
		//System.out.println("現在のメンテナンス対象テーブル：" + targetTableName(tblFlg));
		System.out.println("1:検索 2:登録 0:終了");
		String ope = sc.nextLine();
		switch (ope) {
		case "1":
			if (tblFlg == 0) {
				scrSelect();
			}
			break;
		case "2":
			if (tblFlg == 0) {
				scrInsert();
			}
			break;
//		case "3":
//			if (tblFlg == 0) {
//				empUpdate();
//			}
//			break;
//		case "4":
//			if (tblFlg == 0) {
//				empDelete();
//			}
//			break;
//		case "9":
//			if (tblFlg == 0) {
//				tblFlg = 1;
//			} else {
//				tblFlg = 0;
//			}
//			return;
		case "0":
			System.out.println("システムを終了します");
			System.exit(0);
		default:
			System.out.println("指定の番号を入力してください");
			break;
		}
	}

	/**
	 * 社員テーブルから情報を検索する
	 *
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void scrSelect() throws ClassNotFoundException, SQLException {
		List<ScoreDTO> list = new ArrayList<>();
		Connection con = DbUtil.getConnection();
		ScoreDAO scrDao = new ScoreDAO(con);
		System.out.println("1:全検索 2:条件検索 それ以外:戻る");
		String select = sc.nextLine();
		try {
			switch (select) {
			case "1":
				list = scrDao.selectAll();
				break;
			case "2":
				String id = inputCheck("ID", true);
				list = scrDao.selectById(id);
				if (list.size() == 0) {
					System.out.println("入力された値で情報がみつかりませんでした");
					break;
				}
				break;
			default:
				return;
			}
			new Main().dispSelectEmp(list);
		} finally {
			DbUtil.closeConnection(scrDao.con);
		}

	}


	/**
	 * 社員テーブルに情報を登録する
	 *
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void scrInsert() throws NumberFormatException, ClassNotFoundException, SQLException {
		Connection con = DbUtil.getConnection();
		ScoreDAO empDao = new ScoreDAO(con);
		try {
			String name = inputCheck("名前", false);
			String point = inputCheck("score", true);
			empDao.scrInsert(name, point);
			System.out.println("登録が完了しました");
		} finally {
			DbUtil.closeConnection(empDao.con);
		}
	}


	/**
	 * 社員テーブルの情報を更新する
	 *
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
//	public static void empUpdate() throws NumberFormatException, ClassNotFoundException, SQLException {
//		List<ScoreDTO> list = new ArrayList<>();
//		Connection con = DbUtil.getConnection();
//		ScoreDAO empDao = new ScoreDAO(con);
//		try {
//			String id = inputCheck("ID", true);
//			list = empDao.selectById(id);
//			if (list.size() == 0) {
//				System.out.println("入力された値で情報がみつかりませんでした");
//				return;
//			}
//			String name = inputCheck("名前", false);
//			String point = inputCheck("score", true);
//			empDao.empUpdate(id, name, point);
//			System.out.println("更新が完了しました");
//		} finally {
//			DbUtil.closeConnection(empDao.con);
//		}
//	}


	/**
	 * 社員テーブルの情報を削除する
	 *
	 * @throws NumberFormatException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
//	public static void empDelete() throws NumberFormatException, ClassNotFoundException, SQLException {
//		List<ScoreDTO> list = new ArrayList<>();
//		Connection con = DbUtil.getConnection();
//		ScoreDAO empDao = new ScoreDAO(con);
//		try {
//			String id = inputCheck("ID", true);
//			list = empDao.selectById(id);
//			if (list.size() == 0) {
//				System.out.println("入力された値で情報がみつかりませんでした");
//				return;
//			}
//			System.out.println("指定されたIDの情報を削除します？ Y/y");
//			System.out.println("（Y/y以外の入力であればキャンセル）");
//			String yY = sc.nextLine();
//			if (!"y".equals(yY) && !"Y".equals(yY)) {
//				System.out.println("削除をキャンセルしました");
//				return;
//			}
//			empDao.empDelete(id);
//			System.out.println("削除が完了しました");
//		} finally {
//			DbUtil.closeConnection(empDao.con);
//		}
//	}

	/**
	 * フラグを判定し、テーブル名を取得する
	 *
	 * @param tblFlg
	 *            テーブル名判定用フラグ
	 * @return テーブル名
	 */
//	public static String targetTableName(int tblFlg) {
//		String rtnTblName = "";
//		if (tblFlg == 0) {
//			rtnTblName = "scoreテーブル";
//		} else {
//			rtnTblName = "部署テーブル";
//		}
//		return rtnTblName;
//	}

	/**
	 * 入力値を判定し、返却する
	 *
	 * @param titleName
	 *            入力項目名
	 * @param flg
	 *            数値チェック可否フラグ
	 * @return
	 */
	public static String inputCheck(String titleName, boolean flg) {
		String str = null;
		while (true) {
			System.out.println(titleName + "を入力してください");
			str = sc.nextLine();
			if (str.trim().length() == 0) {
				System.out.println(titleName + "は入力必須です。");
				continue;
			}
			if (flg) {
				if (str.trim().length() > 0) {
					try {
						Integer.parseInt(str);
					} catch (NumberFormatException e) {
						System.out.println(titleName + "は数値入力です");
						continue;
					}
				}
			}
			break;
		}
		return str;
	}

	/**
	 * 社員テーブル情報を表示する
	 *
	 * @param list
	 *            社員情報リスト
	 */
	public void dispSelectEmp(List<ScoreDTO> list) {
		for (ScoreDTO dto : list) {
			System.out.println(dto.getIdScore() + "\t" + dto.getNmScore() + "\t" + dto.getPoint());
		}
	}


}
