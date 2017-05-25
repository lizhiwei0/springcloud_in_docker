package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



//@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@RibbonClient(name="reservation-service")
public class ReservationClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationClientApplication.class, args);
	}
	
	
	
//	@Bean
//public  PoolingHttpClientConnectionManager connManager() {
//	    PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
//	    connManager.setMaxTotal(32);
//    return connManager;
//    
//}
//	
//	
//	@Bean
//	public RequestConfig requestCfg() {
//	    
//	    RequestConfig cfg =  RequestConfig.custom()
//	            .setConnectionRequestTimeout(20000)
//	            .setConnectTimeout(20000)
//	            .setSocketTimeout(20000)
//	            .build();
//	    
//	    return cfg;
//	    
//	}
//	
//	@Bean
//	public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager connManager,RequestConfig requestCfg) {
//	    CloseableHttpClient client = HttpClientBuilder.create()
//	            .setConnectionManager(connManager)
//	            .setDefaultRequestConfig(requestCfg)
//	            .build();
//	    return client;
//	}

@Bean 
@LoadBalanced
public RestTemplate restTemplate() {
    
//    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//    factory.setHttpClient(httpClient);
    return new RestTemplate();
}
}

@RestController
@RequestMapping("/reservations")
class  ReservationServiceApiGateway {
    
    @RequestMapping(method = RequestMethod.GET, value="/names")

    public Collection<String> getReservationNames() {
        
        ParameterizedTypeReference<Resources<Reservation>> ptr = new ParameterizedTypeReference<Resources<Reservation>>() {};
        
        
       ResponseEntity<Resources<Reservation>> exchange =  this.restTemplate.exchange("http://reservation-service/reservations", 
                HttpMethod.GET,
                null,
                ptr
);
       List<String> names = new ArrayList<String>();
        exchange.getBody().getContent().stream().map(r-> r.getName())
        .forEach(x-> {
            System.out.println(x); 
        names.add(x);
        }
        );
        
        return names;
    }
    
    
    @RequestMapping(method = RequestMethod.GET, value="/{id}")

    public String getById(@PathVariable Long id) {
        
//        ParameterizedTypeReference<Resources<Reservation>> ptr = new ParameterizedTypeReference<Resources<Reservation>>() {};
//        
//        
//       ResponseEntity<Resources<Reservation>> exchange =  this.restTemplate.exchange("http://reservation-service/reservations/"+id, 
//                HttpMethod.GET,
//                null,
//                ptr
//);
//       Iterator<Reservation> ite =
//        exchange.getBody().getContent().iterator();
//       if (ite == null || !ite.hasNext()) {
//           return "NO-Data";
//       }
//        return ite.next().getName();
        
        Reservation r =   restTemplate.getForObject("http://reservation-service/reservations/"+id, Reservation.class);
        if (r != null) {
            return r.getName();
        }
        
        return "haha";
    }
    
    @Autowired
    private RestTemplate restTemplate;
}





class Reservation {
    
    private String name;
    
    private Long id;

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
    
    
}