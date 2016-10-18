# urlminimizer
ne8.org URL minimizer

This is the source code behind http://ne8.org. This is plugin-based modular javaee application, very configuration and made for expansion. It will build into a war that can be deployed to any application server. 

#Build instructions
- Kick off gradle build to build war (./gradelew build on unix)
- War will be in <SOURCE>/UrlMinifierWebapp/build/libs

#Install Instructions
- Build application (use eclipse for now, gradle still WIP)
- Set up postgres database (Many tutorials exist, choose one applicable to your distribution, configure securely)
- Run DDL's (in <SOURCE>/DDL/UrlMinifierV1.sql) in the  local database.

##Debian+tomcat Instructions
- install apache2, mod_proxy
- debian: apt-get install apache2 libapache2-mod-jk
- install tomcat
- apt-get install tomcat8 tomcat8-admin
- uncomment and modify /etc/tomcat8/tomcat-users.xml
- run a2enmod proxy*
- run a2enmod rewrite
- uncomment in /etc/tomcat8/server.xml
```
    <!-- Define an AJP 1.3 Connector on port 8009 -->
    <!--
    <Connector port="8009" protocol="AJP/1.3" redirectPort="8443" />
    -->
```
- Restart Apache
- Place config files(<SOURCE>/config/), edit where needed
- Deploy war in tomcat
- Copy urlmini.xml and log4j.xml onto filesystem (in this example, 
- Modify /etc/default/tomcat8 to include location to configuration, see example, these files will go in /etc/minimizer/
```
JAVA_OPTS="-Djava.awt.headless=true -Xmx512m -XX:+UseConcMarkSweepGC -DCONFIG_XML=/etc/minimizer/urlmini.xml -Dlog4j.configurationFile=/etc/minimizer/log4j2.xml"
```
- Deploy war to tomcat 
- Configure apache to rewrite url from / to web app context root. 

#TODO
* Memory Caching plugin (common used aliases should be stored in memory)
* UI Improvements
