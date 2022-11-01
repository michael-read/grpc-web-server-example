# gRPC Web Server

This example is built with Akka gRPC Web and [akka-http-cors](https://github.com/lomigmegard/akka-http-cors) in Java (11 or 17) but is built and run using SBT.

The current server version is using the latest release of Akka 22.10, and the client is built using React v18.2.0. 

## Run the server locally

```
$ sbt
```

Then in SBT, issue the `run` command:
```
sbt:motorsports-grpc-server> run
```
The gRPC-Web server runs in the background but logs will display once the React UI starts the stream.

## Then start the client 

Directions for running UI is [here](UI/README.md) in the UI subdirectory.

