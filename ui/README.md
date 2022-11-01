## Running Locally
```
$ npm i
$ npm start
```

## How the project was created
```
$ npx create-react-app UI
```

### install packages to project
```
$ npm install grpc-web google-protobuf --save-dev
$ npm install webpack webpack-cli --save-dev
$ npm install style-loader css-loader --save-dev
$ npm install html-webpack-plugin --save-dev
```

### Generate Protobuf Messages and Service Client Stub
To generate the protobuf message classes from our metrics.proto, from the root of the UI  directory run the following command:

```
$ protoc -I=../src/main/proto metrics.proto \
--js_out=import_style=commonjs:./src/proto
```

The import_style option passed to the --js_out flag makes sure the generated files will have CommonJS style require() statements.

To generate the service client stub file, from the root of the UI directory run this command:
```
$ protoc -I=../src/main/proto metrics.proto \
--grpc-web_out=import_style=commonjs,mode=grpcwebtext:./src/proto
```

## Build for deployment
```
$ npm run build
```


