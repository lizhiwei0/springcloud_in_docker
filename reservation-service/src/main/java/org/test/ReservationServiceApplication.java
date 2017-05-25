package org.test;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
public class ReservationServiceApplication {
    
    /**
     * @param rr
     * @return
     */
    @Bean
    CommandLineRunner runner (ReservationRepository rr) {
        return args -> {
            Arrays.asList("foo","bar","orange","apple").forEach(xx->rr.save(new Reservation(xx)));
            
            rr.findAll().forEach(System.out::println);
        };
    }

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}
}

@RefreshScope
@RestController
class MessageController {
        @Value("${message}")
        private String message;
        
        @RequestMapping("/message")
        String msg() {
            return this.message;
        }
}



@RepositoryRestResource

interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @RestResource(path="by-name")
    Collection<Reservation> findReservationByName(@Param("rn") String rn);
}

@Entity
class Reservation {


	@Id
	@GeneratedValue
	private Long id;

	private String name;

	public  Reservation() {
		//
	}

	public Reservation(String reservationName) {
		this.name = reservationName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String reservationName) {
		this.name = reservationName;
	}



	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}




}
