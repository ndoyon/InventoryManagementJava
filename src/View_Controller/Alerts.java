/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 *Class Alerts for all alerts in Inventory Management Program. 
 * @author nick
 */

public class Alerts {
    
    /** Method FieldError sets style to red border around field. 
     *@param field Text field
     */
    
    private static void fieldError(TextField field) {
        if (field != null) {
            field.setStyle("-fx-border-color: red");
        }
    }
    
    /** Method errorPart alerts for specific error with part. 
     *@param field text field using int switch code
     *@param code integer
     */
    
    public static void errorPart(int code, TextField field) {
        fieldError(field);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Part Error!");
        alert.setHeaderText("Can't add part");
        switch (code) {
            case 1: {
                alert.setContentText("Empty Field!");
                break;
            }
            case 2: {
                alert.setContentText("Please select In House or OutSourced!");
                break;
            }
            case 3: {
                alert.setContentText("Incorrect format");
                break;
            }
            case 4: {
                alert.setContentText("Invalid Name");
                break;
            }
            case 5: {
                alert.setContentText("The Value can't be zero or a negtive.");
                break;
            }
            case 6: {
                alert.setContentText("Inv can't be lower than min");
                break;
            }
            case 7: {
                alert.setContentText("Inv can't be higher than max");
                break;
            }
            case 8: {
                alert.setContentText("Min cannot be higher than max!");
                break;
            }
            case 9: {
                alert.setContentText("Machine ID must be a number");
                break;
            }
            default: {
                alert.setContentText("uh oh, Unknown error!");
                break;
            }
        }
        alert.showAndWait();
    }

    /** Method errorPart alerts for specific error with Product. 
     *@param field text field using int switch code
     *@param code int
     */
    
    public static void errorProduct(int code, TextField field) {
        fieldError(field);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Product Error");
        alert.setHeaderText("Can't add product");
        switch (code) {
            case 1: {
                alert.setContentText("Empty Field!");
                break;
            }
            case 2: {
                alert.setContentText("Part is already in this Product");
                break;
            }
            case 3: {
                alert.setContentText("Incorrect format");
                break;
            }
            case 4: {
                alert.setContentText("Invalid Name");
                break;
            }
            case 5: {
                alert.setContentText("Value can't be negative.");
                break;
            }
            case 6: {
                alert.setContentText("Product cost must not be lower than part.");
                break;
            }
            case 7: {
                alert.setContentText("Product must have one part.");
                break;
            }
            case 8: {
                alert.setContentText("Inv can't be lower than min");
                break;
            }
            case 9: {
                alert.setContentText("Inv can't be more than max");
                break;
            }
            case 10: {
                alert.setContentText("Min can't be higher than max");
                break;
            }
            default: {
                alert.setContentText("uh oh, Unknown error!");
                break;
            }
        }
        alert.showAndWait();
    }
    
    /** Method confirmationWindow pop up window confirming part to delete. 
     *@param name String name
     * @return result.get
     */
    
    public static boolean confirmationWindow(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete part");
        alert.setHeaderText("Are you sure you want to delete " + name);
        alert.setContentText("Click ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /** Method cancel pop up window prompting confirmation to cancel.
     * @return result.get
     */
    
    public static boolean cancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Click ok for yes");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /** Method infoWindow information window showing confirmation or error. 
     *@param name String
     *@param code integer
     */
    
    public static void infoWindow(int code, String name) {
        if (code != 2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmed");
            alert.setHeaderText(null);
            alert.setContentText(name + " is deleted!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred!");
        }
    }
}
