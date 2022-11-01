/**
 * @fileoverview gRPC-Web generated client stub for com.lightbend.grpc
 * @enhanceable
 * @public
 */

// Code generated by protoc-gen-grpc-web. DO NOT EDIT.
// versions:
// 	protoc-gen-grpc-web v1.4.1
// 	protoc              v3.14.0
// source: metrics.proto


/* eslint-disable */
// @ts-nocheck



const grpc = {};
grpc.web = require('grpc-web');

const proto = {};
proto.com = {};
proto.com.lightbend = {};
proto.com.lightbend.grpc = require('./metrics_pb.js');

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?grpc.web.ClientOptions} options
 * @constructor
 * @struct
 * @final
 */
proto.com.lightbend.grpc.MetricsServiceClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options.format = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname.replace(/\/+$/, '');

};


/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?grpc.web.ClientOptions} options
 * @constructor
 * @struct
 * @final
 */
proto.com.lightbend.grpc.MetricsServicePromiseClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options.format = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname.replace(/\/+$/, '');

};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.com.lightbend.grpc.StartStreamRequest,
 *   !proto.com.lightbend.grpc.MetricsResponse>}
 */
const methodDescriptor_MetricsService_StartStreamingMetrics = new grpc.web.MethodDescriptor(
  '/com.lightbend.grpc.MetricsService/StartStreamingMetrics',
  grpc.web.MethodType.SERVER_STREAMING,
  proto.com.lightbend.grpc.StartStreamRequest,
  proto.com.lightbend.grpc.MetricsResponse,
  /**
   * @param {!proto.com.lightbend.grpc.StartStreamRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.com.lightbend.grpc.MetricsResponse.deserializeBinary
);


/**
 * @param {!proto.com.lightbend.grpc.StartStreamRequest} request The request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!grpc.web.ClientReadableStream<!proto.com.lightbend.grpc.MetricsResponse>}
 *     The XHR Node Readable Stream
 */
proto.com.lightbend.grpc.MetricsServiceClient.prototype.startStreamingMetrics =
    function(request, metadata) {
  return this.client_.serverStreaming(this.hostname_ +
      '/com.lightbend.grpc.MetricsService/StartStreamingMetrics',
      request,
      metadata || {},
      methodDescriptor_MetricsService_StartStreamingMetrics);
};


/**
 * @param {!proto.com.lightbend.grpc.StartStreamRequest} request The request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!grpc.web.ClientReadableStream<!proto.com.lightbend.grpc.MetricsResponse>}
 *     The XHR Node Readable Stream
 */
proto.com.lightbend.grpc.MetricsServicePromiseClient.prototype.startStreamingMetrics =
    function(request, metadata) {
  return this.client_.serverStreaming(this.hostname_ +
      '/com.lightbend.grpc.MetricsService/StartStreamingMetrics',
      request,
      metadata || {},
      methodDescriptor_MetricsService_StartStreamingMetrics);
};


module.exports = proto.com.lightbend.grpc;

