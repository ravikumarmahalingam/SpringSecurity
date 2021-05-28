# SpringSecurity
SpringSecurity Framework

This project tells how we can protect our API's by providing spring security. 
Spring security can be configured by various ways:
1. Just by adding spring security dependency in the pom.xml file app will generate a unique password for all the API's.
2. Other way is to add userName and password in the application.properties file. 
3. Other way is to add userName and password in the Security config file with InMemory configuration. 
4. Other way is to add userName and password to the users table and then use it with JdbcUserDetailsManager.
5. Other way is to define our own entity and then add our own userDetails implementation and then override the loadByUserName function.
6. Other way is to define our own AuthenticationProvider class by overriding the authenticate method. Here if we use this then the UserDetails chain will be broken and the flow will not reach the UserDetails section. 
