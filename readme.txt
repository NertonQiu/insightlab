
*************************************************************************************************
*                                                                                               *
* This document provides INSIGHTLAB UI Configuration HOW-TO guide
*                                                                                               *
*************************************************************************************************

Prerequisites:

1. Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files
	1.1. Download: 
		 Java 6:
		   http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html
		 Java 7:
		   http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html

	1.2. Extract JCE archive and place it's content to JAVA_HOME/jre/lib/security

2. Tomcat with configured SSL support
	Short instructions:
		2.1. Create a keystore file to store the server's private key and self-signed certificate 
			 by executing the following command:
			 	Windows:
				%JAVA_HOME%\bin\keytool -genkey -alias tomcat -keyalg RSA
				Unix:
				$JAVA_HOME/bin/keytool -genkey -alias tomcat -keyalg RSA
				and specify a password value of "changeit".
				
		2.2. Uncomment the SSL connector entry in $CATALINA_BASE/conf/server.xml (Something like that: <Connector SSLEnabled="true"
									 clientAuth="false" maxThreads="150" port="8443" 
									 protocol="HTTP/1.1" scheme="https" secure="true" 
									 sslProtocol="TLS"/>) 
	For more information visit:
		Tomcat 6: 
	  	  http://tomcat.apache.org/tomcat-6.0-doc/ssl-howto.html
		Tomcat 7:
	      http://tomcat.apache.org/tomcat-7.0-doc/ssl-howto.html

*************************************************************************************************
*                                                                                               *
* STEPS TO IMPORT Account center certificate. 
*                                                                                               *
*************************************************************************************************
1. Copy certificate from 
https://www.acxm.sethq.com (available in the folder \sso-library\trunk)
or other domain wich certificate u want to add.

You can export certificate from browser
(Firefox)Click on SSL icon -More Info -View Certificate - Details - Export

2. You need to run the following
$JAVA_HOME/bin/keytool -import -alias <some descriptive name> -file <certificate file> -keystore <path to keystore>
For MAC - keystore is $JAVA_HOME/lib/security/jssecacerts
(and JAVA_HOME on the Mac is /System/Library/Frameworks/JavaVM.framework/Versions/<your version>/Home)
For Windows/Linux - keystore is $JAVA_HOME/jre/lib/security/jssecacerts
Default password is changeit

Example:
My JAVA_HOME is located: C:\Program Files\Java\jdk1.6.0_37;

"%JAVA_HOME%\bin\keytool" -import -alias accounts.acxm.sethq.com -file "C:\Users\Viktor\Desktop\accounts.myacxiom.aws.crt" -keystore "%JAVA_HOME%\jre\lib\security\cacerts"
 
3. Verify
"%JAVA_HOME%\bin\keytool" -list  -keystore "%JAVA_HOME%\jre\lib\security\cacerts"

Warning. certificate CN has to be same as site domain.

Certificate delete example
"%JAVA_HOME%\bin\keytool" -delete -alias accounts.acxm.sethq.com -keystore "%JAVA_HOME%\jre\lib\security\cacerts"

For Mac OS X cert import exampple:
sudo keytool -import -alias acxm.sethq.com -file /development/sethq/sso-library/acxm.sethq.com.cer -keystore /System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/lib/security/cacerts

Password there is:  changeit or changeme

usefull links:
http://www.chrissearle.org/blog/technical/adding_self_signed_https_certificates_java_keystore

#import account center certificate
# for windows %JAVA_HOME%\bin\keytool
# for Unix $JAVA_HOME/bin/keytool  (also possible sudo usage)

*************************************************************************************************
*                                                                                               *
* STEPS CO INTEGRATION. 
*                                                                                               *
*************************************************************************************************
#import co certificate
#keytool -import -alias co.acxm.sethq.com -file ~/Downloads/cert/co.acxm.sethq.com 
Prerequisites:
1. CO API client written on JAVA.
How to:
	1. Access CO API via browser. 
	2. Export certificate from browser storage to your local file system folder.
	3. Open command line (Windows), or Terminal (UNIX based systems);
	4. Run "keytool -import -alias <some descriptive name> -file <certificate file> -keystore <path to keystore>"
   
	   	NOTE: This command exists in every JDK, and has to be accessible from any location in your command line, 
	   	however it's basic location is in $JAVA_HOME/bin/keytool (for Windows %JAVA_HOME%\bin\keytool)
	   
	   	NOTE 1: keystore location:
	         - MAC: $JAVA_HOME/lib/security/cacerts
	         - Linux:  $JAVA_HOME/jre/lib/security/cacerts
	         - Windows: %JAVA_HOME%\jre\lib\security\cacerts
	   
	   	NOTE 2: JAVA_HOME location on MAC:
	   		 /System/Library/Frameworks/JavaVM.framework/Versions/<your version>/Home
	   	NOTE 3: default keystore password: changeit (or changeme)
   
Examples:

	- Windows: "%JAVA_HOME%\bin\keytool" -import -alias co.acxm.sethq.com -file "C:\Users\Viktor\Desktop\co.myacxiom.aws.crt" -keystore "%JAVA_HOME%\lib\security\cacerts"
	- MAC OS X: sudo keytool -import -alias acxm.sethq.com -file /path/to/co/certificate/co.acxm.sethq.com.cer -keystore /System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/lib/security/cacerts
Usefull links:
	http://www.chrissearle.org/blog/technical/adding_self_signed_https_certificates_java_keystore



# verify in the case if self-signed sertificate is used
accept by browser certificate from global nav / cdn ?

#skipt test
mvn clean install -Dmaven.test.skip=true -DskipTests

