# OpenCyclos.

Original Cyclos source from https://sourceforge.net/projects/cyclos/files/Cyclos3/3.7.3/

Cyclos doesn't seem to come with a build system, only a bunch of source
code. This repository contains a Gradle build script for Cyclos and continues 
the development of Cyclos3 as a fork of OpenCyclos.

## Building

NOTE: Doesn't fully compile in it's current state. Help us fix that.

Only requires a JDK 8 to be installed. Will automatically download
all dependencies on first build or after dependencies are changed.

### Windows with cmd.exe

```
gradlew clean build
```

### Windows with Powershell

```
.\gradlew clean build
```

### UNIX-like operating systems (Linux, OS X, BSD) or Windows with POSIX-compatible shell

```
./gradlew clean build
```

## Configuration

The `cyclos.properties` file is contained in `src/main/resources/cyclos.properties`.
After editing it, be sure to rebuild.

## Running

Either run `build/libs/cyclos-3.7.3.war` with your favorite servlet
container or just run the build command with `tomcatRunWar` instead
of `build`.

## Changing dependencies

The `build.gradle` file contains all dependencies. Each dependency
has a link to itself on mvnrepository.com, and if you plan on updating
the dependency you should check there for the version name. Sometimes,
"artifacts" are moved to a different group or name, and mvnrepository.com
will tell you if that has happened.

## Distributions

There are two `.zip` files in `build/distributions`. One is made for deployment
to Tomcat or Jetty, and the other is standalone. The one for deployment
is called `cyclos-3.7.3.zip` and the standalone one is `cyclos-standalone-3.7.3.zip`.
To use the deployment version, follow the Cyclos documentation. To use the
standalone version, run one of the following commands from the folder you
unzipped `cyclos-standalone-3.7.3.zip` to and navigate to `localhost:8080`.

### Windows with cmd.exe or Powershell

```
apache-tomcat-{VERSION}\bin\startup
```

### UNIX-like operating systems (Linux, OS X, BSD) or Windows with POSIX-compatible shell

```
sh apache-tomcat-{VERSION}/bin/startup.sh
```
