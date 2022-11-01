package com.lightbend.app;

import akka.NotUsed;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;
import akka.event.Logging;
import akka.grpc.Trailers;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.RequestContext;
import akka.http.javadsl.server.Route;
import akka.japi.function.Function;
import akka.stream.Materializer;
import akka.stream.SystemMaterializer;
import com.lightbend.grpc.MetricsGrpcServiceImpl;
import com.lightbend.grpc.MetricsServiceHandlerFactory;
import com.lightbend.grpc.MetricsServicePowerApiHandlerFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import akka.event.Logging;
import akka.grpc.Trailers;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.RequestContext;
import akka.http.javadsl.server.Route;
import akka.stream.Materializer;
import akka.stream.SystemMaterializer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Arrays;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import akka.grpc.javadsl.WebHandler;

import io.grpc.Status;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;

import static akka.http.javadsl.server.Directives.*;

public class MetricsGrpcServerApp {
    private static final Config appConfig = ConfigFactory.load();

    public static Behavior<NotUsed> rootBehavior() {
        return Behaviors.setup(context -> {

            ActorSystem<?> sys = context.getSystem();

            Materializer mat = SystemMaterializer.get(sys).materializer();

            Function<HttpRequest, CompletionStage<HttpResponse>> metricService =
                    MetricsServicePowerApiHandlerFactory.create(new MetricsGrpcServiceImpl(mat), sys);

            Function<HttpRequest, CompletionStage<HttpResponse>> grpcWebServiceHandlers =
                    WebHandler.grpcWebHandler(Arrays.asList(metricService), context.getSystem(), mat);

            Http.get(context.getSystem())
                    .newServerAt("127.0.0.1", 8090)
                    .bind(grpcWebServiceHandlers);

            return Behaviors.empty();
        });
    }

    public static void main(String[] args) {
        ActorSystem<NotUsed> system = ActorSystem.create(rootBehavior(), "grpc-web-server", appConfig);
        system.getWhenTerminated();
    }
}
