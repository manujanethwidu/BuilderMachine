package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;
import java.util.function.UnaryOperator;

import javax.print.PrintException;
import javax.swing.JOptionPane;

import beans.CompoundSGBean;
import beans.FinalWgtTolBean;
import beans.MoldDetailBean;
import beans.OrderDetailsBean;
import beans.ScaleFxBean;
import beans.SpecDetailsBean;
import beans.TCValidateBean;
import beans.TireDetailsBean;
import db.CreateConn;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.UnsupportedCommOperationException;
import importExcel.ImportExceldata;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import printer.PrintTextFile;
import printer.PrintWGT;
import tables.TireCodeTbl;
import tables.CompoudSGTbl;
import tables.MfgBuilderTbl;
import tables.MoldDetailsTbl;
import tables.OrderSummeryTbl;
import tables.SpecTbl;
import tables.StockStkTbl;
import utils.ComPortReader;
import utils.FloatRoundOff;
import utils.TireCodeValidation;
import weigthlimit.ReadBandWgtLimit;
import weigthlimit.ReadScaleLimit;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class BuilderController implements Initializable {
	// Scale Reading Event Handling Variables
	private InputStream in;
	CommPort commPort;   

	SerialPort serialPort;
	String scaleVal = "";
	int data;
	private byte[] buffer = new byte[1024];
	String ScalVal;
	static String SirialPortnomber = "COM3";

	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	private ScaleFxBean scaleBean = new ScaleFxBean();
	////////////////////////////////////////////////////////////////////
	// tirecode textfield
	@FXML
	TextField tireCodetxt;
	// serial number text field
	@FXML
	TextField sntxt;
	// scale rading text field
	@FXML
	TextField scaleTxtFld;
	@FXML
	TextField bandWeighttxt;
	// tire description label
	@FXML
	Label descriptionLBL;
	// standard band weight label
	@FXML
	Label standardBandWgtlbl;
	@FXML
	Label actBandWgtlbl;
	// toatal weight label
	@FXML
	Label totalWeightlbl;
	// Swmsg and Brand Name label
	@FXML
	Label swBrandNameLBL;
	// tire type label
	@FXML
	Label tireTypelbl;
	// base copmond label
	@FXML
	Label bclbl, cclbl, trclbl;
	// cusion compund label

	@FXML
	Label bandNamelbl;
	// base compound weight label
	@FXML
	Label bwgtlbl, cwgtlbl, trwgtlbl;
	@FXML
	VBox vboxTireDescr;
	@FXML
	Button bandWgtStickerbtn;

	// standard tread compound label
	@FXML
	Label actTrwgtlbl;
	@FXML
	Label bandWgtlbl;
	// OK button
	@FXML
	Button oKButton;
	@FXML
	Label bCaplbl;
	@FXML
	Label trCaplbl;
	@FXML
	Label bandCaplbl;
	@FXML
	Label cCaplbl;
	@FXML
	Label ActWgtCaplbl;
	@FXML
	GridPane gridPaneTireDetails;
	@FXML
	Label snMsglbl;
	@FXML
	Label bondWgtlbl;
	@FXML
	Label bondCaplbl;
	@FXML
	Label SpecWgtCaplbl;
	@FXML
	TextField stabilitylbl;
	@FXML
	Pane prtPane;
	@FXML
	Button snChangebtn;
	@FXML
	Label demandlbl;

	// Float Fields
	float scaleReader = 0f;
	float totalTireWeight = 0f;
	float actBandWgt = 0f;
	// string Fields
	String strActBandWgt = "";
	String tireCodeSub = "";
	String specVersionSub = "";

	// Beans
	SpecDetailsBean specBean = new SpecDetailsBean();
	TireDetailsBean tireBean = new TireDetailsBean();
	CompoundSGBean compoundSGBean = new CompoundSGBean();
	FinalWgtTolBean tolaranceBean = new FinalWgtTolBean();
	MoldDetailBean moldDetailBean = new MoldDetailBean();

	// Objects
	ReadScaleLimit readScaleLimit = new ReadScaleLimit();
	CompoudSGTbl compoudSGTbl = new CompoudSGTbl();
	ReadBandWgtLimit readBandWgtLimit = new ReadBandWgtLimit();

	boolean blnWeightInRange = false;

	/*
	 * .............................................................................
	 * .............................................................................
	 */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		scaleTxtFld.textProperty().bind(scaleBean.scaleReadingProperty().asString());
		stabilitylbl.textProperty().bind(scaleBean.scaleStabilityProperty());

		initialShowandHide();
		SNTextUpdate();
		// Interger Filter for TireCode and SN
		UnaryOperator<Change> integerFilter = change -> {
			String input = change.getText();
			if (input.matches("[0-9]*")) {
				return change;
			}
			return null;
		};
		tireCodetxt.setTextFormatter(new TextFormatter<String>(integerFilter));
		sntxt.setTextFormatter(new TextFormatter<String>(integerFilter));
		// Action listner for input tirecode

		tireCodetxt.textProperty().addListener((observable, oldValue, newValue) -> {
			String TireDescripton = "";
			String SWmsgBrandNameDescrip = "";

			SpecTbl specTbl = new SpecTbl();

			if (tireCodetxt.getText().length() >= 8) {
				if (tireCodetxt.getText().length() == 8) {
					if ((tireCodetxt.getText().length() == 8)) {

						/*
						 * get tirecode from the tirecode text by using sub string it set to the
						 * beanTire bean
						 */
						tireCodeSub = tireCodetxt.getText().substring(0, 5);
						System.out.println("asdf");
						tireBean.setTirecode(Integer.parseInt(tireCodeSub));

						// get the old tire descripiton form bean
						CreateConn createConn = new CreateConn();
						try (Connection con = createConn.GetConn();) {
							// get basic tire data including pid
							tireBean = GetTireDataFromTireCode(con, Integer.parseInt(tireCodeSub));
							// get mold QC approval
							MoldDetailsTbl moldDetailsTbl = new MoldDetailsTbl();
							moldDetailBean = moldDetailsTbl.GetMoldDetailsFromTC(con, Integer.parseInt(tireCodeSub));

							if (tireBean != null) {
								// Store SN frome sntxt TextField in to the bean
								int correctSn = Integer.parseInt(sntxt.getText());
								tireBean.setsN(correctSn);
								// Set tire Details in the relevent lablels
								TireDescripton = tireBean.getTireSize() + " " + tireBean.getLugType() + " "
										+ tireBean.getConfig() + "   " + tireBean.getTireType() + "   "
										+ tireBean.getRimSize();
								descriptionLBL.setText(TireDescripton);
								SWmsgBrandNameDescrip = tireBean.getBrandName() + "    " + tireBean.getSwMsg();
								swBrandNameLBL.setText(SWmsgBrandNameDescrip);

								// set TireType and Band Size to the label
								tireTypelbl.setText(tireBean.getTireType() + "");

								// set data to the spec bean
								specBean = specTbl.GetTireSpecData(con, Integer.parseInt(tireCodeSub),
										tireBean.getTireTypeId());
								if (specBean != null) {
									/*
									 * TCValidateBean passes gets 1.int sVerDB,2.int sVerCard 3.int eDC1stTire 4.int
									 * rndApproval 5.int moldQcapp 6.int pID 7.int nOS 8.int sVerCard and gets
									 * 1.boolean blnTCValidate 2.String ErrorMsg
									 */
									TireCodeValidation tireCodeValidation = new TireCodeValidation();
									TCValidateBean tCValidationBean = new TCValidateBean();

									specVersionSub = tireCodetxt.getText().substring(5, 8);
									tCValidationBean.setsVerCard(Integer.parseInt(specVersionSub));
									tCValidationBean.setsVerDB(specBean.getSpecVersion());
									tCValidationBean.seteDC1stTire(specBean.geteDC1stTire());
									tCValidationBean.setRndApproval(specBean.getRnDApproval());
									tCValidationBean.setMoldQcapp(moldDetailBean.getqCapp());
									tCValidationBean.setPid(tireBean.getPid());

									tCValidationBean = tireCodeValidation.validateTireCode(con, tCValidationBean);

									if (tCValidationBean.isBlnTCValidate()) {
										if (tireBean.gettCat() == 1) {
											// Total Weight Calculation
											getTotalWgt(con);
											setVisibleSRTComponent();
										}
										if (tireBean.gettCat() == 2) {
											// Show Standard Band Weight
											FloatRoundOff floatRoundOff = new FloatRoundOff();
											float standardBandWgtRounded = floatRoundOff.round(tireBean.getStdBandWGT(),
													2);
											standardBandWgtlbl.setText(Float.toString(standardBandWgtRounded));
											bandNamelbl.setText(tireBean.getBandName());
											Platform.runLater(new Runnable() {
												@Override
												public void run() {
													bandWeighttxt.setVisible(true);
													bandWeighttxt.requestFocus();
												}
											});
											// Show POB Items to be shown when TC is enterd
											trCaplbl.setVisible(true);
											bandCaplbl.setVisible(true);
											ActWgtCaplbl.setVisible(true);
											SpecWgtCaplbl.setVisible(true);

											// SN Text Addjustment
											sntxt.setEditable(false);
											snChangebtn.setVisible(false);
											prtPane.setStyle("-fx-background-color: rgb(38, 214, 76);");
										}
										// set base copmound to the label
										bclbl.setText(specBean.getbComp());
										// set cusion copmound to the label
										cclbl.setText(specBean.getcComp());
										// set tread copmound to the label
										trclbl.setText(specBean.gettRComp());
										demandlbl.setText("Order Qty:-  " + tCValidationBean.getBalanceDemand());
									} else {// if (tCValidationBean.isBlnTCValidate())
										Alert alert = new Alert(AlertType.INFORMATION);
										alert.setTitle("Error Message");
										alert.setHeaderText("TireCode Validation Error");
										alert.setContentText(tCValidationBean.getErrorMsg() + "");
										alert.showAndWait();
										System.out.println("Exception 1");
										refreshWholeScene();
									}

								} else {// (beanSpec != null)
									System.out.println("Exception 2");
									JOptionPane.showMessageDialog(null, "Spec not available");
									refreshWholeScene();
									Alert alert = new Alert(AlertType.INFORMATION);
									alert.setTitle("Error Message");
									alert.setHeaderText("TireCode Validation Error");
									alert.setContentText(
											"BuilderController-E07-application.BuilderController.tireCodetxt actionListner :- "
													+ "Spec bean is null");
									alert.showAndWait();
								}
							} else {// if(TiredetailsBean)
								System.out.println("Exception 3");
								refreshWholeScene();
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Error Message");
								alert.setHeaderText("TireCode Validation Error");
								alert.setContentText("BuilderController-E13-TiredetailBean is null");
								alert.showAndWait();
							}
						} catch (SQLException e) {
							System.out.println("Exception 4");
							refreshWholeScene();
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Error Message");
							alert.setHeaderText("TireCode Validation Error");
							alert.setContentText(
									"BuilderController-E09-application.BuilderController.tireCodetxt actionListner :-  Please Check the TireCode"
											+ e);
							alert.showAndWait();
						} catch (ClassNotFoundException e1) {
							System.out.println("Exception 5");
							refreshWholeScene();
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Error Message");
							alert.setHeaderText("TireCode Validation Error");
							alert.setContentText(
									"BuilderController-E10-application.BuilderController.tireCodetxt actionListner "
											+ e1);
							alert.showAndWait();
						} catch (Exception e3) {
							System.out.println("Exception 6");
							refreshWholeScene();
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Error Message");
							alert.setHeaderText("TireCode Validation Error");
							alert.setContentText(
									"BuilderController-E14-application.BuilderController.tireCodetxt actionListner "
											+ e3);
							alert.showAndWait();
						}
					} ///////// if(tireCodetxt.getText().length() == 8)
				}
				if (tireCodetxt.getText().length() > 0) {
					String s = tireCodetxt.getText().substring(0, 8);
					tireCodetxt.setText(s);
				}
			} else {// if (tireCodetxt.getText().length() >= 8)
				RefreshFields();
			}
		});
		bandWeighttxt.textProperty().addListener((observable, oldValue, newValue) -> {

			if (bandWeighttxt.getText().length() > 1) {

				if (((bandWeighttxt.getText()).toUpperCase().charAt(0) == 'S') && ((bandWeighttxt.getText())
						.toUpperCase().charAt(bandWeighttxt.getText().length() - 1) == 'L')) {
					// get numaric portion of the barcoded text(remove s and
					// l)
					strActBandWgt = bandWeighttxt.getText().substring(1, bandWeighttxt.getText().length() - 1);
					CreateConn createConn = new CreateConn();
					try (Connection con = createConn.GetConn();) {
						actBandWgt = Float.parseFloat(strActBandWgt);
						boolean blnBandWgtVeryfy = false;
						ReadBandWgtLimit readBandWgtLimit = new ReadBandWgtLimit();
						if (tireBean.getTireType() == "APW") {
							blnBandWgtVeryfy = true;
						} else {
							blnBandWgtVeryfy = readBandWgtLimit.veryfyBandWgt(actBandWgt, tireBean.getStdBandWGT());
						}
						tireBean.setActBandWGT(actBandWgt);
						if (blnBandWgtVeryfy) {
							getTotalWgt(con);
							setVisiblePOBComponent();
						} else {
							// this ecmeption called when band weight text
							// field is
							// filled with non numeric value
							JOptionPane.showMessageDialog(null, "Please Enter Corect Band Weight!");
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									bandWeighttxt.setText("");
									bandWeighttxt.requestFocus();
								}
							});
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"BuilderController-E03-application.BuilderController.initialize method.bandWeighttxt actionlistner :- "
										+ e);
					} catch (SQLException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"BuilderController-E04-application.BuilderController.initialize method.bandWeighttxt actionlistner :- "
										+ e);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"BuilderController-E05-application.BuilderController.initialize method.bandWeighttxt actionlistner :- "
										+ e);
					}

				} // if first latter==S and last letter==L
			} // if(bandWeighttxt.getText().length() > 1)

		});
		// code for the Last Serial number textfield and limit the only 9 numbers
		// that number get from calling SerialNumbersDataTbl.GetTireSN(con) method
		sntxt.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {

					// Check if the new character is the number or other's

					if (sntxt.getText().length() > 9) {
						sntxt.setText(sntxt.getText().substring(0, 9));
					}
					if (sntxt.getText().length() < 9) {
						oKButton.setVisible(false);
						tireCodetxt.setDisable(true);
						snMsglbl.setText(sntxt.getText().length() + "/9");
					}
					if (sntxt.getText().length() == 9) {

						snMsglbl.setText("");
						tireCodetxt.setDisable(false);
						tireCodetxt.clear();
						tireCodetxt.requestFocus();
					}
				}
			}
		});

		// Scale Reader text feild
		scaleTxtFld.textProperty().addListener((observable, oldValue, newValue) -> {
			
			scaleReader = Float.parseFloat(scaleTxtFld.getText());
			if (scaleReader > 0) {
				
				if (tolaranceBean.getMinTol() <= scaleReader && scaleReader <= tolaranceBean.getMaxTol()) {
					blnWeightInRange = true;
				
					System.out.println("IN Range");
				} else {
					blnWeightInRange = false;
					System.out.println(tolaranceBean.getMinTol());
					System.out.println(tolaranceBean.getMaxTol());
				}
			}
		});
		stabilitylbl.textProperty().addListener((observable, oldValue, newValue) -> {
			if (stabilitylbl.getText().trim().equals("KG")) {
				bandWgtStickerbtn.setVisible(true);
				if (blnWeightInRange) {
					oKButton.setVisible(true);
					prtPane.setStyle("-fx-background-color:rgb(38, 214, 76);");
				} else {
					oKButton.setVisible(false);

					prtPane.setStyle("-fx-background-color: rgb(192, 232, 217);"); // after Scane
				}
			} else {
				bandWgtStickerbtn.setVisible(false);
				oKButton.setVisible(false);
				prtPane.setStyle("-fx-background-color: rgb(192, 232, 217);"); // after Scane
			}

		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				tireCodetxt.requestFocus();
				tireCodetxt.positionCaret(0);
			}
		});
	}

	private void SNTextUpdate() {
		oKButton.setVisible(false);
		sntxt.setEditable(false);
		// get the last serial number from the SearialNumberDataTbl
		CreateConn createConn = new CreateConn();
		try (Connection con = createConn.GetConn();) {
			int FinalSn = 0;
			FinalSn = TireCodeTbl.GetLastSN(con);

			// sntxt.setText(FinalSn + "");
			sntxt.setText((FinalSn + 1) + "");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"BuilderController-E01-application.BuilderController.initialize method :- " + e);
			sntxt.setText("");
		} catch (SQLException e) {

			e.printStackTrace();
			sntxt.setText("");
			JOptionPane.showMessageDialog(null,
					"BuilderController-E02-application.BuilderController.initialize method :- " + e);
		}
	}

	@FXML
	private void barcodePrinting() {

		PrintTextFile printTextFile = new PrintTextFile();
		try {
			if (tireBean.gettCat() == 1) {
				printTextFile.printSRTSticker(tireBean, specBean);
			}
			if (tireBean.gettCat() == 2) {
				printTextFile.printPOBSticker(tireBean, specBean);
			}

		} catch (PrintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"BuilderController-E014-application.BuilderController.barcodePrinting method :- " + e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"BuilderController-E015-application.BuilderController.barcodePrinting method :- " + e);
		}
	}

	// get tire description from BuilderTbl by using TireDetailsBe1an
	private TireDetailsBean GetTireDataFromTireCode(Connection con, int TireCode) throws SQLException {
		TireDetailsBean beandup = new TireDetailsBean();
		// GetTireData(con, 161207820);
		TireCodeTbl bulderTbl = new TireCodeTbl();
		beandup = bulderTbl.GetTireDataFromTC(con, TireCode);
		if (beandup != null) {
			return beandup;
		} else {
			return null;
		}
	}
	// when click the ok button
	// insert data to mfg.builder table and stock.stk table at the server
	

	@FXML
	private void insertDatatoBuilder() throws Exception {
		if (Integer.toString(tireBean.getsN()).length() == 9) {
			// Clear Band text'
			bandWeighttxt.clear();
			float actWgt = 0f;

			FloatRoundOff floatRoundOff = new FloatRoundOff();
			OrderDetailsBean orderDetailsBean = new OrderDetailsBean();
			OrderSummeryTbl orderSummeryTbl = new OrderSummeryTbl();
			CreateConn createConn = new CreateConn();

			// Set Actual weight
			actWgt = Float.parseFloat(scaleTxtFld.getText());
			float actWeightRounded = floatRoundOff.round(actWgt, 2);
			specBean.setActTtlTireWeight(actWeightRounded);

			try (Connection con = createConn.GetConn()) {
				con.setAutoCommit(false);
				boolean blnmfginserted = false;
				boolean blnstkinserted = false;
		
				boolean spectblUpdated = false;
				// insert data into mfg.builder table
				blnmfginserted = MfgBuilderTbl.InsertMfgBuilderTbl(con, tireBean, specBean, compoundSGBean);
				// insert data into stock.stk table
				blnstkinserted = StockStkTbl.InsertStockstk(con, tireBean);

				if (specBean.getRnDApproval() == 0 || specBean.geteDC1stTire()==0) {
					//System.out.println("***"+specBean.getRnDApproval());
					spectblUpdated=SpecTbl.UpdateSpecTbl(con, specBean);
				 
				}
				
				// substitute tire from mfg.pidcrosscheck table
				if (blnmfginserted == false || blnstkinserted == false) {
					System.out.println("Not updated");
					con.rollback();
					refreshWholeScene();
				} else {
					barcodePrinting();
					con.commit();
					refreshWholeScene();
					System.out.println("Updated  " + tireBean.getsN());
				}
				con.setAutoCommit(true);
			} catch (ClassNotFoundException e) { // TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"BuilderController-E17-application.BuilderController.insertDatatoBuilder method :- " + e);

			}
		} else {// SN.length() !=9
			System.out.println("Exception 5");
			refreshWholeScene();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Message");
			alert.setHeaderText("");
			alert.setContentText("Wrong Serial Number ");
			alert.showAndWait();
			snChangebtn.setVisible(true);
		}

	}

	@FXML
	private void TireWGT() {
		// print scaleweight barcode
		PrintWGT printWGT = new PrintWGT();
		try {
			printWGT.printScaleWgt(scaleTxtFld.getText());
		} catch (PrintException | IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	private void changeSN() {
		sntxt.setEditable(true);

	}

	// when click the ok button
	// this method call to refresh the all scene and it display new SN
	public void refreshWholeScene() {
		try {

			initialShowandHide();
			SNTextUpdate();

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					tireCodetxt.clear();
					tireCodetxt.requestFocus();
					tireCodetxt.end();
				}
			});

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error Message");
			alert.setHeaderText("Error in refreshWholeScene");
			alert.setContentText(e + "");

			alert.showAndWait();
		}

	}

	// to get total tire weight from compoudSGTbl
	private void getTotalWgt(Connection conn) throws ClassNotFoundException, SQLException {
		FloatRoundOff floatRoundOff = new FloatRoundOff();
		// get bsg value from the CompoundSG table by usin bCode
		float bSG = compoudSGTbl.GetBaseCompoundSG(conn, specBean.getbCode());
		// get csg value from the CompoundSG table by usin cCode
		float cSG = compoudSGTbl.GetCentreCompoundSG(conn, specBean.getcCode());
		// get trsg value from the CompoundSG table by usin cCode
		float trSG = compoudSGTbl.GetTreadCompoundSG(conn, specBean.getTrCode());

		// calculate copmound weights
		float baseCompWgt = (specBean.getbVol() * bSG);
		float cusionCompWgt = (specBean.getcVol() * cSG);
		float treadCompWgt = (specBean.getTrVol() * trSG);

		compoundSGBean.setBsg(bSG);
		compoundSGBean.setCsg(cSG);
		compoundSGBean.setTrsg(trSG);
		specBean.setBaseCompWgt(baseCompWgt);
		specBean.setCusionCompWgt(cusionCompWgt);
		specBean.setTreadCompWgt(treadCompWgt);

		if (tireBean.gettCat() == 1) {
			// get SRT tire total weight
			totalTireWeight = (specBean.getbVol() * bSG) + (specBean.getcVol() * cSG) + (specBean.getTrVol() * trSG)
					+ specBean.getBonWgt();

			// Set SRT Labels

			float baseCompWeightRounded = floatRoundOff.round(baseCompWgt, 2);
			float cusionCompWeightRounded = floatRoundOff.round(cusionCompWgt, 2);
			float treadCompWeightRounded = floatRoundOff.round(treadCompWgt, 2);
			float bondWgtRounded = floatRoundOff.round(specBean.getBonWgt(), 2);
			bwgtlbl.setText(Float.toString(baseCompWeightRounded));
			cwgtlbl.setText(Float.toString(cusionCompWeightRounded));
			trwgtlbl.setText(Float.toString(treadCompWeightRounded));

			bondWgtlbl.setText(Float.toString(bondWgtRounded));
		}
		if (tireBean.gettCat() == 2) {
			// calculate actualtread compound weight

			float calcTrWgt = (specBean.getTrVol() * trSG) + ((tireBean.getStdBandWGT() - actBandWgt)) * 150 / 1000;
			/// get POB tire total weight
			if (tireBean.getTireType() == "APW") {
				totalTireWeight = actBandWgt + treadCompWgt;
			} else {
				totalTireWeight = calcTrWgt + actBandWgt;
			}
			// Set POB Labels
			float calcTrWgtRounded = floatRoundOff.round(calcTrWgt, 2);
			float specTrWgtRounded = floatRoundOff.round(treadCompWgt, 2);
			trwgtlbl.setText(Float.toString(specTrWgtRounded));
			actTrwgtlbl.setText(Float.toString(calcTrWgtRounded));

			actBandWgtlbl.setText(actBandWgt + "");
		}
		// Display Total Weight
		float totalWeightRounded = floatRoundOff.round(totalTireWeight, 2);
		totalWeightlbl.setText(Float.toString(totalWeightRounded));
		specBean.setTtlTireWeight(totalTireWeight);
		// Displya vboxTireDescr
		vboxTireDescr.setVisible(true);
		// cal the ReadScaleLimit method to get correct weight
		tolaranceBean = readScaleLimit.getCorrectScale(totalTireWeight);
	}

	private void RefreshFields() {
		scaleReader = 0;
		totalTireWeight = 0;

		bclbl.setText("");
		cclbl.setText("");
		trclbl.setText("");
		bwgtlbl.setText("");
		cwgtlbl.setText("");
		trwgtlbl.setText("");
		actTrwgtlbl.setText("");
		bondWgtlbl.setText("");
		standardBandWgtlbl.setText("");
		actBandWgtlbl.setText("");
		actTrwgtlbl.setText("");
		bandNamelbl.setText("");

		descriptionLBL.setText("");
		swBrandNameLBL.setText("");
		tireTypelbl.setText("");

		totalWeightlbl.setText("");
		bandNamelbl.setText("");
		standardBandWgtlbl.setText("");
		standardBandWgtlbl.setText("");
		demandlbl.setText("");

		bCaplbl.setVisible(false);
		cCaplbl.setVisible(false);
		trCaplbl.setVisible(false);
		bandCaplbl.setVisible(false);
		ActWgtCaplbl.setVisible(false);
		SpecWgtCaplbl.setVisible(false);
		bondCaplbl.setVisible(false);

		oKButton.setVisible(false);
		vboxTireDescr.setVisible(false);

		// Band Weight Text and Caption
		bandWgtlbl.setVisible(false);
		bandWeighttxt.setVisible(false);
		prtPane.setStyle("-fx-background-color: rgb(192, 232, 217);"); // after Scane
	}

	private void setVisibleSRTComponent() {
		gridPaneTireDetails.setVisible(true);
		descriptionLBL.setVisible(true);
		swBrandNameLBL.setVisible(true);
		tireTypelbl.setVisible(true);
		totalWeightlbl.setVisible(true);

		bCaplbl.setVisible(true);
		cCaplbl.setVisible(true);
		trCaplbl.setVisible(true);
		bondCaplbl.setVisible(true);

		bclbl.setVisible(true);
		cclbl.setVisible(true);
		trclbl.setVisible(true);
		bwgtlbl.setVisible(true);
		cwgtlbl.setVisible(true);
		trwgtlbl.setVisible(true);
		actTrwgtlbl.setVisible(true);

		// Hid Band Related Details
		bandNamelbl.setText("");
		bandCaplbl.setVisible(false);
		actBandWgtlbl.setText("");
	}

	private void setVisiblePOBComponent() {

		cclbl.setText("");
		bclbl.setText("");
	}

	private void initialShowandHide() {
		RefreshFields();

		snChangebtn.setVisible(true);

	}

	// ......................................................................................
	public void WindowShownEvent() {
		try {
			SirialPortnomber = ComPortReader.getComPort();

			System.setProperty("gnu.io.rxtx.SerialPorts", SirialPortnomber);

			CommPortIdentifier portId = null;
			Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

			// First, Find an instance of serial port as set in PORT_NAMES.
			while (portEnum.hasMoreElements()) {
				CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
				if (currPortId.getName().equals(SirialPortnomber)) {
					portId = currPortId;
					break;
				}
			}
			if (portId == null) {
				JOptionPane.showMessageDialog(null, "No Com Port");
			}
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {

				try {
					int len = 0;
					while ((data = input.read()) > -1) {
						if (data == '\n') {
							break;
						}
						buffer[len++] = (byte) data;
					}
					ScalVal = new String(buffer, 0, len);
					Platform.runLater(() -> {
						// Context.getInstance().currentCountry().setCountry(ScalVal);
						scaleBean.setScaleReadingFull(ScalVal);
						scaleBean.setScaleReading(Float.parseFloat(ScalVal.substring(3, 9).trim()));
						scaleBean.setScaleStability(ScalVal.substring(9,12).trim());
						//System.out.println(ScalVal.substring(9, 11).trim());
						//System.out.println(ScalVal);
					});
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e);
					System.exit(-1);
				}
			});
			serialPort.notifyOnDataAvailable(true);
		} catch (TooManyListenersException | UnsupportedCommOperationException | PortInUseException | IOException
				| ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	@FXML
	public void changeScreenBtnPushed(ActionEvent event) {
		try {
			Parent  builderScene= FXMLLoader.load(getClass().getResource("PressScreen.fxml"));
			Scene pressScene = new Scene(builderScene);
			
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(pressScene);
			window.show();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		
	}
	
	public void WindowColoasEvent() {
		try {
			serialPort.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
