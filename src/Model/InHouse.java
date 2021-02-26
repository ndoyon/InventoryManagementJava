
package Model;

/**
 * InHouse class extends Abstract Part class.
 * @author ndoyon1
 */

public class InHouse extends Part {
    
  
    private int machineID;
    
    /** This Constructor initializes the InHouse Part fields. 
     * @param id InHouse Part ID
     * @param name InHouse name of Part
     * @param price InHouse price of part
     * @param stock InHouse stock of parts available
     * @param min InHouse Part minimum inventory
     * @param max InHouse Part Maximum inventory
     * @param machineID InHouse MachineID of Part
     */
    
     public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
            super(id, name, price, stock, min, max);
        
    }
     
/** 
 * @return machineID
 */
     
    public int getMachineID() {
        return machineID;
    }
    
/**
 *@param machineID the machineID to set
 */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}