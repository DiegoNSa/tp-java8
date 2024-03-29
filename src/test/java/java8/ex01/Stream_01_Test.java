package java8.ex01;

import java8.data.Data;
import java8.data.domain.Order;
import java8.data.domain.Pizza;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Exercice 01 - Recherche
 */
public class Stream_01_Test {

    @Test
    public void test_stream_filter() throws Exception {
        List<Pizza> pizzas = new Data().getPizzas();

        // TODO rÃ©cupÃ©rer la liste des pizzas dont le prix est >= 1300
        // TODO utiliser l'API Stream
        List<Pizza> result = pizzas.stream().filter(pizza -> pizza.getPrice() >= 1300).collect(Collectors.toList());

        assertThat(result, hasSize(3));
        assertThat(result, everyItem(hasProperty("price", anyOf(equalTo(1300), greaterThan(1300)))));
    }

    @Test
    public void test_stream_anyMatch() throws Exception {

        List<Pizza> pizzas = new Data().getPizzas();

        // TODO valider si au moins une pizza Ã  un prix >= 1300
        Boolean result1 = pizzas.stream().anyMatch(pizza -> pizza.getPrice() >= 1300);

        // TODO valider si au moins une pizza Ã  un prix >= 2000
        Boolean result2 = pizzas.stream().anyMatch(pizza -> pizza.getPrice() >= 2000);

        assertThat(result1, is(true));
        assertThat(result2, is(false));
    }

    @Test
    public void test_stream_allMatch() throws Exception {

        List<Pizza> pizzas = new Data().getPizzas();

        // TODO valider que toutes les pizzas ont un prix >= 1300
        Boolean result1 = pizzas.stream().allMatch(pizza -> pizza.getPrice() >= 1300);

        // TODO valider que toutes les pizzas ont un prix >= 900
        Boolean result2 = pizzas.stream().allMatch(pizza -> pizza.getPrice() >= 900);

        assertThat(result1, is(false));
        assertThat(result2, is(true));
    }


    @Test
    public void test_stream_noneMatch() throws Exception {

        List<Pizza> pizzas = new Data().getPizzas();

        // TODO valider qu'aucune pizza n'a un prix >= 2000
        Boolean result1 = pizzas.stream().noneMatch(pizza -> pizza.getPrice() >= 2000);
;

        assertThat(result1, is(true));
    }

    @Test
    public void test_stream_filter_and_match() throws Exception {

        List<Order> orders = new Data().getOrders();

        // TODO rÃ©cupÃ©rer toutes les commandes dont
        // TODO le prÃ©nom du client est "Johnny"
        // TODO dont au moins une pizza a un prix >= 1300
        List<Order> result = orders.stream().filter(order -> order.getCustomer().getFirstname().equals("Johnny") && order.getPizzas().stream().anyMatch(p -> p.getPrice() >= 1300)).collect(Collectors.toList());

        assertThat(result, hasSize(1));
        assertThat(result.get(0), hasProperty("id", is(8)));
    }

    @Test
    public void test_stream_findFirst() throws Exception {
        List<Order> orders = new Data().getOrders();

        // TODO rÃ©cupÃ©rer une commande faite par un client dont le prÃ©nom est "Sophie"
        Optional<Order> result = orders.stream().filter((Order order) -> order.getCustomer().getFirstname().equals("Sophie")).findFirst();

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void test_stream_max() throws Exception {
        List<Pizza> pizzas = new Data().getPizzas();

        // TODO Trouver la pizza la plus chÃ¨re
        Optional<Pizza> result = pizzas.stream().max((p1,p2)->p1.getPrice().compareTo(p2.getPrice()));

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), hasProperty("id", is(5)));
        assertThat(result.get(), hasProperty("name", is("La Cannibale")));
        assertThat(result.get(), hasProperty("price", is(1550)));
    }

    @Test
    public void test_stream_min() throws Exception {
        List<Order> orders = new Data().getOrders();

        List<Pizza> pizzas = new Data().getPizzas();

        // TODO Trouver la pizza la moins chÃ¨re dont le prix est >= 950
        Optional<Pizza> result = pizzas.stream().filter(pizza -> pizza.getPrice() > 950).min((p1,p2)->p1.getPrice().compareTo(p2.getPrice()));

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), hasProperty("id", is(3)));
        assertThat(result.get(), hasProperty("name", is("La Reine")));
        assertThat(result.get(), hasProperty("price", is(1000)));
    }
    
    @Test
    public void test_sorted() throws Exception {
        List<Order> orders = new Data().getOrders();

        // TODO Trier les commandes sur le nom de famille du client
        List<Order> result = orders.stream().sorted((p1,p2) -> p2.getCustomer().getFirstname().compareTo(p1.getCustomer().getFirstname())).collect(Collectors.toList());

        assertThat(result, hasSize(8));
        
        // RÃ©sultat: les 4 premiÃ¨res commandes de la liste appartiennent Ã  M. Cotillard
        assertThat(result.get(0).getCustomer(), hasProperty("id", is(2)));
        assertThat(result.get(1).getCustomer(), hasProperty("id", is(2)));
        assertThat(result.get(2).getCustomer(), hasProperty("id", is(2)));
        assertThat(result.get(3).getCustomer(), hasProperty("id", is(2)));
        
       // RÃ©sultat: les 4 premiÃ¨res commandes de la liste appartiennent Ã  J. Halliday
        assertThat(result.get(4).getCustomer(), hasProperty("id", is(1)));
        assertThat(result.get(5).getCustomer(), hasProperty("id", is(1)));
        assertThat(result.get(6).getCustomer(), hasProperty("id", is(1)));
        assertThat(result.get(7).getCustomer(), hasProperty("id", is(1)));
        
    }
}
