//https://www.youtube.com/watch?v=sbPSjI4tt10
//Getting Started with Spring Boot Josh Long
package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication //@IWantToGoHomeEarly, makes this class a config file
public class DemoApplication {

    class Foo {}
    class Bar {}

    @Bean Bar bar () {
        return new Bar ();
    }

    @Bean Foo foo (Bar bar) {
        return new Foo();
    }


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

interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // select * from reservations where reservation_name = : rn
    // @Query("Your own query")
    Collection<Reservation> findByReservationName (String rn);

} // end of ReservationRepository

@Entity
class Reservation {

    public Reservation() {
    }

    public Reservation(String reservationName) {
        this.reservationName = reservationName;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String reservationName;

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