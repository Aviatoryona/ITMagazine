Set Up

Database
Assuming you have xampp installed, Locate the magazineapp.sql file in the  Backend/MagazineApp/db folder;
Import the sql file to create the database and tables using database management tool.
Verify the database has been created ie magazineapp 

Backend
The backend runs on PHP, ensure you have php >5.4 installed
locate the MagazineApp folder in Backend/ and copy into to htdocs in your server directory
Navigate to your browser and type http://localhost/MagazineApp/src/api.php. It should print `Unknown action`
To change database conections, open the file aviator.php, and modify the defined variables values at the top of class definition

Front End
Assuming you have android studio installed, extract and import the MagazineApp project under Frontend/ directory
Locate App.java inside the sources directory and change the APPURL to match your server IP address;
Locate nextork_security_config.xml in xml folder under res directory and change the IP address to match your servers' IP.

NB. If you are using your computer as a server, ensure your testing device and computer are in the same network.

Clean and build the project
Run the project to install into your phone or emulator