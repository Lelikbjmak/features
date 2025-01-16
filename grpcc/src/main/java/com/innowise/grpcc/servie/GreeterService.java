package com.innowise.grpcc.servie;

import com.innowise.grpcc.helloworld.GreeterGrpc;
import com.innowise.grpcc.helloworld.HelloReply;
import com.innowise.grpcc.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class GreeterService extends GreeterGrpc.GreeterImplBase {

  @Override
  public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
    HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }
}
