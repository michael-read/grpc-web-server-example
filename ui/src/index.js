// import React, { useState, useEffect } from 'react';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { MetricsServiceClient } from './proto/metrics_grpc_web_pb.js';
import { StartStreamRequest } from './proto/metrics_pb.js';
import './index.css';

var metricsService = new MetricsServiceClient("http://localhost:8090");

class MetricsServiceClass {
  constructor() {
    this.stream = null;
  }

  startStreamingMetrics(callback) {
    var request = new StartStreamRequest();
    if (this.stream != null) {
      this.stream.cancel();
    }
    this.stream = metricsService.startStreamingMetrics(request, {});
    this.stream.on('data', function(response) {
      var metrics = response.toObject();
      callback(metrics);
    });
    this.stream.on('status', function(status) {
      if (status.metadata) {
        console.log("Received metadata");
        console.log(status.metadata);
      }
    });
    this.stream.on('error', function(err) {
      console.log('Error code: '+err.code+' "'+
                                      err.message+'"');
    });
    this.stream.on('end', function() {
      console.log("stream end signal received");
    });   
  }

  stopStreamingMetrics(callback) {
    if (this.stream != null) {
      this.stream.cancel();
      this.stream = null;
      callback();
    }
  }
}

function StartButton(props) {
  return (
    <button onClick={props.onClick}>
      Start Metrics Stream
    </button>
  );
}

function StopButton(props) {
  return (
    <button onClick={props.onClick}>
      Stop Metrics Stream
    </button>
  );
}

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      timestamp: "",
      counter: 0,
      hasStarted: false,
      metricsClient: new MetricsServiceClass(),
    };
    this.handleStateChange = this.handleStateChange.bind(this);
  }

  handleStateChange(response) {
    console.log("handleStateChange:" + response.timestamp + ":" + response.counter);
    this.setState({
      timestamp: response.timestamp,
      counter: response.counter
    });
  }

  handleStartClick() {
    if (!this.state.hasStarted) {
      this.setState({
        hasStarted: true,
      });  
      this.state.metricsClient.startStreamingMetrics(this.handleStateChange);
      console.log("start button clicked...")
    }
  }

  handleStopClick() {
    if (this.state.hasStarted) {
      this.state.metricsClient.stopStreamingMetrics(() => {
        console.log("stream stopped successfully!");
        this.setState({
          hasStarted: false,
        });
      });                 
    }
  }

  render() {
    const timestamp = this.state.timestamp;
    const counter = this.state.counter;

    return (
      <div className="row">
        <div className="column">
          <StartButton
            value={this.props}
            onClick={() => this.handleStartClick()}
          />
        </div>
        <div className="column">
          <StopButton
            value={this.props}
            onClick={() => this.handleStopClick()}          
          />
        </div>
        <div className="column">
          <p>Timestamp : {timestamp}</p>
          <p>Counter: {counter}</p>
        </div>        
      </div>
    );
  }
}

// ========================================

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<App />);