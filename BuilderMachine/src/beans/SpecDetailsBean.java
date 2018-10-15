package beans;

public class SpecDetailsBean {
	int SpecID;
	int bCode;
	int cCode;
	int trCode;
	float bVol;
	float cVol;
	float trVol;
	float bonWgt;
	float baseCompWgt;
	float cusionCompWgt;
	float treadCompWgt;
	int specVersion;
	String bComp;
	String cComp;
	public float getTtlTireWeight() {
		return ttlTireWeight;
	}
	public void setTtlTireWeight(float ttlTireWeight) {
		this.ttlTireWeight = ttlTireWeight;
	}
	public float getActTtlTireWeight() {
		return actTtlTireWeight;
	}
	public void setActTtlTireWeight(float actTtlTireWeight) {
		this.actTtlTireWeight = actTtlTireWeight;
	}
	float ttlTireWeight;
	float actTtlTireWeight;

	
	public float getBaseCompWgt() {
		return baseCompWgt;
	}
	public void setBaseCompWgt(float baseCompWgt) {
		this.baseCompWgt = baseCompWgt;
	}
	public float getCusionCompWgt() {
		return cusionCompWgt;
	}
	public void setCusionCompWgt(float cusionCompWgt) {
		this.cusionCompWgt = cusionCompWgt;
	}
	public float getTreadCompWgt() {
		return treadCompWgt;
	}
	public void setTreadCompWgt(float treadCompWgt) {
		this.treadCompWgt = treadCompWgt;
	}
	String tRComp;
	int rnDApproval;
	int eDC1stTire;
	
	
	public int getSpecID() {
		return SpecID;
	}
	public void setSpecID(int specID) {
		SpecID = specID;
	}
	public int getbCode() {
		return bCode;
	}
	public void setbCode(int bCode) {
		this.bCode = bCode;
	}
	public int getcCode() {
		return cCode;
	}
	public void setcCode(int cCode) {
		this.cCode = cCode;
	}
	public int getTrCode() {
		return trCode;
	}
	public void setTrCode(int trCode) {
		this.trCode = trCode;
	}
	public float getbVol() {
		return bVol;
	}
	public void setbVol(float bVol) {
		this.bVol = bVol;
	}
	public float getcVol() {
		return cVol;
	}
	public void setcVol(float cVol) {
		this.cVol = cVol;
	}
	public float getTrVol() {
		return trVol;
	}
	public void setTrVol(float trVol) {
		this.trVol = trVol;
	}
	public float getBonWgt() {
		return bonWgt;
	}
	public void setBonWgt(float bonWgt) {
		this.bonWgt = bonWgt;
	}
	public int getSpecVersion() {
		return specVersion;
	}
	public void setSpecVersion(int specVersion) {
		this.specVersion = specVersion;
	}
	public String getbComp() {
		return bComp;
	}
	public void setbComp(String bComp) {
		this.bComp = bComp;
	}
	public String getcComp() {
		return cComp;
	}
	public void setcComp(String cComp) {
		this.cComp = cComp;
	}
	public String gettRComp() {
		return tRComp;
	}
	public void settRComp(String tRComp) {
		this.tRComp = tRComp;
	}
	public int getRnDApproval() {
		return rnDApproval;
	}
	public void setRnDApproval(int rnDApproval) {
		this.rnDApproval = rnDApproval;
	}
	public int geteDC1stTire() {
		return eDC1stTire;
	}
	public void seteDC1stTire(int eDC1stTire) {
		this.eDC1stTire = eDC1stTire;
	}
	
	
	
	


}
