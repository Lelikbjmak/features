package com.innowise.grpcc;

import com.innowise.grpcc.servie.GreeterService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcApp implements CommandLineRunner {

  private final GreeterService greeterService;

  @Override
  public void run(String... args) throws Exception {
    Server server = ServerBuilder.forPort(9090)
        .addService(greeterService)
        .build()
        .start();

    System.out.println("Server started, listening on " + 9090);
    server.awaitTermination();
  }
}
