package com.marcio.financialSchedulerClient.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.util.Date;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class financialSchedulerClient implements EntryPoint {

	private HorizontalPanel usersPanel = new HorizontalPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel addTransactionPanel = new VerticalPanel();
	private VerticalPanel transactionPanel = new VerticalPanel();
	private VerticalPanel fullTransactionPanel = new VerticalPanel();
	private FlexTable transactionsList = new FlexTable();
	private Label errorLabel = new Label();
	private FlexTable fullTransactionTable = new FlexTable();

	private Label title = new Label("Nova Transação");
	private Label destinationAccount = new Label("Conta de destino");
	private TextBox destAccountTextBox = new TextBox();
	private Label transactionDate = new Label("Data da transação");
	private DatePicker transactionDatePicker = new DatePicker();
	private Label transactionValue = new Label("Valor da Transação");
	private TextBox transactionValueTextBox = new TextBox();
	private Label userName = new Label();

	private List<UserTO> users;
	private List<FullTransactionTO> transactions;
	private FullTransactionTO fullTransaction;
	private UserTO selectedUser;
	private BankAccountTO selectedBankAccount;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		getUsers();
		mainPanel.add(errorLabel);
		RootPanel.get("nameFieldContainer").add(mainPanel);
	}

	private void userButtonClick(UserTO user) {
		selectedUser = user;
		userName.setText("Usuário Atual: " + user.getName());
		userName.setVisible(true);
		mainPanel.add(userName);

		retrieveUserBankAccount();
		retrieveTransactions();
	}

	private void newTransactionButton() {
		transactionPanel.clear();

		title.setVisible(true);
		destinationAccount.setVisible(true);
		transactionDate.setVisible(true);
		transactionValue.setVisible(true);

		Button send = new Button("Enviar");
		send.addClickHandler(event -> sendNewTransaction());

		addTransactionPanel.add(title);
		addTransactionPanel.add(destinationAccount);
		addTransactionPanel.add(destAccountTextBox);
		addTransactionPanel.add(transactionDate);
		addTransactionPanel.add(transactionDatePicker);
		addTransactionPanel.add(transactionValue);
		addTransactionPanel.add(transactionValueTextBox);
		addTransactionPanel.add(send);
		transactionPanel.add(addTransactionPanel);
		mainPanel.add(transactionPanel);
	}

	private void sendNewTransaction() {
		Long destAccount = Long.parseLong(destAccountTextBox.getValue());
		Date date = transactionDatePicker.getValue();
		String transactionValue = transactionValueTextBox.getValue();
		Long sourceAccount = selectedBankAccount.getId();

		DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");
		//Add one day to the date since DatePicker always gets the day prior to the chosen by the user
		date  = new Date(date.getTime() + (1000 * 60 * 60 * 24));

		TransactionTO transaction = new TransactionTO();
		transaction.setDestinationAccountId(destAccount);
		transaction.setSourceAccountId(sourceAccount);
		transaction.setScheduleDate(dtf.format(date));
		transaction.setTransactionValue(transactionValue);

		sendTransaction(transaction);
	}

	private void closeFullTransactionPanel() {
		fullTransactionTable.clear();
		fullTransactionPanel.clear();
		mainPanel.remove(fullTransactionPanel);
	}

	/* Send Request methods */
	private void retrieveTransactions() {
		FinancialSchedulerClientServiceAsync clientService = GWT.create(FinancialSchedulerClientService.class);
		clientService.getTransactions(selectedUser.getId(), new AsyncCallback<List<FullTransactionTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				transactionPanel.clear();
				errorLabel.setText(caught.getMessage());
				errorLabel.setVisible(true);
			}

			@Override
			public void onSuccess(List<FullTransactionTO> result) {
				transactionPanel.remove(transactionsList);
				mainPanel.remove(transactionPanel);
				transactionPanel.clear();

				for (int i = 0; i <transactionsList.getRowCount(); i++) {
					transactionsList.removeRow(i);
				}

				errorLabel.setVisible(false);

				transactions = result;
				Button button = new Button("Nova transação");
				button.addClickHandler(event -> newTransactionButton());
				transactionPanel.add(button);

				if (null != result && !transactions.isEmpty()) {
					transactionsList.setText(0, 0, "Data");
					transactionsList.setText(0, 1, "Valor");
					transactionsList.setText(0, 2, "Conta de Destino");

					for (int i = 0; i < transactions.size(); i++) {
						transactionsList.setText(i + 1, 0, transactions.get(i).getTransactionScheduleDate());
						transactionsList.setText(i + 1, 1, transactions.get(i).getTransactionValue().toString());
						transactionsList.setText(i + 1, 2, transactions.get(i).getDestinationAccount().getId().toString());
					}

					transactionPanel.add(transactionsList);

				} else {
					FlexTable table = new FlexTable();
					table.setText(0,0,"");
					table.setText(0,1,"");

					Label label = new Label("Nenhuma transação a ser listada.");
					label.setVisible(true);
					transactionPanel.add(label);
				}

				mainPanel.add(transactionPanel);
			}
		});
	}

	private void retrieveUserBankAccount() {
		FinancialSchedulerClientServiceAsync clientService = GWT.create(FinancialSchedulerClientService.class);
		clientService.getUserBankAccount(selectedUser.getId(), new AsyncCallback<BankAccountTO>() {
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				errorLabel.setText(caught.getMessage());
				errorLabel.setVisible(true);
			}

			@Override
			public void onSuccess(BankAccountTO result) {
				errorLabel.setVisible(false);
				selectedBankAccount = result;
			}
		});
	}
	
	private void sendTransaction(TransactionTO transactionTO) {
		FinancialSchedulerClientServiceAsync clientService = GWT.create(FinancialSchedulerClientService.class);
		clientService.submitNewTransaction(transactionTO, new AsyncCallback<FullTransactionTO>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				errorLabel.setText(caught.getMessage());
				errorLabel.setVisible(true);
			}

			@Override
			public void onSuccess(FullTransactionTO result) {
				mainPanel.remove(transactionPanel);
				fullTransactionPanel.clear();
				fullTransactionTable.clear();
				errorLabel.setVisible(false);
				fullTransaction = result;

				if (null != fullTransaction) {
					fullTransactionTable.setText(0,0, "Tipo");
					fullTransactionTable.setText(0,1, "Valor");
					fullTransactionTable.setText(1, 0, "Data da Transação");
					fullTransactionTable.setText(1,1, fullTransaction.getTransactionScheduleDate());
					fullTransactionTable.setText(2, 0, "Valor da Transação");
					fullTransactionTable.setText(2, 1, fullTransaction.getTransactionValue().toString());
					fullTransactionTable.setText(3, 0, "Valor da Taxa");
					fullTransactionTable.setText(3, 1, fullTransaction.getPaidTransactionTax().toString());
					fullTransactionTable.setText(4, 0, "Tipo de Transação");
					fullTransactionTable.setText(4, 1, fullTransaction.getTransactionType().getDescription());


				} else {
					errorLabel.setText("Não foi possível salvar a nova transação");
					errorLabel.setVisible(true);
				}

				Button closeButton = new Button("Fechar");
				closeButton.addClickHandler(event -> closeFullTransactionPanel());

				fullTransactionPanel.add(fullTransactionTable);
				fullTransactionPanel.add(closeButton);
				mainPanel.add(fullTransactionPanel);
			}
		});
	}

	private void getUsers() {
		FinancialSchedulerClientServiceAsync clientService = GWT.create(FinancialSchedulerClientService.class);
		clientService.getUsers(new AsyncCallback<List<UserTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				errorLabel.setText(caught.getMessage());
				errorLabel.setVisible(true);
			}

			@Override
			public void onSuccess(List<UserTO> result) {
				users = result;

				for (UserTO user: users) {
					Button button = new Button(user.getName());
					button.addClickHandler(event -> userButtonClick(user));
					usersPanel.add(button);
					mainPanel.add(usersPanel);
				}
			}
		});
	}
}
