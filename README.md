# Project: Upload Image Async with Java
Using Java, Spring Boot, Kafka and MySQL, I created this project to perform an image upload with the possibility of reducing the size between 1% and 100% and changing the color to grey.

# Programs Needed to Use This Project
	- Docker
	- Docker compose
    Note: All the tests are maded using Ubuntu 22.04.1

# How to Install and Run
  	1 - In the terminal, navigate to the main folder and run 'docker-compose build'.
  	2 - In the same folder, run 'docker-compose up -d'.
  	3 - Execute the command 'docker ps' to verify that you see 4 containers running, as shown below:
![image](https://github.com/user-attachments/assets/93fe9423-dba4-42ef-b92a-9173d1594c87)

# How to test
You can use the Swagger collection after you bring the project up by accessing the URL: http://localhost:8080/swagger-ui/index.html

# How to Copy the Images
	1 - To copy the image you uploaded, use the command: docker cp upload-image-async-service:/tmp/images/IMAGE_NAME_RETURNED_IN_THE_RESPONSE DESTINATION_FOLDER
	1 - To copy the processed image using Kafka, use the command: docker cp upload-image-async-service:/tmp/images/processed/IMAGE_NAME_RETURNED_IN_THE_RESPONSE DESTINATION_FOLDER
