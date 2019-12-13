package com.***REMOVED***.site;


import com.***REMOVED***.site.cards.Card;
import com.***REMOVED***.site.cards.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SiteApplication implements CommandLineRunner {

	@Autowired
	private CardRepository cardRepository;
	public static void main(String[] args) {
		SpringApplication.run(SiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{

		cardRepository.save(new Card("1", "2"));
		cardRepository.save(new Card("3", "4"));

		for (Card card : cardRepository.findAll()) {
			System.out.println(card);
		}

	}

}
