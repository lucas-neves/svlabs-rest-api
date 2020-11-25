package br.com.svlabs.api;

import br.com.svlabs.model.Customer;
import br.com.svlabs.util.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lucasns
 * @since #1.0
 */
public class APIConnection {

    private static final Logger log = LoggerFactory.getLogger(APIConnection.class.getTypeName());

    public static ResponseEntity<Customer> getCustomerAddress(String cpf) {
        log.info("Buscando o endereço do cliente...");
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> vars = new HashMap<>();
        vars.put("cpf", cpf);
        try {
            return restTemplate.getForEntity(String.format("%s/%s", Data.get("app.baseurl"), "/address"), Customer.class, vars);
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            Data.set("erro", e.getResponseBodyAsString());
            return new ResponseEntity<>(e.getStatusCode());
        }
    }

    public static ResponseEntity<Customer> postCustomer(Customer customer) {
        log.info("Cadastrando o cliente...");
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.postForEntity(Data.get("app.baseurl"), customer, Customer.class);
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            Data.set("erro", e.getResponseBodyAsString());
            return new ResponseEntity<>(e.getStatusCode());
        }
    }
}
