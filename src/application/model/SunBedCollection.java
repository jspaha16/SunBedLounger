/**
 * 
 */
package application.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import application.util.XMLDataExchange;

/**
 * Manages the collection of sun beds available in the application. There is no
 * direct access to a sun bed, all access is via this class. All changes must be
 * written out to file as they happen.
 * 
 * @author Josh James
 *
 */
public class SunBedCollection {
	private static volatile SunBedCollection instance = null;
	private List<SunBed> sunBeds;
	// private static final String FILE_NAME_AND_LOCATION = "sunloungers.ser";
	private static final String FILE_NAME_AND_LOCATION = "sunloungers.xml";

	/**
	 * An instance of this class can only be created by calling the
	 * <code>getInstance()</code> method hence the constructor is private.
	 */
	private SunBedCollection() {
		super();
		sunBeds = new ArrayList<>();
	}

	/**
	 * Returns an object of this class. Ensures that only one instance is created.
	 * This is an example of the singleton design pattern.
	 * 
	 * @return The collection of sunbeds
	 */
	public static SunBedCollection getInstance() {
		if (instance == null) {
			synchronized (SunBedCollection.class) {
				if (instance == null) {
					instance = new SunBedCollection();
				}
			}
		}
		return instance;
	}

	/**
	 * @return List<SunBed> return the sunBeds
	 */
	public List<SunBed> getSunBeds() {
		return sunBeds;
	}

	/**
	 * @param sunBeds the sunBeds to set
	 */
	public void setSunBeds(List<SunBed> sunBeds) {
		this.sunBeds = sunBeds;
		try {
			writeDataToFile();
		} catch (Exception e) {
			System.out.println("SunBedCollection write to file failed!");
			e.printStackTrace();
		}
	}

	/**
	 * Used to get the total number of sun beds
	 * 
	 * @return int The number of sun beds in the system
	 */
	public int getCount() {
		return sunBeds.size();
	}

	/**
	 * Returns the number of sun beds available
	 * 
	 * @return Returns the number of sunbeds that are currently free
	 */
	public int getCountFree() {
		int count = 0;
		for (SunBed s : sunBeds) {
			if (!s.isBooked()) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Adds a sun bed to the end of the collection
	 */
	public void addSunBed() {
		sunBeds.add(new SunBed());
		try {
			writeDataToFile();
		} catch (Exception e) {
			System.out.println("SunBedCollection write to file failed!");
			e.printStackTrace();
		}
	}

	/**
	 * Removes the last sun bed from the end of the collection
	 */
	public void removeSunBed() {
		sunBeds.remove(sunBeds.size() - 1);
		try {
			writeDataToFile();
		} catch (Exception e) {
			System.out.println("SunBedCollection write to file failed!");
			e.printStackTrace();
		}
	}

	/**
	 * Passed the id of a sun bed, returns if the sun bed is occupied.
	 * 
	 * @param id The ID of the sunbed as an int
	 * @return Returns the state of the sunbed - free or occupied
	 */
	public boolean isOccupied(int id) {
		return sunBeds.get(id).isBooked();
		
/*		for (SunBed s : sunBeds) {
			System.out.println("sunbed id: " + s.getID());
			if (s. .getID() == id) {
				return s.isBooked();
			}
		}
		throw new IllegalStateException("Can not find a sunbed matching ID:" + id);
*/
	}

	/**
	 * Set all sun beds to the free state
	 */
	public void setAllSunBedsToFree() {
		for (SunBed s : sunBeds) {
			if (s.isBooked())
				s.setBooked(false);
		}

		try {
			writeDataToFile();
		} catch (Exception e) {
			System.out.println("SunBedCollection write to file failed!");
			e.printStackTrace();
		}
	}

	/**
	 * Clears the sunBeds list
	 */
	public void clearSunBeds() {
		sunBeds.clear();

		try {
			writeDataToFile();
		} catch (Exception e) {
			System.out.println("SunBedCollection write to file failed!");
			e.printStackTrace();
		}
	}

	/**
	 * Passed the id of a sun bed, toggles its state between free and occupied
	 * 
	 * @param id The ID of the sunbed as an int
	 */
	public void toggleSunBed(int id) {
		
		sunBeds.get(id).toggleBooked();
/*		
		for (SunBed s : sunBeds) {
			if (s.getID() == id) {
				s.toggleBooked();
			}
		}
*/
		try {
			writeDataToFile();
		} catch (Exception e) {
			System.out.println("SunBedCollection write to file failed!");
			e.printStackTrace();
		}
	}

	/**
	 * Read sun bed data from file. If there is an error the method must throw it.
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void readDataFromFile() throws IOException {
		if (XMLDataExchange.readData(FILE_NAME_AND_LOCATION) instanceof List<?>) {
			sunBeds = (List<SunBed>) XMLDataExchange.readData(FILE_NAME_AND_LOCATION);
		}
	}

	/**
	 * Write sun bed data out to file. If there is an error the method must throw
	 * it.
	 * 
	 * @throws IOException
	 */
	public void writeDataToFile() throws IOException {
		XMLDataExchange.writeData(sunBeds, FILE_NAME_AND_LOCATION);
	}
}
