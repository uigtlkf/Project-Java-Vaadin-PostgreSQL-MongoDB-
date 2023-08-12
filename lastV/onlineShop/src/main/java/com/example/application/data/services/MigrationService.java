package com.example.application.data.services;

import com.example.application.data.entities.*;
import com.example.application.data.nosql.entities.*;
import com.example.application.data.nosql.repositories.*;
import com.example.application.data.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MigrationService {

    private final ArticleRepository articleRepository;
    private final ArticleNoSQLRepository articleNoSQLRepository;
    private final CustomerRepository customerRepository;
    private final CustomerNoSQLRepository customerNoSQLRepository;

    private final EmployeeRepository employeeRepository;

    private final EmployeeNoSQLRepository employeeNoSQLRepository;
    private final WarehouseNoSqlRepository warehouseNoSQLRepository;

    private final WarehouseRepository warehouseRepository;

    private final OrderRepository orderRepository;

    private final OrderNoSqlRepository orderNoSqlRepository;
    private final VendorRepository vendorRepository;
    private final VendorNoSQLRepository vendorNoSQLRepository;

    private final PaymentRepository paymentRepository;
    private final PaymentNoSQLRepository paymentNoSQLRepository;
    @Autowired
    public MigrationService(ArticleRepository articleRepository, ArticleNoSQLRepository articleNoSQLRepository, CustomerRepository customerRepository, CustomerNoSQLRepository customerNoSQLRepository, EmployeeRepository employeeRepository, EmployeeNoSQLRepository employeeNoSQLRepository, WarehouseNoSqlRepository warehouseNoSQLRepository, WarehouseRepository warehouseRepository, OrderRepository orderRepository, OrderNoSqlRepository orderNoSqlRepository, VendorRepository vendorRepository, VendorNoSQLRepository vendorNoSQLRepository, PaymentRepository paymentRepository, PaymentNoSQLRepository paymentNoSQLRepository) {
        this.articleRepository = articleRepository;
        this.articleNoSQLRepository = articleNoSQLRepository;
        this.customerRepository = customerRepository;
        this.customerNoSQLRepository = customerNoSQLRepository;
        this.employeeRepository = employeeRepository;
        this.employeeNoSQLRepository = employeeNoSQLRepository;
        this.warehouseNoSQLRepository = warehouseNoSQLRepository;
        this.warehouseRepository = warehouseRepository;
        this.orderRepository = orderRepository;
        this.orderNoSqlRepository = orderNoSqlRepository;
        this.vendorRepository = vendorRepository;
        this.vendorNoSQLRepository = vendorNoSQLRepository;
        this.paymentRepository = paymentRepository;
        this.paymentNoSQLRepository = paymentNoSQLRepository;
    }

    public void migrateToNoSQL(){
        migrateArticles();
        migrateCustomers();
        migrateOrders();
        migrateEmployees();
        migrateWarehouses();
        migrateVendors();
        migratePayments();
    }

    public void migrateCustomers() {
        customerNoSQLRepository.deleteAll();

        List<CustomerNoSQL> customerNoSQLList = new ArrayList<>();

        List<Customer> customers = customerRepository.findAll();

        for (Customer customer : customers) {
            CustomerNoSQL customerNoSQL = new CustomerNoSQL();
            customerNoSQL.setId(String.valueOf(customer.getCustomerId()));
            customerNoSQL.setAddress(customer.getAddress());
            customerNoSQL.setEmail(customer.getEmail());
            customerNoSQL.setLastname(customer.getLastname());
            customerNoSQL.setPassword(customer.getPassword());
            customerNoSQL.setSurname(customer.getSurname());
            if(customer.getVendor()!=null){
                customerNoSQL.setVendorName(customer.getVendor().getName());
            }
            if(!customer.getOrders().isEmpty()){
                customerNoSQL.setOrders(transformOrders(customer.getOrders()));
            }

            customerNoSQLList.add(customerNoSQL);
        }

        customerNoSQLRepository.saveAll(customerNoSQLList);
    }

    public void migrateOrders() {
        orderNoSqlRepository.deleteAll();

        List<OrderNoSQL> orderNoSQLList = new ArrayList<>();

        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            OrderNoSQL orderNoSQL = new OrderNoSQL();
            orderNoSQL.setStatus(order.getStatus());
            orderNoSQL.setId(String.valueOf(order.getOrderId()));
            orderNoSQL.setFulfilled(order.isFulfilled());
            orderNoSQL.setOrderDate(order.getOrderDate());
            orderNoSQL.setTotalAmount(order.getTotalAmount());
            orderNoSQL.setEmployeeId(String.valueOf(order.getEmployee().getPersonalId()));
            orderNoSQL.setPaymentId(String.valueOf(order.getPayment().getPaymentId()));
            orderNoSQL.setVendorId(order.getVendor().getName());
            orderNoSQL.setArticles(transformArticles(order.getArticles()));

            orderNoSQLList.add(orderNoSQL);
        }

        orderNoSqlRepository.saveAll(orderNoSQLList);
    }

    public void migrateEmployees() {
        employeeNoSQLRepository.deleteAll();

        List<EmployeeNoSQL> employeeNoSQLList = new ArrayList<>();

        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            EmployeeNoSQL employeeNoSQL = new EmployeeNoSQL();
            employeeNoSQL.setId(String.valueOf(employee.getPersonalId()));
            employeeNoSQL.setLocation(employee.getLocation());
            employeeNoSQL.setName(employee.getName());
            employeeNoSQL.setWarehouses(transformWarehouses(employee.getWarehouses()));

            employeeNoSQLList.add(employeeNoSQL);
        }

        employeeNoSQLRepository.saveAll(employeeNoSQLList);
    }
    public void migrateArticles() {
        articleNoSQLRepository.deleteAll();

        List<ArticleNoSQL> articleNoSQLList = new ArrayList<>();

        List<Article> articles = articleRepository.findAll();

        for (Article article : articles) {
            ArticleNoSQL articleNoSQL = new ArticleNoSQL();
            articleNoSQL.setId(String.valueOf(article.getId()));
            articleNoSQL.setArticleName(article.getArticleName());
            articleNoSQL.setArticlePrice(article.getArticlePrice());
            articleNoSQL.setArticleQuantity(article.getArticleQuantity());

            articleNoSQLList.add(articleNoSQL);
        }

        articleNoSQLRepository.saveAll(articleNoSQLList);
    }

    private List<OrderNoSQL> transformOrders(List<Order> orders) {
        List<OrderNoSQL> orderNoSQLList = new ArrayList<>();

        for (Order order : orders) {
            OrderNoSQL orderNoSQL = new OrderNoSQL();
            orderNoSQL.setId(String.valueOf(order.getOrderId()));
            //TODO Migrate order

            orderNoSQLList.add(orderNoSQL);
        }

        return orderNoSQLList;
    }

    public void migrateWarehouses() {
        warehouseNoSQLRepository.deleteAll();

        List<WarehouseNoSQL> warehouseNoSQLList = new ArrayList<>();

        List<Warehouse> warehouses = warehouseRepository.findAll();

        for (Warehouse warehouse : warehouses) {
            WarehouseNoSQL warehouseNoSQL = new WarehouseNoSQL();
            warehouseNoSQL.setId(String.valueOf(warehouse.getWarehouseId()));
            warehouseNoSQL.setEmployees(transformEmployees(warehouse.getEmployees()));

            warehouseNoSQLList.add(warehouseNoSQL);
        }

        warehouseNoSQLRepository.saveAll(warehouseNoSQLList);
    }

    public void migrateVendors() {
        vendorNoSQLRepository.deleteAll();

        List<VendorNoSQL> vendorNoSQLList = new ArrayList<>();

        List<Vendor> vendors = vendorRepository.findAll();

        for (Vendor vendor : vendors) {
            VendorNoSQL vendorNoSQL = new VendorNoSQL();
            vendorNoSQL.setId(vendor.getName());
            vendorNoSQL.setVendorName(vendor.getName());
            vendorNoSQL.setVendorAddress(vendor.getAddress());
            vendorNoSQL.setVendorSvnr(vendor.getSvnr());
            vendorNoSQL.setRelatedVendors(transformRelatedVendors(vendor.getRelatedVendors()));

            vendorNoSQLList.add(vendorNoSQL);
        }
        vendorNoSQLRepository.saveAll(vendorNoSQLList);
    }
    public void migratePayments() {
        paymentNoSQLRepository.deleteAll();

        List<PaymentNoSQL> paymentNoSQLList = new ArrayList<>();

        List<Payment> payments = paymentRepository.findAll();

        for (Payment payment : payments) {
            PaymentNoSQL paymentNoSQL = new PaymentNoSQL();
            paymentNoSQL.setId(String.valueOf(payment.getPaymentId()));
            paymentNoSQL.setPaymentDate(payment.getPaymentDate());
            paymentNoSQL.setPrice(payment.getPrice());
            paymentNoSQL.setTime(payment.getTime());

            paymentNoSQLList.add(paymentNoSQL);
        }

        paymentNoSQLRepository.saveAll(paymentNoSQLList);
    }


    private List<String> transformEmployees(List<Employee> employees) {
        return employees.stream()
                .map( employee -> String.valueOf(employee.getPersonalId()))
                .collect(Collectors.toList());
    }

    private List<String> transformRelatedVendors(List<Vendor> relatedVendors) {
        return relatedVendors.stream()
                .map(Vendor::getName)
                .collect(Collectors.toList());
    }


    private List<String> transformWarehouses(List<Warehouse> warehouses) {
        return warehouses.stream()
                .map(warehouse -> String.valueOf(warehouse.getWarehouseId()))
                .collect(Collectors.toList());
    }
    private List<ArticleNoSQL> transformArticles(List<Article> articles) {
        List<ArticleNoSQL> articleNoSQLList = new ArrayList<>();

        for (Article article : articles) {
            ArticleNoSQL articleNoSQL = new ArticleNoSQL();
            articleNoSQL.setId(String.valueOf(article.getId()));
            articleNoSQL.setArticleName(article.getArticleName());
            articleNoSQL.setArticlePrice(article.getArticlePrice());
            articleNoSQL.setArticleQuantity(article.getArticleQuantity());

            articleNoSQLList.add(articleNoSQL);
        }

        return articleNoSQLList;
    }
}
