import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TestPDFFileProcessor {
	
	private PDFFileProcessor pdfFileProcessor;
	
	private String samplePDF = "src/test/resources/test_cv.pdf";
	
	private String cvStrapLine = "This is my Awesome CV\n\nI have worked as a developer.\n";
	
	@Before
	public void setUp() throws IOException {
		pdfFileProcessor = new PDFFileProcessor();
		pdfFileProcessor.setDocument(samplePDF);
	}

	@Test
	public void testPDFFileProcessor() throws IOException {
		PDFFileProcessor pdfFileProcessor = new PDFFileProcessor("src/test/resources/test_cv.pdf");
	}
	
	@Test
	public void testPDFFileProcessorCanViewPDFContent() throws IOException {
		assertEquals(cvStrapLine, pdfFileProcessor.readTextFromPdf());
	}
	
	@Test
	public void testPDFFileProcessorManipulatesPDFContent() throws IOException {
		
		String inputText = "You should be able to write COBOL, Fortran, make a new form of react, "+
				"run our agile transformation, secure the network and make tea." +
				" Must also be a member of the AWS (American Welding Society).";
		
		boolean hideText = false;
		pdfFileProcessor.addText(inputText, hideText);
		
		PDFFileProcessor hiddenPdfFileProcessor = new PDFFileProcessor();
		hiddenPdfFileProcessor.setDocument(FileHelper.appendHiddenToFileName(samplePDF));
		
		assertEquals(cvStrapLine + inputText+"\n", hiddenPdfFileProcessor.readTextFromPdf());
	}
	
	@Test
	public void testPDFFileProcessorHidesPDFContent() throws IOException {
		
		String hiddenText = "You should be able to write COBOL, Fortran, make a new form of react, "+
				"run our agile transformation, secure the network and make tea." +
				" Must also be a member of the AWS (American Welding Society).";
		
		boolean hideText = true;
		pdfFileProcessor.addText(hiddenText, hideText);
		
		PDFFileProcessor hiddenPdfFileProcessor = new PDFFileProcessor();
		hiddenPdfFileProcessor.setDocument(FileHelper.appendHiddenToFileName(samplePDF));
		
		assertEquals(cvStrapLine + hiddenText+"\n", hiddenPdfFileProcessor.readTextFromPdf());
	}

}
