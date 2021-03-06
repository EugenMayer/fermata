Fermata
=======

Fermata is an application that makes email testing simple and approachable by non-technical people.  Configuring and testing systems that send mail can require a significant amount of time and effort.  Fermata acts as a mail server, but instead of delivering mail to clients, it stores them for viewing in a web interface.  This allows quick testing of off-the-shelf software (both Free/OpenSource and proprietary), and user acceptance and exploratory testing by business users.

Traditional Email Testing Techniques
------------------------------------

Most of the methods traditionally used to test application mail-sending capabilities have serious drawbacks, especially in an enterprise environment:

Connect directly to a production mail server:

* Risks disrupting and confusing people not involved in testing with spurious mail

Reconfiguring tester's mail clients to a test mail server:

* Requires managing an SMTP server and POP/IMAP, accounts, etc.
* You now must manage local client mail configuration and troubleshooting.
* Enterprise-managed clients may not be able to alter their configuration.

Configuring procmail filters/whitelists to restrict recipients:

* Makes exploratory testing difficult: Who should receive mail from which addresses?

Use testing mail accounts:

* May restrict integration testing of other resources like LDAP servers.
* Requires coordination with manager of enterprise mail services.
* Difficult to manage with large/dynamic teams of people doing testing.

Status
------

Beyond basic functionality such as receiving and displaying messages, we've implemented Lucene-powered search, listings by message and by recipient, and the ability to view attachments.  Various RSS feeds are available as well, so you can keep track of messages directly through mail clients that support feeds (should work with Mail.app, Outlook, Evolution, and others).

Future work will include better integration with testing frameworks, such as Selenium.

Installation
------------

Install [sbt](http://code.google.com/p/simple-build-tool/) (Simple Build Tool), and run:

    sbt update
    sbt jetty

To create a WAR file that can be deployed to Tomcat, use:

    sbt package

and move the resulting WAR file into your webapps directory.

Standalone Operation
--------------------

An executable "standalone" WAR file is [available as well](http://github.com/scsibug/fermata/wiki).  Simply double-click, or run

    java -jar -Dport=8080 fermata-X.X-standalone.war

And fermata will start a webserver on port 8080, and an SMTP server on port 2500.

License
-------

Fermata is distributed under the BSD-3 license, see the file "LICENSE" in the project root directory.

Dependencies include

* [subethasmtp](http://code.google.com/p/subethasmtp/) (Apache 2.0 license)
* [Lift](http://liftweb.net/) (Apache 2.0 license)
* [Scala](http://www.scala-lang.org/) (Scala license (BSD-style))

Copyright
---------

Copyright (c) 2010 Greg Heartsfield.



