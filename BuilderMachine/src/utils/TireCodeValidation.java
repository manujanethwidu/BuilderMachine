package utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import beans.OrderDetailsBean;
import beans.TCValidateBean;
import beans.TireDetailsBean;
import db.CreateConn;
import tables.OrderSummeryTbl;
import tables.StockStkTbl;

public class TireCodeValidation {
	@SuppressWarnings("unused")
	public TCValidateBean validateTireCode(Connection con, TCValidateBean bean) throws Exception {
		// TireinHand should be less than zero;
		// Spec-version
		// 1st Tire-EDC&RnD
		// Mold Stop

		boolean retVal = true;
		boolean blnPIDcheck = false;
		String ErrorMsg = "Unknown Error";
		int TireinHand = 1000000;

		// get PID validation
		OrderSummeryTbl orderSummeryTbl = new OrderSummeryTbl();

		OrderDetailsBean orderDetailsBean = new OrderDetailsBean();

		orderDetailsBean = orderSummeryTbl.CheckDemandAvlforPID(con, bean.getPid());
		if (orderDetailsBean != null) {
			blnPIDcheck = orderDetailsBean.isBlnPIDAvailability();
		} else {
			blnPIDcheck = false;
			retVal = false;
			ErrorMsg = "PID is not in order list";
		}
		StockStkTbl stockStkTbl = new StockStkTbl();
		TireinHand = stockStkTbl.GetTiresinHand(con, bean.getPid());
		// sn2txt.setDisable(true);
		// check the availability of the tires and how much production wants to produce
		if (orderDetailsBean != null) {
			int orderBalance = orderDetailsBean.getDemand() - TireinHand;
			System.out.println("Demand " + orderDetailsBean.getDemand());
			System.out.println("Stock " + TireinHand);
			if (orderBalance <= 0) {
				retVal = false;
				ErrorMsg = "Order Completed";
				bean.setBalanceDemand(0);
			} else {
				bean.setBalanceDemand(orderBalance);
			}
			// For the last Tire
			if (orderBalance == 1) {
				bean.setBlnLastTire(true);
			} else {
				bean.setBlnLastTire(false);
			}
		}
		/*
		 * Spec Version must be equal card specversion and database spec version if it
		 * isn't equaled that the application will be refreshed
		 */
		if (bean.getsVerCard() != bean.getsVerDB()) {
			ErrorMsg = "Old Spec Card";
			retVal = false;
		}
		// Rnd Approval must have to produce the tire
		// if it isn't approved that the application will be refreshed
		if (bean.getRndApproval() == 1) {
			ErrorMsg = "2nd Tire. Pls approve the 1st Tire from R&D ";
			retVal = false;
		}
		// Quality approval must have to produce the tire
		//// if it isn't approved that the application will be refreshed

		if (bean.getMoldQcapp() != 1) {
			ErrorMsg = "Quality Dep Hold the Mold! Please Get the Approval";
			retVal = false;
		}
		bean.setErrorMsg(ErrorMsg);
		bean.setBlnTCValidate(retVal);
		return bean;
	}
}
