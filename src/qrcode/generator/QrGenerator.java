package qrcode.generator;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Trieda vygeneruje a zakóduje unikátne info o klientovi
 * do QRcódu, ktorý sa pri registrácii odošle na klientov
 * mail
 * @author grofc
 *
 */

public class QrGenerator {

	private static final String IMAGE_LOCATION = "./Images/QRcode.png";
	private static final int HEIGHT = 350;
	private static final int WIDTH = 350;
	
	
	
	public static void createQRCode(int ID, String name) {
		try {
			generateQRCodeImage(ID, name, HEIGHT, WIDTH, IMAGE_LOCATION);
		} catch (WriterException e) {
			System.out.println("Could not generate QR code");
		}catch (IOException e) {
			System.out.println("Could not generate QR code IO Exception");
		}
	}
	

	private static void generateQRCodeImage(int ID, String name, int height,
			int width, String location) throws WriterException, IOException {
		QRCodeWriter codeWriter = new QRCodeWriter();
		
		String text = "Name: " + name + "\n " +  " ID: " + ID;
		
		BitMatrix matrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
		Path path = FileSystems.getDefault().getPath(location);
		
		MatrixToImageWriter.writeToPath(matrix, "PNG", path);
	}
	
	
}
