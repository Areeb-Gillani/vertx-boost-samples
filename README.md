# vertx-boost-samples
Sample project to display the usage of vertx-boost (https://github.com/Areeb-Gillani/vertx-boost/) and vertx-boost-db (https://github.com/Areeb-Gillani/vertx-boost/). Please visit these repositories to get the basic knowledge of what I am targeting in this sample code. If you don't want to check the repos then at least make sure you have basic understanding of Vertx (https://vertx.io)

### RestController
```java
@RestController
public class ExampleController extends AbstractVerticle{
    @GetMapping("/sayHi")
    public String sayHi(){
        return "hi";
    }
    @GetMapping("/sayHello")
    public String sayHello(@RequestParam("username") String user){
        return "Hello "+user;
    }
    @PostMapping("/sayHiToUser")
    public String sayHiToUser(JsonObject body){
        return "Hi! " +body.getString("username");
    }
    @PostMapping("/replyHiToUser")
    public void replyHiToUser(JsonObject body, RoutingContext context){
         vertx.eventBus().request("MyTopic", body, reply->{
            if(reply.succeeded()){
                context.json(reply.result().body());
            }
        });
    }
}
```
#### Note
- We can't call the service function directly to keep our controller lightweight, so if you want some blocking calls, use the event bus to pass them to the worker threads.
- Return in this case will be handled by vertx, which is why the controller's return type is void. We are writing the response directly to our routing context.
- @Autowired will not work in controller classes because all the controllers run on event loops, and one can't block the event loop's thread. Vertx will throw an exception if the event loop thread is blocked that is why composition is prohibited.
 
### Service
```java
@Service("ExampleWorker")
public class ExampleService extends AbstractVerticle {
    @Autowired
    DatabaseRepo myRepo;

    @Override
    public void start() {
        Vertx.currentContext().owner().eventBus().consumer("MyTopic", this::replyHiToUser);
    }

    private void replyHiToUser(Message<Object> message) {
        JsonObject vertxJsonObject = (JsonObject) message.body();
        Test t = new Test();
        t.setEntryBy("Test");
        t.setEntryTime(System.currentTimeMillis() + "");
        t.setId(Math.random() + "");
        t.setMessage("Message saved for " + vertxJsonObject.getString("username"));
        //myRepo.save(t, "Insert into Test values (#{id}, #{message}, #{entry_by}, #{entry_time})", Mapper.getTestMapper());
        myRepo.save(t, "Insert into Test values (#{id}, #{message}, #{entry_by}, #{entry_time})", TestParametersMapper.INSTANCE);
        message.reply("Data Saved");
    }
}
```
#### Note
- Service will never return anything directly; instead, it will use the reply method to return the response.
- Bind all the methods to the topics in the start method.
- Uncomment the line if you are going to develop your own mappers otherwise use this code will work.
- TestParametersMapper.INSTANCE will be automatically generated. Please refer to the last section and don't miss the right configurations.
### Model
I took the liberty of using vertx-code-gen library to generate models on the fly. You can either use the annotation based model generation or use create something similar to Mapper class /mappers/Mapper.java
```java
@DataObject
@ParametersMapped
public class Test {
    @Column(name="message")
    String message;
    ...
}
```
#### Note:
- In case of code-gen, a model should have a constructor with JsonObject as param. Also needs a package-info.java for package identification.
### Repository
### CrudRepository
```java
@Repository("MyDbConfig")
public class DatabaseRepo extends CrudRepository<ExampleModel>{
   public DatabaseRepo (String connectionName, JsonObject config){
      super(connectionName, config);
   }
    //Write other db operations here your CRUD operations are already covered above 
}
```
### Configuration
#### Step 1
To process annotations one should enable annotation processing in the respective IDE. e.g. In intelliJ File>Settings>"Search Annotation">Enable Annoation Processing
#### Step 2
Gradle has been evolving so much and most of the examples on internet are for older versions of gradle. To process relative annotations you need to have these lines in dependencies in build.gradle
```kotlin
    // To generate mapper from @DataObject annotation
    compileOnly("io.vertx:vertx-codegen:4.4.5")
    annotationProcessor("io.vertx:vertx-codegen:4.4.5:processor")
    annotationProcessor("io.vertx:vertx-sql-client-templates:4.4.5")
```
### Database
```sql
CREATE DATABASE `test_db`
CREATE TABLE `test` (
  `unique_id` varchar(100) NOT NULL,
  `message` varchar(100) DEFAULT NULL,
  `entry_time` varchar(45) DEFAULT NULL,
  `entry_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`unique_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```
