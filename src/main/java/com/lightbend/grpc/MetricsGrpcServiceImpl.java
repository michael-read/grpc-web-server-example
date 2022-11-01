package com.lightbend.grpc;

import akka.NotUsed;
import akka.grpc.javadsl.Metadata;
import akka.stream.Materializer;
import akka.stream.javadsl.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

public class MetricsGrpcServiceImpl implements MetricsServicePowerApi {
    private static final Logger log = LoggerFactory.getLogger(MetricsGrpcServiceImpl.class);

    private final Materializer mat;

    public MetricsGrpcServiceImpl(Materializer mat) {
        this.mat = mat;
    }

    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @Override
    public Source<Metrics.MetricsResponse, NotUsed> startStreamingMetrics(Metrics.StartStreamRequest in, Metadata metadata) {
        log.info(String.format("startStreamingMetrics request received %s", metadata.toString()));
        final AtomicLong counter = new AtomicLong(1l);
        return Source.repeat(NotUsed.getInstance()).map((element) -> {
            var timestamp = sdf2.format(System.currentTimeMillis());
            log.info(String.format("sending streamed MetricResponse(%d) @ %s", counter.get(), timestamp));
            return Metrics.MetricsResponse
                .newBuilder()
                    .setTimestamp(timestamp)
                    .setCounter(counter.getAndIncrement())
                    .build();
        })
        .watchTermination((prevMatValue, completionStage) -> {
            completionStage.whenComplete(
                    (done, ex) -> {
                        if (done != null) {
                            log.info("The stream has been successfully completed or terminated.");
                        }
                        else {
                            log.error(String.format("The stream ended in failure: %s", ex.getMessage()));
                        }
                    });
            return prevMatValue;
        })
        .throttle(1, Duration.ofSeconds(1));
    }

}
