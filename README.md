# urlminimizer
ne8.org URL minimizer


#Install Instructions
-Set up postgres database

##Debian Instructions
-install apache2, mod_proxy
-debian: apt-get install apache2 libapache2-mod-jk
-install tomcat
-apt-get install tomcat8 tomcat8-admin

-uncomment /etc/tomcat8/tomcat-users.xml

-run a2enmod proxy*
-run a2enmod rewrite
- uncomment in /etc/tomcat8/server.xml
```
    <!-- Define an AJP 1.3 Connector on port 8009 -->
    <!--
    <Connector port="8009" protocol="AJP/1.3" redirectPort="8443" />
    -->
```
-Restart Apache
-Place property files, edit where needed
-Deploy war in tomcat,

