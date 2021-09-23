package com.example.zeromqsubcriber;

import io.insource.framework.annotation.EnableZmqSubscriber;
import io.insource.framework.annotation.QueueBinding;
import io.insource.framework.annotation.ZmqSubscriber;
import io.insource.framework.zeromq.MessageConverter;
import io.insource.framework.zeromq.MessageListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.zeromq.api.Message;

import java.io.Serializable;
import java.util.Map;

@SpringBootApplication
@EnableZmqSubscriber
public class  ZeromqSubscriberApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeromqSubscriberApplication.class, args);
	}

	@Bean
	public MessageConverter messageConverter(){
		return new VerySimpleMessageConverter();
	}
}
@ZmqSubscriber(values = {
		@QueueBinding(topic = "events", key = "MyEvent", queue = "my-events")
})
class MySubscriber implements MessageListener {
	@Override
	public void onMessage( org.zeromq.api.Message message) {
		System.out.println(message.getFirstFrame());
	}
}
class VerySimpleMessageConverter implements MessageConverter, Serializable {
	@Override
	public Message toMessage(Object obj, Map<String, String> headers) {
		return new Message(obj.toString());
	}

	@Override
	public Object fromMessage(Message message) {
		return message.popString();
	}
}