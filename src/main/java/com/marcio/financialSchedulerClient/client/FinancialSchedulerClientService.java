package com.marcio.financialSchedulerClient.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("api")
public interface FinancialSchedulerClientService extends RemoteService {
    List<UserTO> getUsers();
    List<FullTransactionTO> getTransactions(Long userId);
    FullTransactionTO submitNewTransaction(TransactionTO transaction);
    BankAccountTO getUserBankAccount(Long userId);
}
