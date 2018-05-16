package co.ke.bigfoot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.bigfoot.app.model.Greeting;

@SpringBootApplication
@RestController
public class SpringKafkaDemoApplication {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
    private KafkaTemplate<String, Greeting> greetingKafkaTemplate;
	
	@Value(value = "${message.topic.name}")
    private String topicName;
	
	@Value(value = "${greeting.topic.name}")
    private String greetingTopicName;
	
	//To receive the message as string
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Object> receiveKafkaStringMessage(@RequestParam("message") String message){
		kafkaTemplate.send(topicName, message);
		return new ResponseEntity<Object>("message received", HttpStatus.OK);
	}	
	//Listener of string message
	@KafkaListener(topics = "${message.topic.name}", groupId = "foo", containerFactory = "fooKafkaListenerContainerFactory")
    public void listenGroupFoo(String message) {
        System.out.println("Received Messasge in group 'foo': " + message);
    }
	
	//To receive the message as POJO-Greeting
	@RequestMapping(method=RequestMethod.POST, value = "/greeting")
	public ResponseEntity<Object> receiveKafakaPOJOMessage(@RequestBody Greeting greeting){
		greetingKafkaTemplate.send(greetingTopicName, greeting);
		return new ResponseEntity<Object>("Greeting received", HttpStatus.OK);
	}
	//Listener of POJO-Greeting message
	@KafkaListener(topics = "${greeting.topic.name}", containerFactory = "greetingKafkaListenerContainerFactory")
    public void greetingListener(Greeting greeting) {
        System.out.println("Recieved greeting message: " + greeting);
    }
	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaDemoApplication.class, args);
	}
}
