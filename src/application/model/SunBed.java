package application.model;

/**
 * The <code>SunBed</code> objects are only accessed through the
 * <code>SunBedCollection</code>
 * 
 * @author Josh James
 *
 */
public class SunBed {
    private int ID;
    private boolean booked;

    private static int nextID = 1;

    public SunBed() {
        this.ID = nextID;
        this.booked = false;
        nextID++;
    }

    /**
     * Used to get the id of a sun bed
     * 
     * @return int The ID
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Used to toggle the status of a sun bed between free and occupied
     */
    public void toggleBooked() {
        if (!this.booked) {
            this.booked = true;
        }

        else {
            this.booked = false;
        }
    }

    /**
     * Used to get the string representation of a sunbed
     * 
     * @return String
     */
    @Override
    public String toString() {
        if (this.booked) {
            return "Sun Bed #" + this.ID + " is booked right now.";
        }

        else {
            return "Sun Bed #" + this.ID + " is not booked right now.";
        }
    }

    /**
     * Used to determine if the sun bed is free or occupied
     * 
     * @return The status of the sun bed as a boolean
     */
    public boolean isBooked() {
        return booked;
    }

    /**
     * 
     * @param booked
     */
    public void setBooked(boolean booked) {
		this.booked = booked;
	}

}
