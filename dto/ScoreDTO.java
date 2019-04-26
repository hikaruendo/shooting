package dto;

public class ScoreDTO {
	/** 社員ID */
	private int idscore;
	/** 社員名 */
	private String nmscore;

	private int point;



	/**
	 * @return 社員ID
	 */
	public int getIdScore() {
		return idscore;
	}
	/**
	 * @param idscore 社員ID
	 */
	public void setIdScore(int idscore) {
		this.idscore = idscore;
	}
	/**
	 * @return 社員名
	 */
	public String getNmScore() {
		return nmscore;
	}
	/**
	 * @param nmscore 社員名
	 */
	public void setNmScore(String nmscore) {
		this.nmscore = nmscore;
	}

	public int getPoint() {
		return point;
	}
	/**
	 * @param idscore 社員ID
	 */
	public void setPoint(int point) {
		this.point = point;
	}

}
