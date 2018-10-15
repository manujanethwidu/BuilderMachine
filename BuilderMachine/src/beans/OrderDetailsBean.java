package beans;

public class OrderDetailsBean {
	int pID;
	int Demand;
	public int getpID() {
		return pID;
	}
	public void setpID(int pID) {
		this.pID = pID;
	}
	public int getDemand() {
		return Demand;
	}
	public void setDemand(int demand) {
		Demand = demand;
	}
	public boolean isBlnPIDAvailability() {
		return blnPIDAvailability;
	}
	public void setBlnPIDAvailability(boolean blnPIDAvailability) {
		this.blnPIDAvailability = blnPIDAvailability;
	}
	boolean blnPIDAvailability;
}
