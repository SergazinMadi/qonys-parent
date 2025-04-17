package serg.madi.netflixeurika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NetflixEurikaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetflixEurikaApplication.class, args);
    }

}
