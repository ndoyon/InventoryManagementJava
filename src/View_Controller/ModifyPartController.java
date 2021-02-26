
package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/** The ModifyPartController.java class implements initializable for Modify Part scene. 
 *
 * @author nick
 */

public class ModifyPartController implements Initializable {
    
Inventory inv;
Part part;

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
    
   /** Method ModifyPartController
     * @param inv inventory
     * @param part Part
     */ 
    public ModifyPartController(Inventory inv, Part part) {
        this.inv = inv;
        this.part = part;
    }

    /**
     * Method initialize initializes and sets data. 
     * @param url URL
     * @param rb Resource Bundle
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setData();
    }  
    
    
    /** 
     * Method setData sets data and text for InHouse and Outsourced. 
     */
    
    private void setData() {
        
    
        if (part instanceof InHouse) {

            InHouse part1 = (InHouse) part;
            InHouseRad.setSelected(true);
            compLabel.setText("Machine ID");
            this.partName.setText(part1.getName());
            this.partID.setText((Integer.toString(part1.getId())));
            this.partInv.setText((Integer.toString(part1.getStock())));
            this.priceCost.setText((Double.toString(part1.getPrice())));
            this.partMin.setText((Integer.toString(part1.getMin())));
            this.partMax.setText((Integer.toString(part1.getMax())));
            this.Label.setText((Integer.toString(part1.getMachineID())));

        }

        if (part instanceof Outsourced) {

            Outsourced part2 = (Outsourced) part;
            OutsourcedRad.setSelected(true);
            compLabel.setText("Company Name");
            this.partName.setText(part2.getName());
            this.partID.setText((Integer.toString(part2.getId())));
            this.partInv.setText((Integer.toString(part2.getStock())));
            this.priceCost.setText((Double.toString(part2.getPrice())));
            this.partMin.setText((Integer.toString(part2.getMin())));
            this.partMax.setText((Integer.toString(part2.getMax())));
            this.Label.setText(part2.getCompanyName());
        }
    
    }
    
      /** Method saveModifyPart mouse event  that saves part and returns to main screen. 
      * @param event mouse event save part changes or reports error with alert
      * @return alert if error
      */    
    
@FXML
    private void saveModifyPart(MouseEvent event) {
         boolean end = false;
        TextField[] fieldCount = {partInv, priceCost, partMin, partMax};
        if (InHouseRad.isSelected() || OutsourcedRad.isSelected()) {
            for (int i = 0; i < fieldCount.length; i++) {
                boolean valueError = checkValue(fieldCount[i]);
                if (valueError) {
                    end = true;
                    break;
                }
                boolean typeError = checkType(fieldCount[i]);
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
            } else if (OutsourcedRad.isSelected() && Label.getText().trim().isEmpty()) {
                Alerts.errorPart(1, Label);
                return;
            } else if (InHouseRad.isSelected() && !Label.getText().matches("[0-9]*")) {
                Alerts.errorPart(9, Label);
                return;

            } else if (InHouseRad.isSelected() & part instanceof InHouse) {
                updateItemInHouse();

            } else if (InHouseRad.isSelected() & part instanceof Outsourced) {
                updateItemInHouse();
            } else if (OutsourcedRad.isSelected() & part instanceof Outsourced) {
                updateItemOutSourced();
            } else if (OutsourcedRad.isSelected() & part instanceof InHouse) {
                updateItemOutSourced();
            }

        } else {
            Alerts.errorPart(2, null);
            return;

        }
        mainScreen(event);
    }
    
    /** Method selectInhouse selects In house on mouse click of radial button and changes label field to Machine ID. 
     *@param event Mouse event clicked
     */
    
    @FXML
    private void selectInHouse(MouseEvent event
    ) {
        compLabel.setText("Machine ID");

    }
    
    /** Method selectOutsourced selects outsourced on mouse click of radial button and changes label field to Company Name.  
     *@param event Mouse event clicked
     */
    
    @FXML
    private void selectOutSourced(MouseEvent event
    ) {
        compLabel.setText("Company Name");

    }
    
    /** 
     * Method updateItemInHouse updates item in inv by part InHouse. 
     */
    
    private void updateItemInHouse() {
        inv.updatePart(new InHouse(Integer.parseInt(partID.getText().trim()), partName.getText().trim(),
                Double.parseDouble(priceCost.getText().trim()), Integer.parseInt(partInv.getText().trim()),
                Integer.parseInt(partMin.getText().trim()), Integer.parseInt(partMax.getText().trim()), Integer.parseInt(Label.getText().trim())));
    }
    
     /** 
     * Method updateItemOutSourced updates item in inv by part Outsourced. 
     */
    private void updateItemOutSourced() {
        inv.updatePart(new Outsourced(Integer.parseInt(partID.getText().trim()), partName.getText().trim(),
                Double.parseDouble(priceCost.getText().trim()), Integer.parseInt(partInv.getText().trim()),
                Integer.parseInt(partMin.getText().trim()), Integer.parseInt(partMax.getText().trim()), Label.getText().trim()));
    }
    
    
    /** Method checkValue  checks value entered in field and alerts if not correct price. 
     *@param field checks field
     *@return error
     *@exception exception
     */
    
    private boolean checkValue(TextField field) {
        boolean error = false;
        try {
            if (field.getText().trim().isEmpty() || field.getText().trim() == null) {
                Alerts.errorPart(1, field);
                return true;
            }
            if (field == priceCost && Double.parseDouble(field.getText().trim()) <= 0.0) {
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

    /** Method cancel with alert confirmation.
     *@return true if cancel, return false if not ok */
    private boolean cancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Click ok to confirm");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
    
    /** 
     * Method mainScreen changes scene back to inventory management MainScreen. 
     *@param event event click
     */
    
     private void mainScreen(MouseEvent event) {
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

    /** Method cancelModifyPart cancels adding a part and goes to main screen. 
     *@param event mouse event with alert
     */

     @FXML
    private void cancelModifyPart(MouseEvent event
    ) {
        boolean cancel = cancel();
        if (cancel) {
            mainScreen(event);
        } else {
            return;
        }
    }
}

