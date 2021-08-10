This file contains instructions for running the application

1) Download all the files in a folder
2) Open up the applications.properties folder in the 'backend' directory. Here you will need to modify the database configuration to either a in memory database or connect it to an external db providing your credentials.
3) Open up two terminals. One terminal should be in the 'backend' directory and the other should be in the 'frontend'
4) In the 'backend' terminal, run 'mvn spring-boot:run' to start up the backend 
5) In the 'frontend' terminal, run npm start to start up the ui.