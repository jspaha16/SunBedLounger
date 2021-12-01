/**
 * 
 */
package application.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author John Mc
 *
 */
public class XMLDataExchange {

	public static void writeData(Object data, String fileDestination) throws IOException {
		XMLEncoder encoder;
		encoder = new XMLEncoder(new BufferedOutputStream( new FileOutputStream( fileDestination )));
		encoder.writeObject(data);
		encoder.close();
	}
	
	public static Object readData(String fileLocation) throws IOException {
		Object data = null;
		XMLDecoder decoder;
		
		decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileLocation)));
		data = decoder.readObject();
		decoder.close();
		
		return data;
	}
}
