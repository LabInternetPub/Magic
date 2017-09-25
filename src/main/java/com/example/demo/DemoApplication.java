package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
	CommandLineRunner runner(ReservationRepository rr) {
		return strings -> {
			Arrays.asList("Josep,Maria,Laura,Axel,Oriol,Mireia".split(","))
					.forEach(n -> rr.save(new Reservation(n)));

			rr.findAll().forEach(System.out::println);

			rr.findByName("Mireia").forEach(System.out::println);
		};
	}
}


interface ReservationRepository extends JpaRepository<Reservation,Long>{
	Collection<Reservation> findByName(String rn);
}

@RestController
class ReservationController {

	@Autowired
	private ReservationRepository reservationRepository;

	@RequestMapping("reservations")
	private Collection<Reservation> reservations() {
		return reservationRepository.findAll();
	}
}

@Entity
class Reservation {
	@Id
	@GeneratedValue
	private Long id;


	private String name;

	public Reservation() {  //why jpa why...
	}

	public Reservation(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Reservation{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}