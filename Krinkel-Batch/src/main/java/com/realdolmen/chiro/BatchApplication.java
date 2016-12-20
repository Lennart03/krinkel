package com.realdolmen.chiro;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:batch-development-properties.properties")
public class BatchApplication extends SpringBootServletInitializer {

//  public static void main(String[] args) {
//  SpringApplication.run(BatchApplication.class, args);
//}
	
  public static void main(String[] args) {
      SpringApplication.run(applicationClass, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
      return application.sources(applicationClass);
  }

  private static Class<BatchApplication> applicationClass = BatchApplication.class;

}