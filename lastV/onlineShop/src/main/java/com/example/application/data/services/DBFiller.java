package com.example.application.data.services;


import com.example.application.data.entities.*;
import com.example.application.data.repositories.*;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DBFiller {
    private static final int USER_LIMIT = 100;
    private static final int ARTICLE_LIMIT = 10;
    private static final int WAREHOUSE_LIMIT = 5;


    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ArticleRepository articleRepository;

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final PaymentRepository paymentRepository;
    private final VendorRepository vendorRepository;
    private final WarehouseRepository warehouseRepository;


    public DBFiller(CustomerRepository customerRepository, OrderRepository orderRepository, ArticleRepository articleRepository, /*CorporateCustomerRepository corporateCustomerRepository, PrivateCustomerRepository privateCustomerRepository,*/ DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, PaymentRepository paymentRepository, VendorRepository vendorRepository, WarehouseRepository warehouseRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.articleRepository = articleRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.paymentRepository = paymentRepository;
        this.vendorRepository = vendorRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public void fillDB() {
        insertCustomers();
        insertArticle();
        insertEmployees();
        insertWarehouse();
        insertVendors();
        insertPayments();
        insertOrders();
    }

    private void insertCustomers() {
        var domains = new String[]{
                "gmail.com",
                "protonmail.com",
                "yahoo.com"
        };

        var users = new ArrayList<Customer>();

        for (int i = 0; i < USER_LIMIT; i++) {
            var n = Faker.instance().name();
            var firstName = n.firstName();
            var lastName = n.lastName();
            var address = Faker.instance().address();
            var password = Faker.instance().random().hex();
            var email = firstName + "." + lastName + "@" + domains[i % domains.length];

            if (i < USER_LIMIT / 2) {
                var age = Faker.instance().random().nextInt(20, 55);
                var phone = Faker.instance().phoneNumber();
                var customer = new PrivateCustomer(
                        email,
                        lastName,
                        firstName,
                        address.fullAddress(),
                        password);
                users.add(customer);
            } else {
                var taxNumber = Faker.instance().number().digits(5);

                var customer = new CorporateCustomer(
                        email,
                        lastName,
                        firstName,
                        address.fullAddress(),
                        password,
                        taxNumber
                );
                users.add(customer);
            }
        }
        customerRepository.saveAll(users);
    }

    private void insertArticle() {
        var articles = new Article[ARTICLE_LIMIT];

        for (int i = 0; i < ARTICLE_LIMIT; i++) {
            var name = Faker.instance().commerce().productName();
            var price = Faker.instance().commerce().price(10, 10000).length();
            var quantity = Faker.instance().number().numberBetween(1, 100);
            articles[i] = new Article(
                    name,
                    price, quantity
            );
        }

        articleRepository.saveAll(List.of(articles));
    }

    private void insertEmployees() {

        var employees = new Employee[USER_LIMIT];

        for (int i = 0; i < USER_LIMIT; i++) {
            var n = Faker.instance().name();
            var firstName = n.firstName();
            var lastName = n.lastName();
            var address = Faker.instance().address();
            var location = address.fullAddress();

            employees[i] = new Employee(
                    location,
                    firstName + " " + lastName
            );
        }

        employeeRepository.saveAll(List.of(employees));
    }

    private void insertWarehouse() {
        var warehouses = new Warehouse[WAREHOUSE_LIMIT];

        for (int i = 0; i < WAREHOUSE_LIMIT; i++) {
            var n = Faker.instance().name();
            var firstName = n.firstName();
            var lastName = n.lastName();
            var address = Faker.instance().address();
            var location = address.fullAddress();

            warehouses[i] = new Warehouse(
                    location,
                    firstName + " " + lastName
            );
        }

        warehouseRepository.saveAll(List.of(warehouses));
    }


    private void insertOrders() {
        var orders = new ArrayList<Order>();
        List<Customer> customers = customerRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();
        List<Article> articles = articleRepository.findAll();
        List<Vendor> vendors = vendorRepository.findAll();
        List<Payment> payments = paymentRepository.findAll();

        for (int i = 0; i < 10; i++) {
            Customer customer = customers.get(i % customers.size());
            Employee employee = employees.get(i % employees.size());
            Vendor vendor = vendors.get(i % vendors.size());
            Payment payment = payments.get(i % payments.size());

            List<Article> orderArticles = articles.subList(i, i+1);

            double totalAmount = orderArticles.stream()
                    .mapToDouble(Article::getArticlePrice)
                    .sum();

            String orderDate = Faker.instance().date().past(100, TimeUnit.DAYS).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .toString();

            Order order = new Order();
            order.setTotalAmount(totalAmount);
            order.setOrderDate(orderDate);
            order.setStatus(OrderStatus.OPEN.toString());
            order.setCustomer(customer);
            order.setVendor(vendor);
            order.setPayment(payment);
            order.setEmployee(employee);

            order = orderRepository.save(order);

            for (Article article : orderArticles) {
                var quantity = Faker.instance().number().numberBetween(1, 10);
                article.setArticleQuantity(quantity);
                article.setOrder(order);
                articleRepository.save(article);
            }
            orders.add(order);
        }

        orderRepository.saveAll(orders);
    }


    private void insertVendors() {
        var vendors = new ArrayList<Vendor>();

        for (int i = 0; i < 15; i++) {
            var n = Faker.instance().name();
            var firstName = n.firstName();
            var lastName = n.lastName();
            var vendorName = firstName + " " + lastName;

            var address = Faker.instance().address();
            var location = address.fullAddress();

            var svnr = Faker.instance().number().digits(9);

            Vendor vendor = new Vendor();
            vendor.setName(vendorName);
            vendor.setAddress(location);
            vendor.setSvnr(svnr);

            vendors.add(vendor);
        }

        vendorRepository.saveAll(vendors);
    }

    private void insertPayments() {
        var payments = new ArrayList<Payment>();

        for (int i = 0; i < 15; i++) {
            double price = Double.parseDouble(Faker.instance().commerce().price(1, 10000));
            String paymentDate = Faker.instance().date().past(100, TimeUnit.DAYS).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .toString();

            String time = Faker.instance().date().past(100, TimeUnit.HOURS).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime()
                    .format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            Payment payment = new Payment();
            payment.setPrice(price);
            payment.setPaymentDate(paymentDate);
            payment.setTime(time);

            payments.add(payment);
        }

        paymentRepository.saveAll(payments);
    }

}
