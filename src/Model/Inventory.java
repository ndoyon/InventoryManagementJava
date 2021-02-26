/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *Inventory class houses all Parts and Products through an arrayList, To extend functionality in 2.0, adding a persistent inventory that utilizes a database would be the best option. 
 * @author nick
 */

public class Inventory {

    private ArrayList<Product> allProducts;
    private ArrayList<Part> allParts;

    public Inventory() {
        allProducts = new ArrayList<>();
        allParts = new ArrayList<>();
    }
    
 /**
 * 
 *  Method addProduct adds products. 
     * @param productToAdd adds product to allProducts
 */

    public void addProduct(Product productToAdd) {
        if (productToAdd != null) {
            this.allProducts.add(productToAdd);
        }
    }

    /** Method remove Product from allProducts. 
     *@param productToRemove removes product from allProducts
     * @return true product can be removed if there is a product ID, false product cant remove
     */
    
    public boolean removeProduct(int productToRemove) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProdID() == productToRemove) {
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    /** Method lookUpProduct look up product from allProducts. 
     * @param productToSearch searches for products in allProducts
     * @return if allProducts has a prodID then return it, if not return null
     */
    public Product lookUpProduct(int productToSearch) {
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getProdID() == productToSearch) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }

   /** Method lookUpProduct using observableList Part.
     * @param productNameToLookUp String that looks up product
     * @return null
    */
    
    public ObservableList<Part> lookUpProduct(String productNameToLookUp) {
        return null;
    }
    
    
    /** Method updateProduct updating the product for allProducts
     *@param product gets product ID from allProducts and sets
     */
    public void updateProduct(Product product) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProdID() == product.getProdID()) {
                allProducts.set(i, product);
                break;
            }
        }
    }
    
    
/** Method addPart adds part to allParts. 
 * @param  partToAdd adds part to all part if part is not null
 */
    
    
    public void addPart(Part partToAdd) {
        if (partToAdd != null) {
            allParts.add(partToAdd);
        }
    }

    
  /** Method deletePart boolean delete the part if there is a partID if not don't delete. 
   *@param partToDelete deletes part if part ID is found
   * @return true delete the part, false don't delete part
   */
    
    public boolean deletePart(Part partToDelete) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == partToDelete.getId()) {
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }
    
/** Method lookUpPart from part and get
 *@param partToLookUp looks up part in allParts and gets
 * @return return part if there's ID if not return null
 */
    
    public Part lookUpPart(int partToLookUp) {
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getId() == partToLookUp) {
                    return allParts.get(i);
                }
            }

        }
        return null;
    }

    /** Method lookUpPart from observableList Part. 
     *@param partNameToLookUp searches observable part for part
     *@return  searchPartsList
     */
    public ObservableList<Part> lookUpPart(String partNameToLookUp) {
        if (!allParts.isEmpty()) {
            ObservableList searchPartsList = FXCollections.observableArrayList();
             for (Part part : getAllParts()) {
                if (part.getName().contains(partNameToLookUp)) {
                    searchPartsList.add(part);
                }
            }
             return searchPartsList;
        }
        return null;
    }
     
    /** Method updatePart updates part to allPart. 
     *@param partToUpdate updates part in allParts with part ID
     */
    
    public void updatePart(Part partToUpdate) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == partToUpdate.id) {
                allParts.set(i, partToUpdate);
                break;
            }
        }
    }
    
/** 
 * @return allProducts
 */
    
    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }
/** 
 * @return allParts
 */
    
    public ArrayList<Part> getAllParts() {
        return allParts;
    }
    
/** 
 * @return allParts.size
 */
    
    public int partListSize() {
        return allParts.size();
    }
    
/** 
 * @return allProducts.size
 */
    
    public int productListSize() {
        return allProducts.size();
    }

}
