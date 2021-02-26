
package Model;

import java.util.ArrayList;



/** Class Product public class for all products in the Inventory Management system
 *@author ndoyon1
 */

public class Product {
    
   
    private ArrayList<Part> associatedParts = new ArrayList<>();
    private int prodID;
    private String prodName;
    private double price = 0.0;
    private int inStock;
    private int min;
    private int max;


    /** This Constructor initializes the Product fields. 
     * @param prodID product ID
     * @param prodName  name of Product
     * @param price  price of Product
     * @param inStock stock of product available
     * @param min Product minimum inventory
     * @param max Product Maximum inventory 
     */

    public Product(int prodID, String prodName, double price, int inStock, int min, int max) {
        this.prodID = prodID;
        this.prodName = prodName;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
   }
    

    /**
     * @return the product id
     */
    
    public int getProdID() {
        return prodID;
    }
    
    /**
     * @param prodID sets product ID
     */
    
    public void setProdID(int prodID) {
        this.prodID = prodID;
    }

    /**
     * @return the product name
     */
    
    public String getProdName() {
        return prodName;
    }
    
    /**
     * @param prodName sets product Name
     */
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    /**
     * @return the associated Parts
     */
    
    public ArrayList<Part> getAssociatedParts() {
        return associatedParts;
    }
    
    /**
     * @param associatedParts sets associatedParts
     */
    
    public void setAssociatedParts(ArrayList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    /**
     * @return the price
     */
    
    public double getPrice() {
        return price;
    }
    
    /**
     * @param price sets product price
     */
    
    public void setPrice(double price) {
        this.price = price;
    }

    
    /**
     * @return the stock available
     */

    public int getInStock() {
        return inStock;
    }
    
    /**
     * @param inStock sets product inventory stock
     */
    
    public void setInStock(int inStock) {
        this.inStock = inStock;
    }
    
    /**
     * @return the minimum inventory
     */
    
    public int getMin() {
        return min;
    }
    
    /**
     * @param min sets minimum inventory
     */
    
    public void setMin(int min) {
        this.min = min;
    }
    
    /**
     * @return the max inventory
     */
    
    public int getMax() {
        return max;
    }
    
    /**
     * @param max sets maximum inventory
     */
    
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     * @param partToAdd adds associated Part
     */
    
    public void addAssociatedPart(Part partToAdd) {
        associatedParts.add(partToAdd);
    }

    /** Method removeAssociatedPart removes assoc. removes part from associated part. 
     * @param partToRemove removes part from associated part
     * @return if partID is true remove part, if part ID is not true return false
     */
    
    public boolean removeAssociatedPart(int partToRemove) {
        int i;
        for (i = 0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i).getId() == partToRemove) {
                associatedParts.remove(i);
                return true;
            }
        }
        return false;
    
    }   
    
    /**Method lookupAssociatedPart used to search associated part. 
     *@param partToSearch search for associated part with part ID
     *@return if true get associated part, if false return null
     */
    
    public Part lookupAssociatedPart(int partToSearch) {
         for (int i = 0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i).getId() == partToSearch) {
                return associatedParts.get(i);
            }
        }
        return null;
    }
  
    /** 
     *@return associatedParts.size
     */
    
    public int getPartsListSize() {
        return associatedParts.size();
    }
}