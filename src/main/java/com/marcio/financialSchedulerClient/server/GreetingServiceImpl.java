package com.marcio.financialSchedulerClient.server;

import com.marcio.financialSchedulerClient.client.*;
import com.marcio.financialSchedulerClient.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
    GreetingService {

  public String greetServer(String input) throws IllegalArgumentException {
    // Verify that the input is valid.
    if (!FieldVerifier.isValidName(input)) {
      // If the input is not valid, throw an IllegalArgumentException back to
      // the client.
      throw new IllegalArgumentException(
          "Name must be at least 4 characters long");
    }

    String serverInfo = getServletContext().getServerInfo();
    String userAgent = getThreadLocalRequest().getHeader("User-Agent");

    // Escape data from the client to avoid cross-site script vulnerabilities.
    input = escapeHtml(input);
    userAgent = escapeHtml(userAgent);

    return "Hello, " + input + "!<br><br>I am running " + serverInfo
        + ".<br><br>It looks like you are using:<br>" + userAgent;
  }

  /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   *
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
        ">", "&gt;");
  }

    public static class FinancialSchedulerClientServiceImpl extends RemoteServiceServlet implements FinancialSchedulerClientService {
        private final String API_URL = "http://localhost:8080/api";
        private final String USER_ENDPOINT = "/users";
        private final String TRANSACTION_ENDPOINT = "/transactions";


        @Override
        public List<UserTO> getUsers() {
            List<UserTO> users = new ArrayList<>();
            JSONArray jsonResponse = new JSONArray(requestGet(USER_ENDPOINT));

            for (Object obj: jsonResponse) {
                JSONObject jsonObject = (JSONObject) obj;
                users.add(UserTO.buildUserTOFromJSONObject(jsonObject));
            }

            return users;
        }

        @Override
        public List<FullTransactionTO> getTransactions(Long userId) {
            List<FullTransactionTO> transactions = new ArrayList<>();
            JSONArray jsonResponse = new JSONArray(requestGet(TRANSACTION_ENDPOINT + "/user", userId.toString()));

            for (Object obj: jsonResponse) {
                JSONObject jsonObject = (JSONObject) obj;
                transactions.add(FullTransactionTO.buildFullTransactionTOFromJSONObject(jsonObject));
            }

            return transactions;
        }

        @Override
        public FullTransactionTO submitNewTransaction(TransactionTO transactionTO) {
            JSONObject transaction = new JSONObject(transactionTO);
            JSONArray jsonResponse = new JSONArray(requestPost(TRANSACTION_ENDPOINT, transaction.toString()));
            JSONObject response = jsonResponse.getJSONObject(0);

            return FullTransactionTO.buildFullTransactionTOFromJSONObject(response);
        }

        @Override
        public BankAccountTO getUserBankAccount(Long userId) {
            JSONArray jsonResponse = new JSONArray(requestPost("bank-accounts" + "/user", userId.toString()));
            JSONObject obj = jsonResponse.getJSONObject(0);

            return BankAccountTO.buildBankAccountToFromJSONObject(obj);
        }

        private String requestGet(String endpoint) {
            Client client = ClientBuilder.newClient();
            Response response = client.target(API_URL + endpoint)
                    .request().accept(MediaType.APPLICATION_JSON).get(Response.class);
            return response.readEntity(String.class);
        }

        private String requestGet(String endpoint, String dataToSend) {
            Client client = ClientBuilder.newClient();

            Response response = client.target(API_URL + endpoint).request().accept(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(dataToSend, MediaType.APPLICATION_JSON), Response.class);
            return response.readEntity(String.class);
        }

        private String requestPost(String endpoint, String dataToSend) {
            Client client = ClientBuilder.newClient();

            Response response = client.target(API_URL + endpoint).request().accept(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(dataToSend, MediaType.APPLICATION_JSON), Response.class);
            return response.readEntity(String.class);
        }
    }
}
