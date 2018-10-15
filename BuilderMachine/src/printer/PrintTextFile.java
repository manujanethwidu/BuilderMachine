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

import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

import beans.SpecDetailsBean;
import beans.TireDetailsBean;
import utils.FloatRoundOff;
import utils.PrintAlignment;

public class PrintTextFile {

	public static void printSRTSticker(TireDetailsBean beanTire, SpecDetailsBean beanSpec)
			throws PrintException, IOException {
		// instane from TireDetailsBean Class
		String date = new SimpleDateFormat("yyyy-MM-dd ").format(new Date());
		String time = new SimpleDateFormat("hh:mm a").format(new Date());
		PrintAlignment printAlignment = new PrintAlignment();
		// w -->offset from the left in printout
		int w = printAlignment.getZPLPrintAlignment();

		// round SRT actual weight
		FloatRoundOff floatRoundOff = new FloatRoundOff();
		float SRTactWgt = floatRoundOff.round(beanSpec.getActTtlTireWeight(), 2);

		String PrintCommand = "^XA" + "" +

				"^FO" + (w + 5) + ",15^AQ,60,60^FD" + beanTire.getTireSize() + "^FS" + // tiresize
				" ^FO" + (w + 280) + ",15^AQ,50,50^FD" + beanTire.getLugType() + " ^FS" + // lugtype

				" ^FO" + (w + 20) + ",90^AQ,40,40^FDM. No:-" + beanTire.getMoldNO() + " ^FS" + // mold no

				" ^FO" + (w + 20) + ",140^Am,40,20^FD" + beanTire.getConfig() + "^FS" + // config
				"^FO" + (w + 290) + ",140^Am,40,20^FD" + beanTire.getRimSize() + "^FS" + // rimsize

				"^FO" + (w + 140) + ",190^AQ,45,45^FD" + beanTire.getTireType() + "^FS" + // tire type

				"^FO" + (w + 280) + ",250^Am,40,20^FD" + beanTire.getSwMsg() + "^FS" + // swmsg
				"^FO" + (w + 20) + ",250^Am,40,20^FD" + beanTire.getBrandName() + "^FS" + // brandname

				"^FO" + (w + 140) + ",290^AQ,60,60^FD" + SRTactWgt + "kg^FS" + // srt actual weight

				"^FO" + (w + 40) + ",355^Am,40,20^FDSN: " + beanTire.getsN() + "^FS" + // serial number

				" ^FO" + (w + 40) + ",380^AQ,100,100^FD" + time + "^FS" + // time

				" ^FO" + (w + 150) + ",465^AQ,35,25^FD" + date + "^FS" + // date

				" ^FO" + (w + 5) + ",70^GB" + (w + 395) + ",0,2 ^FS" + // first line
				"^FO" + (w + 5) + ",350^GB" + (w + 395) + ",0,2^FS" // last line
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

	public static void printPOBSticker(TireDetailsBean beanTire, SpecDetailsBean beanSpec)
			throws PrintException, IOException {
		// instane from TireDetailsBean Class
		String date = new SimpleDateFormat("yyyy-MM-dd ").format(new Date());
		String time = new SimpleDateFormat("hh:mm a").format(new Date());
		PrintAlignment printAlignment = new PrintAlignment();
		// w -->offset from the left in printout
		int w = printAlignment.getZPLPrintAlignment();

		// round POB act weight
		FloatRoundOff floatRoundOff = new FloatRoundOff();
		float POBactWgt = floatRoundOff.round(beanSpec.getActTtlTireWeight(), 2);

		float actBandWgt = floatRoundOff.round(beanTire.getActBandWGT(), 2);

		String PrintCommand = "^XA" + "" +

				"^FO" + (w + 5) + ",15^AQ,60,60^FD" + beanTire.getTireSize() + "^FS" + // tiresize

				" ^FO" + (w + 20) + ",90^AQ,40,40^FDM. No:-" + beanTire.getMoldNO() + " ^FS" + // mold no

				" ^FO" + (w + 20) + ",140^Am,40,20^FD" + beanTire.getConfig() + "^FS" + // config
				"^FO" + (w + 230) + ",140^Am,40,20^FD" + actBandWgt + "kg^FS" + // rimsize

				"^FO" + (w + 140) + ",190^AQ,45,45^FD" + beanTire.getTireType() + "^FS" + // tire type

				"^FO" + (w + 280) + ",250^Am,40,20^FD" + beanTire.getSwMsg() + "^FS" + // swmsg
				"^FO" + (w + 20) + ",250^Am,40,20^FD" + beanTire.getBrandName() + "^FS" + // brandname

				"^FO" + (w + 140) + ",290^AQ,60,60^FD" + POBactWgt + "kg^FS" + // srt actual weight

				"^FO" + (w + 40) + ",355^Am,40,20^FDSN: " + beanTire.getsN() + "^FS" + // serial number

				" ^FO" + (w + 20) + " ,380^AQ,70,70^FD" + time + "^FS" + // time
				" ^FO" + (w + 280) + " ,392^AQ,80,80^FD" + beanTire.getLugType() + " ^FS" + // lugtype

				" ^FO" + (w + 150) + ",465^AQ,35,25^FD" + date + "^FS" + // date

				" ^FO" + (w + 5) + ",70^GB" + (w + 395) + ",0,2 ^FS" + // first line
				"^FO" + (w + 5) + ",350^GB" + (w + 395) + ",0,2^FS" + // last line
				" ^FO" + (w + 275) + " ,390^GB120,75,5^FS" // square for lug type
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

class PrintJobWatcher {
	boolean done = false;

	PrintJobWatcher(DocPrintJob job) {
		job.addPrintJobListener(new PrintJobAdapter() {
			public void printJobCanceled(PrintJobEvent pje) {
				allDone();
			}

			public void printJobCompleted(PrintJobEvent pje) {
				allDone();
			}

			public void printJobFailed(PrintJobEvent pje) {
				allDone();
			}

			public void printJobNoMoreEvents(PrintJobEvent pje) {
				allDone();
			}

			void allDone() {
				synchronized (PrintJobWatcher.this) {
					done = true;
					System.out.println("Printing done ...");
					PrintJobWatcher.this.notify();
				}
			}
		});
	}

	public synchronized void waitForDone() {
		try {
			while (!done) {
				wait();
			}
		} catch (InterruptedException e) {
		}
	}
}
