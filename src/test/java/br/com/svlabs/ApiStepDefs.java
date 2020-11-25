package br.com.svlabs;

import br.com.svlabs.api.APIConnection;
import br.com.svlabs.model.Address;
import br.com.svlabs.model.Customer;
import br.com.svlabs.util.Data;
import br.com.svlabs.util.GeradorCPF;
import com.github.javafaker.*;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author lucasns
 * @since #1.0
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestApplication.class)
@SpringBootTest
public class ApiStepDefs {

    private Faker faker;
    private ResponseEntity<Customer> customer;

    @Before
    public void setUp() {
        Data.getResourceProperties("application.properties");
        faker = new Faker(new Locale("pt-BR"));
    }

    @Given("^tento cadastrar um novo cliente$")
    public void cadastro_uma_novo_cliente() {
        Customer customer = new Customer();
        String cpf = GeradorCPF.cpf(false);
        customer.setName(faker.name().fullName());
        customer.setCpf(cpf);
        customer.setBirthdate(new SimpleDateFormat("dd/MM/yyyy").format(faker.date().birthday()));
        customer.setAddress(generateFullAddress());

        this.customer = APIConnection.postCustomer(customer);
        Data.set("cpf", cpf);
    }

    @Then("^recebo status \"([^\"]*)\"$")
    public void recebo_status(final String status) {
        Assert.assertEquals("O status não confere com o esperado", customer.getStatusCodeValue(), Integer.parseInt(status));
    }

    @And("^valido cliente cadastrado$")
    public void valido_cliente_cadastrado() {
        Assert.assertNotNull("O campo 'customerId' é nulo ou vazio", customer.getBody().getCustomerId());
    }

    @And("^valido endereço cadastrado$")
    public void valido_endereco_cadastrado() {
        Assert.assertNotNull("O campo 'Código postal' é nulo ou vazio", customer.getBody().getAddress().getZipCode());
        Assert.assertNotNull("O campo 'Logradouro' é nulo ou vazio", customer.getBody().getAddress().getStreet());
        Assert.assertNotNull("O campo 'Número' é nulo ou vazio", customer.getBody().getAddress().getNumber());
        Assert.assertNotNull("O campo 'Complemento' é nulo ou vazio", customer.getBody().getAddress().getComplement());
    }

    @Given("^tento cadastrar um cliente com CPF já cadastrado$")
    public void tento_cadastrar_um_cliente_com_cpf_ja_cadastrado() {
        Customer customer = new Customer();
        customer.setName(faker.name().fullName());
        customer.setCpf(Data.get("cpf"));
        customer.setBirthdate(new SimpleDateFormat("dd/MM/yyyy").format(faker.date().birthday()));
        customer.setAddress(generateFullAddress());

        this.customer = APIConnection.postCustomer(customer);
    }

    @Given("^tento cadastrar um cliente com zipcode inválido$")
    public void tento_cadastrar_um_cliente_com_zipcode_invalido() {
        Customer customer = new Customer();
        customer.setName(faker.name().fullName());
        customer.setCpf(Data.get("cpf"));
        customer.setBirthdate(new SimpleDateFormat("dd/MM/yyyy").format(faker.date().birthday()));

        Address address = new Address();
        address.setZipCode(faker.number().digits(9));
        address.setNumber(Integer.parseInt(faker.address().streetAddressNumber()));
        address.setStreet(faker.address().streetAddress());
        address.setComplement(faker.address().secondaryAddress());
        customer.setAddress(address);

        this.customer = APIConnection.postCustomer(customer);
    }

    @And("^valido a mensagem de erro \"([^\"]*)\"$")
    public void valido_a_mensagem_de_erro_para_cpf_cadastrado(final String mensagem) {
        Assert.assertEquals("Mensagem incorreta", Data.get("erro"), mensagem);
    }

