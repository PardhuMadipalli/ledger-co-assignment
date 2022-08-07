# ledger-co-assignment

### Run the application by running following commands:

```shell
mvn clean install -DskipTests -q assembly:single
java -jar target/geektrust.jar input2.txt
```

Output printed on the console should be:


### To check jacoco report:

```shell
mvn clean test
mvn jacoco:report 
```

Open the file `target/site/jacoco/index.html` from your browser.

