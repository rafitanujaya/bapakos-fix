package org.bapakos.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.bapakos.dao.KostDao;
import org.bapakos.service.KostService;

public class AdminCreateKostController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceKosField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ComboBox provinceComboBox;
    @FXML
    private ComboBox cityComboBox;
    @FXML
    private ComboBox codePosComboBox;
    @FXML
    private TextField addressField;
    @FXML
    private CheckBox acCheckBox;
    @FXML
    private CheckBox kmlCheckBox;
    @FXML
    private CheckBox kmdCheckBox;
    @FXML
    private CheckBox wifiCheckBox;

    private KostService kostService;

    public AdminCreateKostController() {}

    public void setKostService(KostService kostService) {
        this.kostService = kostService;
    }

    public void initialize() {}



}
