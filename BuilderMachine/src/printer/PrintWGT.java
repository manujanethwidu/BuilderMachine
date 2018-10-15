package printer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import beans.TireDetailsBean;
import utils.FloatRoundOff;
import utils.PrintAlignment;

public class PrintWGT {
	public static void printScaleWgt(String scalewgt) throws PrintException, IOException {
		// instane from TireDetailsBean Class
		PrintAlignment printAlignment = new PrintAlignment();
		// w -->offset from the left in printout
		int w = printAlignment.getZPLPrintAlignment();
		System.out.println(scalewgt);
		//round SRT actual weight
		FloatRoundOff floatRoundOff = new FloatRoundOff();
		//float SRTactWgt = floatRoundOff.round(beanTire.getSRTactWGT(), 2);

		String PrintCommand = "^XA" + ""+
		
				"^FO" + (w +50) +",150" + 
				"  ^BY2" + 
				
				
				"^BCN,120,Y,N,S" +
				"^FDs" + scalewgt + "l^FS"
				+ "  ^XZ";
		


		String defaultPrinter = PrintServiceLookup.lookupDefaultPrintService().getName();
		// System.out.println("Default printer: " + defaultPrinter);
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();

		// prints the famous hello world! plus a form feed
		InputStream is = new ByteArrayInputStream(PrintCommand.getBytes("UTF8"));

		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		pras.add(new Copies(1));

		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		Doc doc = new SimpleDoc(is, flavor, null);
		DocPrintJob job = service.createPrintJob();

		PrintJobWatcher pjw = new PrintJobWatcher(job);
		job.print(doc, pras);
		pjw.waitForDone();
		is.close();
	}
}
