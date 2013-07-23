This is the module to place all of your Server specific classes.

This module is used to prepare your application specifically for deployment to the AWS environment.
It includes a reference CloudFormation template as well as a target that will assemble your
application bits along with standard scripts into a standard zip structure that can be easily copied
to and executed on an AWS instance.

The first step in the process it to create a baseline AMI onto which you will install your application.
The scripts in src/main/cloud include

- baselineUbuntu.sh - which installs
  - OS upgrades.
  - Java 7 - for running your application.
  - ZIP - for managing the remote distribution of application bits.
- mysqlUbuntu.sh - which installs
  - mysql client - needed for most deployments that use RDBMS
  - mysql server - nedded for standalone or test installations where the database is locally hosted.
- .mylogin.cnf - is a file that can be used to store teh mysql password so that is not passed on the command line.

There is also a single master script which will transfer these scripts to the



