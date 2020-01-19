import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFFileProcessor {

	String documentFilePath;
	PDDocument document;

	public PDFFileProcessor() {
	}

	public PDFFileProcessor(String filePath) throws IOException {
		documentFilePath = filePath;
	}
	
	public void setDocument(String filePath) throws IOException {
		documentFilePath = filePath;
	}
	
	private void loadDocument() throws IOException {
		this.document = PDDocument.load(new File(documentFilePath));
	}
	
	public String readTextFromPdf() throws IOException {
		PDDocument document = PDDocument.load(new File(documentFilePath));
		PDFTextStripper pdfStripper = new PDFTextStripper();
		
		String pdfOutput = pdfStripper.getText(document);
		document.close();
		return pdfOutput;
	}
	
	private String generateNewHiddenFilename() {
		String[] documentFilePathAndExtension = documentFilePath.split("\\.");
		documentFilePathAndExtension[0] += "_hidden";
		return  "".join(".", documentFilePathAndExtension);
		
	}
	
	public void addHiddenText(String textToHide) throws IOException {
		addText(textToHide, true);
	}
	
	public void addText(String textToHide, boolean hidden) throws IOException {
		loadDocument();
		PDPage page = document.getPage(0);
		PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true);
		
		contentStream.beginText();
		contentStream.newLineAtOffset(25, 500);
		if(hidden)
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 0);
		else
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
		contentStream.showText(textToHide);
		contentStream.endText();
		contentStream.close();
		
		document.save(new File(FileHelper.appendHiddenToFileName(documentFilePath)));
		document.close();
	}
	
	
}
