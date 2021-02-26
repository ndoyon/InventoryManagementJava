
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Class AddPartController.java implements initializable for fxml AddPart scene
 *
 * @author nick
 */
public class AddPartController implements Initializable {
    
    Inventory inv;
    
    /** Constructor AddPArtController
     *@param inv Inventory
     */
    
     public AddPartController(Inventory inv) {
        this.inv = inv;
     }
   
    @FXML
    private RadioButton InHouseRad;
    @FXML
    private RadioButton OutsourcedRad;
    @FXML
    private TextField partID;
    @FXML
    private TextField partName;
    @FXML
    private TextField partInv;
    @FXML
    private TextField priceCost;
    @FXML
    private TextField partMax;
    @FXML
    private TextField Label;
    @FXML
    private TextField partMin;
    @FXML
    private Label compLabel;

    
    
    /**
     * Method initialize initializes the controller class and generates Part ID. 
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generatePartID();
       
    }    
    
    
    /** Method clearText clears text from search field. 
     *@param event mouse event in fxml sets text empty
     */

    @FXML
    private void clearText(MouseEvent event) {
        Object source = event.getSource();
        TextField field = (TextField) source;
        field.setText("");
    }
    
    /** Method generatePartID generates a random part ID between 0 and 1000. 
     */
    
    private void generatePartID() {
        boolean match;
        Random randomNum = new Random();
        Integer num = randomNum.nextInt(1000);

        if (inv.partListSize() == 0) {
            partID.setText(num.toString());

        }
        if (inv.partListSize() == 1000) {
            Alerts.errorPart(3, null);
        } else {
            match = verifyIfTaken(num);

            if (match == false) {
                partID.setText(num.toString());
            } else {
                generatePartID();
            }
        }
    }
    
    /** Method verifyIfTaken  part is taken look up part. 
     *@return match */
     private boolean verifyIfTaken(Integer num) {
        Part match = inv.lookUpPart(num);
        return match != null;
     }
    

     /** Method saveAddPart mouse event  that saves part and returns to main screen. 
      * @param event mouse event save part changes or reports error with alert
      * @return alert if error
      */
     
    @FXML
    private void saveAddPart(MouseEvent event) {
        boolean end = false;
        TextField[] fieldCount = {partInv, priceCost, partMin, partMax};
        if (InHouseRad.isSelected() || OutsourcedRad.isSelected()) {
            for (TextField fieldCount1 : fieldCount) {
                boolean valueError = checkValue(fieldCount1);
                if (valueError) {
                    end = true;
                    break;
                }
                boolean typeError = checkType(fieldCount1);
                if (typeError) {
                    end = true;
                    break;
                }
            }
            if (partName.getText().trim().isEmpty() || partName.getText().trim().toLowerCase().equals("part name")) {
                Alerts.errorPart(4, partName);
                return;
            }
            if (Integer.parseInt(partMin.getText().trim()) > Integer.parseInt(partMax.getText().trim())) {
                Alerts.errorPart(8, partMin);
                return;
            }
            if (Integer.parseInt(partInv.getText().trim()) < Integer.parseInt(partMin.getText().trim())) {
                Alerts.errorPart(6, partInv);
                return;
            }
            if (Integer.parseInt(partInv.getText().trim()) > Integer.parseInt(partMax.getText().trim())) {
                Alerts.errorPart(7, partInv);
                return;
            }
            if (end) {
                return;
            } else if (Label.getText().trim().isEmpty() || Label.getText().trim().toLowerCase().equals("company name")) {
                Alerts.errorPart(3, Label);
                return;

            } else if (InHouseRad.isSelected() && !Label.getText().trim().matches("[0-9]*")) {
                Alerts.errorPart(9, Label);
                return;
            } else if (InHouseRad.isSelected()) {
                addInHouse();

            } else if (InHouseRad.isSelected()) {
                addOutsourced();

            }

        } else {
            Alerts.errorPart(2, null);
            return;

        }
        mainScreen(event);
    }
    
    /** Method addInHouse adds InHouse part to inv. 
     */
    
    private void addInHouse() {
        inv.addPart(new InHouse(Integer.parseInt(partID.getText().trim()), partName.getText().trim(),
                Double.parseDouble(priceCost.getText().trim()), Integer.parseInt(partInv.getText().trim()),
                Integer.parseInt(partMin.getText().trim()), Integer.parseInt(partMax.getText().trim()), (Integer.parseInt(Label.getText().trim()))));

    }
    /** Method addOutsourced adds outsourced part to inv. 
     */
    private void addOutsourced() {
        inv.addPart(new Outsourced(Integer.parseInt(partID.getText().trim()), partName.getText().trim(),
                Double.parseDouble(priceCost.getText().trim()), Integer.parseInt(partInv.getText().trim()),
                Integer.parseInt(partMin.getText().trim()), Integer.parseInt(partMax.getText().trim()), Label.getText().trim()));
    }

    /** Method selectInhouse selects In house on mouse click of radial button and changes label field to Machine ID. 
     *@param event Mouse event clicked
     */
    
    @FXML
    private void selectInHouse(MouseEvent event) {
        compLabel.setText("Machine ID");
        Label.setText("Machine ID");
    }

    /** Method selectOutsourced selects outsourced on mouse click of radial button and changes label field to Company Name.  
     *@param event Mouse event clicked
     */
    
    @FXML
    private void selectOutSourced(MouseEvent event) {
        compLabel.setText("Company Name");
        Label.setText("Company Name");
    }
        
    /** Method cancelAddPart cancels adding a part and goes to main screen. 
     *@param event mouse event with alert
     */
    @FXML
    private void cancelAddPart(MouseEvent event) {
          boolean cancel = Alerts.cancel();
        if (cancel) {
            mainScreen(event);
        }
    }

   
    /** Method mainScreen changes scene back to inventory management MainScreen. 
     *@param event event click
     */
    
    private void mainScreen(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
            MainScreenController controller = new MainScreenController(inv);

            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {

        }
    }
    
    /** Method checkValue  checks value entered in field and alerts if not correct price. 
     *@param field checks field
     *@return error
     *@exception exception
     */
    
    private boolean checkValue(TextField field) {
        boolean error = false;
        try {
            if (field.getText().trim().isEmpty() | field.getText().trim() == null) {
                Alerts.errorPart(1, field);
                return true;
            }
            if (field == priceCost && Double.parseDouble(field.getText().trim()) < 0) {
                Alerts.errorPart(5, field);
                error = true;
            }
        } catch (Exception e) {
            error = true;
            Alerts.errorPart(3, field);
            System.out.println(e);

        }
        return error;
    }

    /** Method checkType checks type entered in field and alerts if price is not correct. 
     *@param field checks field for price
     *@return true or false 
     */
    
    private boolean checkType(TextField field) {

        if (field == priceCost & !field.getText().trim().matches("\\d+(\\.\\d+)?")) {
            Alerts.errorPart(3, field);
            return true;
        }
        if (field != priceCost & !field.getText().trim().matches("[0-9]*")) {
            Alerts.errorPart(3, field);
            return true;
        }
        return false;
    }
    
   
}
