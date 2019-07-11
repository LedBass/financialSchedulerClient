package com.marcio.financialSchedulerClient.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.marcio.financialSchedulerClient.client.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinancialSchedulerClientServiceImpl
        extends RemoteServiceServlet
        implements FinancialSchedulerClientService {

    private static Logger rootLogger = Logger.getLogger("FinancialSchedulerClientServiceImpl");

    private final String API_URL = "http://localhost:8080/api";
    private final String TRANSACTION_ENDPOINT = "/transactions";
    private final String URL_USER_COMPLEMENT = "/user";

    @Override
    public List<UserTO> getUsers() {
        String USER_ENDPOINT = "/users";
        Response response = requestGET(USER_ENDPOINT);

        if (null == checkResponse(response)) {
            rootLogger.log(Level.SEVERE, "Erro ao recuperar a lista de usuários usando a URL " + API_URL + "/users");
            return null;

        } else {
            JSONArray jsonResponse = new JSONArray(response.readEntity(String.class));
            List<UserTO> users = new ArrayList<>();

            for (Object obj: jsonResponse) {
                JSONObject jsonObject = (JSONObject) obj;
                users.add(UserTO.buildUserTOFromJSONObject(jsonObject));
            }
            return users;
        }
    }

    @Override
    public List<FullTransactionTO> getTransactions(Long userId) {
        Response response = requestGET(TRANSACTION_ENDPOINT + URL_USER_COMPLEMENT, userId.toString());

        if (null == checkResponse(response)) {
            rootLogger.log(Level.SEVERE, "Erro ao recuperar as transações do usuário " + userId + " usando a URL " + API_URL + TRANSACTION_ENDPOINT + URL_USER_COMPLEMENT);
            return null;

        } else {
            JSONArray jsonResponse = new JSONArray(response.readEntity(String.class));
            List<FullTransactionTO> transactions = new ArrayList<>();

            for (Object obj: jsonResponse) {
                JSONObject jsonObject = (JSONObject) obj;
                transactions.add(FullTransactionTO.buildFullTransactionTOFromJSONObject(jsonObject));
            }

            return transactions;
        }
    }

    @Override
    public FullTransactionTO submitNewTransaction(TransactionTO transaction) {
        Response response = requestPOST(TRANSACTION_ENDPOINT, new JSONObject(transaction).toString());

        if (null == checkResponse(response)) {
            rootLogger.log(Level.SEVERE, "Erro ao salvar uma nova transação usando a URL " + API_URL + TRANSACTION_ENDPOINT);
            return null;

        } else {
            JSONObject obj = new JSONObject(response.readEntity(String.class));
            return FullTransactionTO.buildFullTransactionTOFromJSONObject(obj);
        }
    }

    @Override
    public BankAccountTO getUserBankAccount(Long userId) {
        Response response = requestGET("/bank-accounts" + URL_USER_COMPLEMENT, userId.toString());

        if (null == checkResponse(response)) {
            rootLogger.log(Level.SEVERE, "Erro ao recuperar contas bancárias do usuário " + userId + " na URL " + API_URL + "/bank-accounts" + URL_USER_COMPLEMENT);
            return null;

        } else {
            JSONArray jsonResponse = new JSONArray(response.readEntity(String.class));
            JSONObject obj = jsonResponse.getJSONObject(0);
            return BankAccountTO.buildBankAccountToFromJSONObject(obj);
        }
    }

    private Response requestGET(String endpoint) {
        Client client = ClientBuilder.newClient();
        return client.target(API_URL + endpoint).request()
                .accept(MediaType.APPLICATION_JSON).get(Response.class);
    }

    private Response requestGET(String endpoint, String data) {
        Client client = ClientBuilder.newClient();
        return client.target(API_URL + endpoint + '/' + data).request()
                .accept(MediaType.APPLICATION_JSON).get(Response.class);
    }

    private Response requestPOST(String endpoint, String data) {
        Client client = ClientBuilder.newClient();
        return client.target(API_URL + endpoint).request()
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(data, MediaType.APPLICATION_JSON), Response.class);
    }

    private Response checkResponse(Response response) {
        if (response.getStatus() >= 400 && response.getStatus() <= 499) {
            rootLogger.log(Level.SEVERE, "Erro ao tentar recuperar dados do web service: " + response.getStatus());
            return null;

        } else if (response.getStatus() >= 200) {
            return response;

        } else {
            rootLogger.log(Level.SEVERE, "Recebido outro status que não 200: " + response.getStatus());
            return null;
        }
    }
}
