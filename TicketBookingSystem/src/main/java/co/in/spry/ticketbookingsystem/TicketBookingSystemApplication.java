package co.in.spry.ticketbookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScan(basePackages = "co.in.spry.ticketbookingsystem")
@EnableJpaRepositories(basePackages = "co.in.spry.ticketbookingsystem.repository")
public class TicketBookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketBookingSystemApplication.class, args);
    }

}
