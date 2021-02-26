/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/** Outsourced class extends Abstract Part class.
*@author ndoyon1
 */

public class Outsourced extends Part{
   
   
    private String companyName;
   
/** This Constructor initializes the Outsourced Part fields. 
     * @param id InHouse Part ID
     * @param name InHouse name of Part
     * @param price InHouse price of part
     * @param stock InHouse stock of parts available
     * @param min InHouse Part minimum inventory
     * @param max InHouse Part Maximum inventory
     * @param companyName InHouse companyName of Part
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max );
          
    }
/**
 * @return the companyName
 */
    public String getCompanyName() {
        return companyName;
    }
/**
 * @param companyName name of the company to set
 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}