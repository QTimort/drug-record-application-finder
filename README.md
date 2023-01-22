# drug-record-application-finder

## Setup

1. Clone the project
   ```
   https://github.com/QTimort/drug-record-application-finder.git
   ```

2. Copy `./font/.env.example` to `./font/.env`

### Docker Compose setup
To install Docker Compose follow the instructions at https://docs.docker.com/compose/install/

1. Open a terminal and run the command below
   ```shell
   docker-compose up
   ```

2. Wait a few moment for the service to build and start.

### Regular setup

1. Java SDK 17 and Maven 3 or above must be installed. 
   - To install Java 17 go to https://adoptium.net/temurin/releases/?version=17.
   - To install Maven 3 go to https://maven.apache.org/download.cgi 

2. Open a terminal and run the following command to create a clean build:
   - Without Test
   ```shell
      mvn clean install -DskipTests \"-Dskip.front.test=true\"
   ```
   - Or with Test
   ```shell
      mvn clean install
   ```

3. Start the back-end:
   ```shell
      mvn spring-boot:run -f ./back/pom.xml
   ```

4. Start the front-end:
   ```shell
      mvn frontend:npm@npm-run-preview -pl front
   ```

## Usage
Once you are all set:
- The application should be available at http://localhost:4284/
- The backend is served at http://localhost:8080/
- API documentation is available at http://localhost:8080/api/v1/swagger-ui/index.html