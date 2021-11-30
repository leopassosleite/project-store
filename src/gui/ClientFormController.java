package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Client;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.ClientService;
import model.services.DepartmentService;

public class ClientFormController implements Initializable {

	private Client entity;

	private ClientService service;

	private DepartmentService departmentService;

	private List<DataChangeListener> dataChangeListener = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtDistrict;

	@FXML
	private TextField txtCity;
	
	@FXML
	private TextField txtCep;
	
	@FXML
	private TextField txtRg;
	
	@FXML
	private TextField txtCpf;
	
	@FXML
	private TextField txtPhone;

	@FXML
	private ComboBox<Department> comboBoxDepartment;

	@FXML
	private Label labelErrorName;

	@FXML
	private Label labelErrorEmail;

	@FXML
	private Label labelErrorDistrict;

	@FXML
	private Label labelErrorCity;
	
	@FXML
	private Label labelErrorCep;
	
	@FXML
	private Label labelErrorRg;
	
	@FXML
	private Label labelErrorCpf;
	
	@FXML
	private Label labelErrorPhone;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	private ObservableList<Department> obsList;

	public void setClient(Client entity) {
		this.entity = entity;
	}

	public void setServices(ClientService service, DepartmentService departmentService) {
		this.service = service;
		this.departmentService = departmentService;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListener.add(listener);
	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity wal null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListener) {
			listener.onDataChanged();
		}

	}

	private Client getFormData() {
		Client obj = new Client();

		ValidationException exception = new ValidationException("Validation error");

		obj.setId(Utils.tryparseToInt(txtId.getText()));

		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Campo Vazio");
		}
		obj.setName(txtName.getText());

		if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
			exception.addError("email", "Campo Vazio");
		}
		obj.setName(txtEmail.getText());

		if (txtDistrict.getText() == null || txtDistrict.getText().trim().equals("")) {
			exception.addError("district", "Campo Vazio");
		}
		obj.setDistrict(txtDistrict.getText());
		
		if (txtCity.getText() == null || txtCity.getText().trim().equals("")) {
			exception.addError("city", "Campo Vazio");
		}
		obj.setCity(txtCity.getText());
		
		if (txtCep.getText() == null || txtCep.getText().trim().equals("")) {
			exception.addError("cep", "Campo Vazio");
		}
		obj.setCep(Utils.tryparseToInt(txtCep.getText()));
		
		if (txtRg.getText() == null || txtRg.getText().trim().equals("")) {
			exception.addError("rg", "Campo Vazio");
		}
		obj.setRg(Utils.tryparseToInt(txtRg.getText()));
		
		if (txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "Campo Vazio");
		}
		obj.setCpf(Utils.tryparseToInt(txtCpf.getText()));
		
		if (txtPhone.getText() == null || txtPhone.getText().trim().equals("")) {
			exception.addError("phone", "Campo Vazio");
		}
		obj.setPhone(Utils.tryparseToInt(txtPhone.getText()));

		obj.setDepartment(comboBoxDepartment.getValue());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@FXML
	public void onBtCanelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
		Constraints.setTextFieldMaxLength(txtEmail, 30);
		Constraints.setTextFieldMaxLength(txtDistrict, 30);
		Constraints.setTextFieldMaxLength(txtCity, 30);
		Constraints.setTextFieldInteger(txtCep);
		Constraints.setTextFieldInteger(txtRg);
		Constraints.setTextFieldInteger(txtCpf);
		Constraints.setTextFieldInteger(txtPhone);

		initializeComboBoxDepartment();
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		txtDistrict.setText(entity.getDistrict());
		txtCity.setText(entity.getCity());
		txtCep.setText(String.valueOf(entity.getCep()));
		txtRg.setText(String.valueOf(entity.getRg()));
		txtCpf.setText(String.valueOf(entity.getCpf()));
		txtPhone.setText(String.valueOf(entity.getPhone()));
		if (entity.getDepartment() == null) {
			comboBoxDepartment.getSelectionModel().selectFirst();
		} else {
			comboBoxDepartment.setValue(entity.getDepartment());
		}
	}

	public void loadAssociatedObjects() {
		if (departmentService == null) {
			throw new IllegalStateException("Department wall null");
		}
		List<Department> list = departmentService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxDepartment.setItems(obsList);
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelErrorName.setText((fields.contains("name") ? errors.get("name") : ""));
		labelErrorEmail.setText((fields.contains("email") ? errors.get("email") : ""));
		labelErrorDistrict.setText((fields.contains("district") ? errors.get("district") : ""));
		labelErrorCity.setText((fields.contains("city") ? errors.get("city") : ""));
		labelErrorCep.setText((fields.contains("cep") ? errors.get("cep") : ""));
		labelErrorRg.setText((fields.contains("rg") ? errors.get("rg") : ""));
		labelErrorCpf.setText((fields.contains("cpf") ? errors.get("cpf") : ""));
		labelErrorPhone.setText((fields.contains("phone") ? errors.get("phone") : ""));
	}

	private void initializeComboBoxDepartment() {
		Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
			@Override
			protected void updateItem(Department item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboBoxDepartment.setCellFactory(factory);
		comboBoxDepartment.setButtonCell(factory.call(null));
	}

}