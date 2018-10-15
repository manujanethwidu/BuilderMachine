package beans;

public class TCValidateBean {
	int sVerCard;
	int sVerDB;
	int eDC1stTire;
	int rndApproval;
	boolean blnTCValidate;
	boolean blnLastTire;
	int pid;
	int balanceDemand;
	public int getPid() {
		return pid;
	}

	public int getBalanceDemand() {
		return balanceDemand;
	}

	public void setBalanceDemand(int balanceDemand) {
		this.balanceDemand = balanceDemand;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public boolean isBlnLastTire() {
		return blnLastTire;
		
	}

	public void setBlnLastTire(boolean blnLastTire) {
		this.blnLastTire = blnLastTire;
	}

	String ErrorMsg;
	int moldQcapp;
	

	public boolean isBlnTCValidate() {
		return blnTCValidate;
	}

	public void setBlnTCValidate(boolean blnTCValidate) {
		this.blnTCValidate = blnTCValidate;
	}

	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}

	public int getsVerCard() {
		return sVerCard;
	}

	public void setsVerCard(int sVerCard) {
		this.sVerCard = sVerCard;
	}

	public int getsVerDB() {
		return sVerDB;
	}

	public void setsVerDB(int sVerDB) {
		this.sVerDB = sVerDB;
	}

	public int geteDC1stTire() {
		return eDC1stTire;
	}

	public void seteDC1stTire(int eDC1stTire) {
		this.eDC1stTire = eDC1stTire;
	}

	public int getRndApproval() {
		return rndApproval;
	}

	public void setRndApproval(int rndApproval) {
		this.rndApproval = rndApproval;
	}

	public int getMoldQcapp() {
		return moldQcapp;
	}

	public void setMoldQcapp(int moldQcapp) {
		this.moldQcapp = moldQcapp;
	}


}
