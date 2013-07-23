This is the module to place all of your Server specific classes.

It will automatically include your common service implementation from the "impl" and "api" peer modules.
It also imports the sample helloworld controller impl and remote test using the controller API

- To start the development server:

mvn exec:exec

- To start the production server with generated configuration:

sh start-server.sh

- To start the production server with generated configuration in debug mode:

sh start-server-debug.sh

Then, you can check the controllers deployed on the server

http://localhost:8080/rest/controllers.json



