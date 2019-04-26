package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ScoreDTO;
import util.DbUtil;

public class ScoreDAO {
	private static final String TBL_NAME = "score";
	private static final String ID_SCORE = "id_score";
	private static final String NM_SCORE = "nm_score";
	private static final String POINT = "point";


	/** DBコネクション */
	public Connection con;
	/** DBステートメント */
	PreparedStatement stmt;
	/** 検索結果 */
	ResultSet rs;

	/**
	 * インスタンス生成時DBコネクション
	 *
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ScoreDAO(Connection con) {
		this.con = con;
	}

	/**
	 * 社員テーブルの情報を全件取得する
	 *
	 * @return 検索結果
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<ScoreDTO> selectAll() throws SQLException, ClassNotFoundException {
		List<ScoreDTO> rtnList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append("    " + "*");
		sql.append(" FROM ");
		sql.append("    " + TBL_NAME);
		try {

			this.stmt = con.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				rtnList.add(rowMappingScore(rs));
			}
		} finally {
			DbUtil.closeStatement(this.stmt);
		}
		return rtnList;
	}

	/**
	 * 社員テーブルから社員IDに合致する情報を取得する
	 *
	 * @param id
	 *            社員ID
	 * @return 検索結果
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws NumberFormatException
	 */
	public List<ScoreDTO> selectById(String id) throws SQLException, ClassNotFoundException, NumberFormatException {
		List<ScoreDTO> rtnList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append("    " + "*");
		sql.append(" FROM ");
		sql.append("    " + TBL_NAME);
		sql.append(" WHERE ");
		sql.append("    " + ID_SCORE + " = " + "?");
		try {
			this.stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, Integer.parseInt(id));
			rs = stmt.executeQuery();
			while (rs.next()) {
				rtnList.add(rowMappingScore(rs));
			}
		} finally {
			DbUtil.closeStatement(this.stmt);
		}
		return rtnList;
	}

	/**
	 * 社員テーブルに新規登録する
	 *
	 * @param name
	 *            社員名
	 * @param kana
	 *            社員名カナ
	 * @param mail
	 *            メールアドレス
	 * @param pass
	 *            パスワード
	 * @param depId
	 *            部署ID
	 * @throws NumberFormatException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void scrInsert(String name, String point)
			throws NumberFormatException, SQLException, ClassNotFoundException {
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT " + " INTO ");
		sql.append("    " + TBL_NAME);
		sql.append(" ( ");
		sql.append("    " + NM_SCORE);
		sql.append("   ," + POINT);
		sql.append(" ) ");
		sql.append(" VALUES ");
		sql.append(" ( ");
		sql.append("    " + " ? ");
		sql.append("   ," + " ? ");
		sql.append(" ) ");
		try {
			this.stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, name);
			stmt.setInt(2, Integer.parseInt(point));
			stmt.executeUpdate();
		} finally {
			DbUtil.closeStatement(this.stmt);
		}
	}

	/**
	 * 社員テーブルの社員IDに合致する情報を更新する
	 *
	 * @param id
	 *            社員ID
	 * @param name
	 *            社員名
	 * @param kana
	 *            社員名カナ
	 * @param mail
	 *            メールアドレス
	 * @param pass
	 *            パスワード
	 * @param depId
	 *            部署ID
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
//	public void scrUpdate(String id, String name,String point)
//			throws ClassNotFoundException, SQLException {
//		StringBuilder sql = new StringBuilder();
//		sql.append(" UPDATE ");
//		sql.append("    " + TBL_NAME);
//		sql.append(" SET ");
//		sql.append("    " + NM_SCORE + " = " + "?");
//		sql.append("   ," + POINT + " = " + "?");
//		sql.append(" WHERE ");
//		sql.append("    " + ID_SCORE + " = " + "?");
//		try {
//			this.stmt = con.prepareStatement(sql.toString());
//			stmt.setString(1, name);
//			stmt.setString(6, id);
//			stmt.executeUpdate();
//		} finally {
//			DbUtil.closeStatement(this.stmt);
//		}
//	}

	/**
	 * 社員テーブルの社員IDに合致する情報を削除する
	 *
	 * @param id
	 *            社員ID
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
//	public void empDelete(String id) throws ClassNotFoundException, SQLException {
//		StringBuilder sql = new StringBuilder();
//
//		sql.append(" DELETE " + " FROM ");
//		sql.append("    " + TBL_NAME);
//		sql.append(" WHERE ");
//		sql.append("    " + ID_SCORE + " = " + " ? ");
//		try {
//			this.stmt = con.prepareStatement(sql.toString());
//			stmt.setString(1, id);
//			stmt.executeUpdate();
//		} finally {
//			DbUtil.closeStatement(this.stmt);
//		}
//	}

	/**
	 * DBから取得した検索結果をDTO型のインスタンスにセットし、リストに格納する
	 *
	 * @param rs
	 *            検索結果
	 * @return リスト
	 * @throws SQLException
	 */
	public ScoreDTO rowMappingScore(ResultSet rs) throws SQLException {
		ScoreDTO emDto = new ScoreDTO();
		emDto.setIdScore(rs.getInt(ID_SCORE));
		emDto.setNmScore(rs.getString(NM_SCORE));
		emDto.setPoint(rs.getInt(POINT));
		return emDto;
	}
}