    @Given("^tento cadastrar um cliente sem CPF$")
    public void tento_cadastrar_um_cliente_sem_cpf() {
        Customer customer = new Customer();
        customer.setName(faker.name().fullName());
        customer.setBirthdate(new SimpleDateFormat("dd/MM/yyyy").format(faker.date().birthday()));
        customer.setAddress(generateFullAddress());

        this.customer = APIConnection.postCustomer(customer);
    }

    @Given("^tento cadastrar um cliente sem Nome$")
    public void tento_cadastrar_um_cliente_sem_nome() {
        Customer customer = new Customer();
        customer.setCpf(GeradorCPF.cpf(false));
        customer.setBirthdate(new SimpleDateFormat("dd/MM/yyyy").format(faker.date().birthday()));
        customer.setAddress(generateFullAddress());

        this.customer = APIConnection.postCustomer(customer);
    }

    @Given("^tento cadastrar um cliente sem Data de nascimento$")
    public void tento_cadastrar_um_cliente_sem_data_de_nascimento() {
        Customer customer = new Customer();
        customer.setName(faker.name().fullName());
        customer.setCpf(GeradorCPF.cpf(false));
        customer.setAddress(generateFullAddress());

        this.customer = APIConnection.postCustomer(customer);
    }

    @Given("^tento cadastrar um cliente com CPF incompleto$")
    public void tento_cadastrar_um_cliente_com_cpf_incompleto() {
        Customer customer = new Customer();
        customer.setName(faker.name().fullName());
        customer.setCpf(faker.number().digits(10));
        customer.setBirthdate(new SimpleDateFormat("dd/MM/yyyy").format(faker.date().birthday()));
        customer.setAddress(generateFullAddress());

        this.customer = APIConnection.postCustomer(customer);
    }

    @Given("^tento cadastrar um cliente menor de idade$")
    public void tento_cadastrar_um_cliente_menor_de_idade() {
        Customer customer = new Customer();
        customer.setName(faker.name().fullName());
        customer.setCpf(GeradorCPF.cpf(false));
        customer.setBirthdate(new SimpleDateFormat("dd/MM/yyyy").format(faker.date().birthday(1, 15)));
        customer.setAddress(generateFullAddress());

        this.customer = APIConnection.postCustomer(customer);
    }

    @Given("^tento cadastrar um cliente com data de nascimento inválida$")
    public void tento_cadastrar_um_cliente_com_data_de_nascimento_invalida() {
        Customer customer = new Customer();
        customer.setName(faker.name().fullName());
        customer.setCpf(GeradorCPF.cpf(false));
        customer.setBirthdate(new SimpleDateFormat("dd/MM/yyyy").format(faker.date().future(1, TimeUnit.DAYS)));
        customer.setAddress(generateFullAddress());

        this.customer = APIConnection.postCustomer(customer);
    }

    @Given("^busco um cliente cadastrado$")
    public void busco_um_cliente_cadastrada() {
        customer = APIConnection.getCustomerAddress(Data.get("cpf"));
    }

    @Given("^busco um cliente com CPF nulo")
    public void busco_um_cliente_com_cpf_nulo() {
        customer = APIConnection.getCustomerAddress(null);
    }

    @Given("^busco um cliente com CPF incompleto$")
    public void busco_um_cliente_com_cpf_incompleto() {
        customer = APIConnection.getCustomerAddress(faker.number().digits(10));
    }

    @Given("^busco um cliente com CPF não cadastrado$")
    public void busco_um_cliente_com_cpf_nao_cadastrado() {
        customer = APIConnection.getCustomerAddress(GeradorCPF.cpf(false));
    }

    private Address generateFullAddress() {
        Address endereco = new Address();
        endereco.setZipCode(faker.address().zipCode());
        endereco.setNumber(Integer.parseInt(faker.address().streetAddressNumber()));
        endereco.setStreet(faker.address().streetAddress());
        endereco.setComplement(faker.address().secondaryAddress());
        return endereco;
    }
}
