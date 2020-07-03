package clients;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.eclipse.paho.client.mqttv3.*;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

import solution.model.PositionKey;
import solution.model.PositionValue;

public class VehiclePositionProducer {
    public static void main(String[] args) throws MqttException {
        System.out.println("*** Starting VP Producer ***");

        Properties settings = new Properties();
        settings.put("client.id", "vp-producer");
        settings.put("bootstrap.servers", "kafka-svc:9092");
        settings.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        settings.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        final KafkaProducer<String, String> producer = new KafkaProducer<>(settings);
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("### Stopping VP Producer ###");
            producer.close();
        }));
        
        Subscriber subscriber = new Subscriber(producer);
        subscriber.start();
    }
}
