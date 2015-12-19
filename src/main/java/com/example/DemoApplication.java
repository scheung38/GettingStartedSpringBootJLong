//https://www.youtube.com/watch?v=sbPSjI4tt10
//Getting Started with Spring Boot Josh Long
package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.data.mongodb.repository.MongoRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication //@IWantToGoHomeEarly, makes this class a config file
public class DemoApplication {

//    class Foo {}
//    class Bar {}
//
//    @Bean Bar bar () {
//        return new Bar ();
//    }
//
//    @Bean Foo foo (Bar bar) {
//        return new Foo();
//    }


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    } // end of main

//    @Bean //Java use lambdas use below's implementation
//    CommandLineRunner runner () {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... strings) throws Exception {
//            }
//        };
//    }

    @Bean
    CommandLineRunner runner (ReservationRepository rr) {
        return args ->{
            Arrays.asList("Les, Josh, Phil".split(","))
            .forEach(n -> rr.save(new Reservation(n)));

            rr.findAll().forEach( System.out::println );
            rr.findByReservationName("Les").forEach(System.out::println);
            };
    } // end of @Bean runner

} // end of class DemoApplication


@RestController
class ReservationRestController {

    // field injection, unittest dies
    // better to use setters and constructors
    @RequestMapping("/reservations")
    Collection<Reservation> reservations() {

        return this.reservationRepository.findAll();
    }

    @Autowired
    private ReservationRepository reservationRepository;
}


@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // select * from reservations where reservation_name = : rn
    // @Query("Your own query")
    Collection<Reservation> findByReservationName (@Param("rn") String rn);

} // end of ReservationRepository

@Entity
class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private String reservationName;

    public Reservation() {
    }

    public Reservation(String reservationName) {
        this.reservationName = reservationName;
    }

    public Long getId() { return id; }

    public String getReservationName() {
        return reservationName;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservationName='" + reservationName + '\'' +
                '}';
    }
} // end of @Entity Reservation