![download](https://user-images.githubusercontent.com/26800918/215619561-91cd4cbf-cef5-4116-9c96-c1c5fd3110bc.png)

# ms-trifork-books-mgmt-dal
Microservice that implements CRUD operations on the books resource.

### For localhost compilation & packaging

- OpenJDK version 17 or
- OpenJDK 64-Bit Server VM GraalVM Community Edition 22.3.1 (for native compilation)

### For dependencies management, packaging & deploy

Apache Maven version 3.8.5

### Development Framework

Spring Boot version 3.0.2. For more information, please check [https://spring.io](https://spring.io)

### Database

H2 Database Engine version 2.1.214

## Public access to REST service in Google Cloud

ms-trifork-books-mgmt-dal is deployed and published in the following URL: [https://ms-trifork-books-mgmt-dal-gzl67i4mbq-uc.a.run.app
](https://ms-trifork-books-mgmt-dal-gzl67i4mbq-uc.a.run.app)

The next services were be used to deploy application in Google Cloud:

- Google Cloud Run - PaaS to deploy containerized applications in a serverless infraestructure
- Google Cloud IAM - SaaS to manage identity & access in Google

## Pre-requisites to localhost

If you want to install in localhost, you need the following tools/configurations:

1) Install or Download portable version of OpenJDK 17 or GraalVM version with JDK 17 support (for instance, version CE 22.3.1)

2) Configure `JAVA_HOME` environment variable, setting OpenJDK 17 or GraalVM root folder

3) Add new entry in `PATH` environment variable, setting OpenJDK 17 or GraalVM `bin` folder (recommended use JAVA_HOME variable to add this new entry)

- Example in Windows: %JAVA_HOME%\bin
- Example in Unix: $JAVA_HOME/bin

4) Add new entry in `PATH` environment variable, setting Maven root folder

- Example value in Windows: C:\apache-maven-3.8.5\bin

## Compiling, Executing & Run Unit/Integration Tests

- Run the following command to clean, compile and startup the application, inside repository rool folder: `mvn clean spring-boot:run`
- Run the following command to clean, compila and run unit tests and integration tests: `mvn clean test`

## REST Operations & OpenAPI Specification 
![OpenAPI_Logo_Pantone-1](https://user-images.githubusercontent.com/26800918/215619814-87947ee2-c05b-40b8-bc83-4306f7d25fd8.png)

You can read abount the API operations specifications and understand request & response data using any Swagger or OpenAPI client. It is recommended to use the https://editor.swagger.io/ website and copy and paste the content of the `openapi.yaml` file (inside GitLab repository root folder) on the page.

You can try calling the operations from there selecting https://ms-trifork-books-mgmt-dal-gzl67i4mbq-uc.a.run.app/ server.

For more information about how to use and execute REST operations, please download and import in Postman the collection file `./postman/Trifork_Books_API.postman_collection.json` inside GitLab repository.
