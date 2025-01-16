package com.innowise.grpcc;

import com.innowise.grpcc.helloworld.GreeterGrpc;
import com.innowise.grpcc.helloworld.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Scheduler {

  @Scheduled(fixedRate = 5000)
  public void schedule() {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
        .usePlaintext()
        .build();

    GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);

    HelloRequest request = HelloRequest.newBuilder()
        .setName("World")
        .build();

    System.out.println(stub.sayHello(request).getMessage());

    channel.shutdown();
  }
}
