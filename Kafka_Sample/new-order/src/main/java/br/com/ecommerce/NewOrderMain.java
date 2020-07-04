package br.com.ecommerce;

import br.com.ecommerce.model.Order;
import br.com.ecommerce.producer.KafkaDispatcher;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (var dispatcher = new KafkaDispatcher<Order>()) {
            try(var dispatcherEmailString = new KafkaDispatcher<String>()){
            for (var i = 0; i < 10; i++) {

                var key = UUID.randomUUID().toString();
                var userId = UUID.randomUUID().toString();
                var amount = Math.random() * 5000 + 1;

                var order = new Order(key, userId, new BigDecimal(amount));

                dispatcher.send("ECOMMERCE_NEW_ORDER", key, order);

                var email = "Thank you for your order! We are processing your order!";
                dispatcherEmailString.send("ECOMMERCE_SEND_EMAIL", key, email);
             }
           }
        }

    }


}
