# Project: Upload Image Async with Java
Using Java, Spring Boot, Kafka, and MySQL, I created this project to perform image uploads with the ability to reduce the size by 1% to 100% and change the color to grey. Users with the FREE role can upload 2 images per day, while PREMIUM users have no limit.

# Programs Needed to Use This Project
	- Docker
	- Docker compose
 	- Java 21
    Note: All the tests are maded using Ubuntu 22.04.1

# How to Install and Run
	1 - Adjust the spring.mail.password field in application.properties to enable email sending (create a free account at https://sendgrid.com/en-us).
	2 - In your preferred IDE, run Maven clean, Maven update, and Maven install in this order.
  	3 - In the terminal, navigate to the main folder and run 'docker-compose build'.
  	4 - In the same folder, run 'docker-compose up -d'.
  	5 - Execute the command 'docker ps' to verify that you see 4 containers running, as shown below:
![image](https://github.com/user-attachments/assets/93fe9423-dba4-42ef-b92a-9173d1594c87)

# How to test
You can use the Swagger collection after you bring the project up by accessing the URL: http://localhost:8080/swagger-ui/index.html

# How to Copy the Images
	1 - To copy the image you uploaded, use the command: docker cp upload-image-async-service:/tmp/images/IMAGE_NAME_RETURNED_IN_THE_RESPONSE DESTINATION_FOLDER
	1 - To copy the processed image using Kafka, use the command: docker cp upload-image-async-service:/tmp/images/processed/IMAGE_NAME_RETURNED_IN_THE_RESPONSE DESTINATION_FOLDER
