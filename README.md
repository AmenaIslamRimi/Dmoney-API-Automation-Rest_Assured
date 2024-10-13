# Overview
This project focuses on automating the API tests for the Dmoney Fintech website using the Rest Assured framework. The automation covers various API endpoints that handle critical financial services such as creating users (e.g., customer, agent, merchant), user(agent, customer, merchant) interactions through transaction processing such as deposite, withdraw, send money, and payment. The goal is to ensure the API endpoints are functioning as expected.

# Tools and Technologies Used
- Java
- JSON
- Rest Assured 
- TestNG
- Gradle
- Allure Reports
- IntelliJ IDEA

# Prerequisites
Before running this project, make sure you have the following installed:
- Java Development Kit (JDK) (version 8 or higher).
  - Download JDK
  - Ensure JAVA_HOME is set and included in the system PATH.
- Install Gradle that matches with the JDK version.
- TestNG: For running and managing the test scripts.
- Allure reports thats compatible with the TestNG version.
- IDE for java (IntelliJ IDEA or Eclipse IDE).

# How to Run
- Clone the Repository
  - ```git clone https://github.com/AmenaIslamRimi/Dmoney-API-Automation-Rest_Assured.git```
  - cd dmoney-api-automation
  - Run complete suite in terminal
    - gradle clean test
  - Generate the Allure report in terminal
    - allure generate allure-results --clean -o allure-report
    - allure serve allure-results

# Screenshots of Report Result
![Allure overview](https://i.postimg.cc/GhxK5SGT/allure-overview-restassured.png)
![Allure behavior](https://i.postimg.cc/4dcbb8Xd/allure-behavior-restassured.png)


      
