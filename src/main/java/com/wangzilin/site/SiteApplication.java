package com.***REMOVED***.site;

import com.***REMOVED***.site.accessingdatamongodb.CustomerRepository;
import com.***REMOVED***.site.cards.Card;
import com.***REMOVED***.site.cards.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SiteApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CardRepository cardRepository;
	public static void main(String[] args) {
		SpringApplication.run(SiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{

		cardRepository.save(new Card("1", "2"));
		cardRepository.save(new Card("3", "4"));

		//fetch all customers
//		for (Customer customer : customerRepository.findAll()) {
//			System.out.println(customer);
//		}

		for (Card card : cardRepository.findAll()) {
			System.out.println(card);
		}

		//find firstName Alice
//		System.out.println(customerRepository.findByFirstName("Alice"));
//		//find last name Smith
//		for (Customer customer : customerRepository.findByLastName("Smith")) {
//			System.out.println(customer);
//		}

//		articleRepository.save(new Article("titleName", "textfield"));
//		articleRepository.save(new Article("titleName2", "textfield2"));

	}


}
