package com.example.SpringCloudStreamProducer;

import com.example.SpringCloudStreamProducer.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@EnableBinding({Source.class, Sink.class})
public class SpringCloudStreamProducerApplication implements CommandLineRunner {

    @Autowired
    private MessageChannel output;

    @Autowired
    private MessageChannel input;

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Book book = new Book(1, "attwn");
        output.send(MessageBuilder.withPayload(book).build());
        System.out.println("Book sent successfully");
    }

    @StreamListener(value = "input")
    public void consumeUpdatedMessage(@Payload Book book) {
        System.out.println("Updated book :: " + book.getBookName());
    }
}
