# KingKiller_p1
Basic ORM based on Hibernate with some django influence

## Simple implementation 
- add the required dependency below to your projects POM file
- create a configuration file to point towards your database like the example shown
- create a mapping file for any objects you wish to map to a given table in your database
- add the required setup lines in your code and use as shown below!

#### Simple setup in code to get you connected to a database and up and running with CRUD functions
```java
//get user object map file once for all instances of the application
static Mapper mapper = new Mapper("src/main/resources/KingKiller.cfg.xml");
//create sessions to pull from every time this class gets instanciated in the app
static SessionManager allSessions = mapper.getSessionManager();
//use the instance session to call sql commands etc.
private Session dbSession;

//create objects to replicate
this.eric = new Employee(); 
eric.setFirstName("Eric");
this.newman = new Employee();
newman.setFirstName("Newman");

dbSession.create(eric); // ----> this creates a new entry in the mapped employee table with first name eric and default values for the class elsewhere

List<Employee> queryRead = 
(List<Employee>) dbSession.readAll(eric); // ----> this reads all the entries in the mapped table

List<Employee> queryRead = 
(List<Employee>) dbSession.findByField(eric, firstName); // ----> this finds all entries in the mapped table matching the passed objects params    

dbSession.update(newman, eric); ----> this updates a target entry (eric in this case) with a source entry in the mapped class table
    
dbSession.delete(newman); // ----> this deletes a table entry matching the fields in the eric object from the mapped employee class table
    
```

### Sample configuration file - the location of this file needs to be passed to the initial mapper object
```
<configuration>
    <dbConfig>
        <url>jdbc:postgresql://somedb.cdsasdf.us-east-2.rds.amazonaws.com:5432/mydb</url>
        <username>databaseusername</username>
        <password>databasepassword</password>
    </dbConfig>
</configuration>
```

### Sample mapping file - xml file that represents the object-table relationships
```
<?xml version = "1.0" encoding = "utf-8"?>

<map>
    <class>
        <name>Account</name>
        <tablename>Accounts</tablename>
        <column>
            <objfieldname>id</objfieldname>
            <colname>account_id</colname>
            <type>int</type>
        </column>
        <column>
            <objfieldname>userId</objfieldname>
            <colname>user_id</colname>
            <type>int</type>
        </column>
        <column>
            <objfieldname>balance</objfieldname>
            <colname>balance</colname>
            <type>double</type>
        </column>
    </class>
</map>
```
### Sample pom file entry to include project as dependency
```
<dependency>
    <groupId>com.revature</groupId>
    <artifactId>KingKiller</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
