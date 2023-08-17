Point of Sail Service / JAVA 17, Spring Boot 3, MySQL

Description:
Service for tool rental by providing checkout date,
number of rental days, tool code and discount percent.
After each rent request, a Rental Agreement is produced.

The store charges a daily rental fee different for each tool type.
Some tools are free of charge on weekends or holidays.

After tool is rented tool available amount decreases by one,
and if the tool is out of the stock it will not be possible to rent.
After rental days passed the tool is considered as returned and the available
amount for that tool increases by 1.

Installation and Running:
Please make sure to have mysql server up and running.
Run the project
		mvn spring-boot:run
When running the project for the first time the schema will be created.
After running the tests ToolsRentalSystemApplicationTests db tables will be populated with data.


Provided APIs:
After running the application you may take a look at API documentation
http://localhost:8081/api-docs
http://localhost:8081/swagger-ui/index.html
please use mm/dd/yy date format (not the one shown in swagger UI)
