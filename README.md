# AMT_project
This is a simple docker - java EE application for our AMT course at HEIG-VD.

The purpose is to learn how multi-tier applications work and how to build them using Java EE and its various components (Servlets, JSPs, Beans, DAOs, DTOs,...) to produce a well-structured app.

In this project, a user is able to create an account, login and see various informations about his account. A REST API, intended for admin purpose, can be used in order to do CRUD operations on the users. To illustrate an example of utilisation of the API, an admin page displaying all the users registered can be accessed when logged in.

## Deploying the app

### Requirements
- Docker-compose
- Maven

### Deployment

Download/clone the repo, `cd` into it and just type `./deploy.sh`.

The app should be running at `localhost:9090/AMT_project/`.

### Detailed commands

If for any reason you prefer to do it manually instead of running the script, you can do the following:

#### Build the app
1. Clone or download the repo.
2. Go to the root of the directory and enter `mvn compile war:war`.

#### Deploy the app
Still at the root of the directory, enter the following commands:

1. `cp target/AMT_project.war images/wildfly/`
2. `docker-compose up --build`

## REST API
Our API is defined on our [wiki page](https://github.com/BenjaminSchubert/AMT_project/wiki/REST-API).
You can also find the postman collection (exported as collection v2) in the `scripts` folder.

## Postman script
If you want to test our API, you can import the postman collection by going on the [wiki page](https://github.com/BenjaminSchubert/AMT_project/wiki/Postman-script) and clicking on the `run in postman` button. You can also directly import it from the `scripts` folder.

## JMeter script
You can also find it in the `scripts` folder. With it, you can test the functional behavior and measure performance of the server.
Please visit our [wiki page](https://github.com/BenjaminSchubert/AMT_project/wiki/JMeter-script) to see detailed running instructions.
