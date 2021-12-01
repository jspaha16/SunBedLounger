/**
 * 
 */
package application.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import application.model.SunBed;


/**
 * Used to read and write sun bed data to and from
 * the file system.  If unable to read or write 
 * the file the method must throw an error.
 * 
 * @author John McNeil
 *
 */
public class AccessDataFile {
	
	
	private static Logger logger = LogManager.getLogger(AccessDataFile.class);
	

	/**
	 * Writes <code>SunBedCollection</code> data to file
	 * @throws IOException 
	 *  
	 * 
	 */
	public static void writeData(List<SunBed> sunBeds, String fileDestination) throws IOException {
		ObjectOutputStream output = 
				new ObjectOutputStream
					(new FileOutputStream(fileDestination));
		output.writeObject(sunBeds);
		logger.info("Write Data method has been called");
		output.flush();
		output.close();
	}
	
	/**
	 * Reads <code>SunBedCollection</code> data from file
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static List<SunBed> readData(String fileLocation) throws IOException, ClassNotFoundException {
		ObjectInputStream input = 
				new ObjectInputStream
					(new FileInputStream(fileLocation));
		
		List<SunBed> inputList = (ArrayList<SunBed>) 
					input.readObject();
		
		logger.info("Read Data method has been called");
		input.close();
		
		return inputList;
	}
	
//	public static void main(String[] args) {
//		
//		List<SunBed>  sbad= new ArrayList<>();
//		sbad.add(new SunBed());
//		try {
//			AccessDataFile.writeData(null, null);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	}
	

