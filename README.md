# Mileage service, AWS Lambda function development manual (Java)

![Architecture](/images/sample-java-basic.png)

The project source includes function code and supporting resources:
- `src/main` - A Java function.
- `build.gradle` - A Gradle build file.
- `shellScript/1-create-bucket.sh`, `shellScript/2-deploy.sh`, etc. - Shell scripts that use the AWS CLI to deploy and manage the application.

Use the following instructions to deploy the mileage application.

# Requirements
- [Java 17 runtime environment (SE JRE)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Gradle 5](https://gradle.org/releases/) or [Maven 3](https://maven.apache.org/docs/history.html)
- The Bash shell. For Linux and macOS, this is included by default. In Windows 10, you can install the [Windows Subsystem for Linux](https://docs.microsoft.com/en-us/windows/wsl/install-win10) to get a Windows-integrated version of Ubuntu and Bash.
- [The AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html) v1.17 or newer.

# Setup
Download or clone this repository.

    $ git clone https://github.com/Team-Space-kbu/haram-mileage.git

To create a new bucket for deployment artifacts, run `1-create-bucket.sh`.

    haram-mileage$ ./shellScript/1-create-bucket.sh
    make_bucket: lambda-artifacts-a5e4xmplb5b22e0d

# Deploy
To deploy the application, run `2-deploy.sh`.

    haram-mileage$ ./shellScript/2-deploy.sh
    BUILD SUCCESSFUL in 1s
    Successfully packaged artifacts and wrote output template to file out.yml.
    Waiting for changeset to be created..
    Successfully created/updated stack - HandlerMileage

This script uses AWS CloudFormation to deploy the Lambda functions and an IAM role. If the AWS CloudFormation stack that contains the resources already exists, the script updates it with any changes to the template or function code.

You can also build the application with Maven. To use maven, add `mvn` to the command.

    haram-mileage$ ./shellScript/2-deploy.sh mvn
    [INFO] Scanning for projects...
    [INFO] -----------------------< space.lambda.HandlerMileage >-----------------------
    [INFO] Building HandlerMileage 1.0-SNAPSHOT
    [INFO] --------------------------------[ jar ]---------------------------------
    ...

# Test
To invoke the function, run `3-invoke.sh`.

    haram-mileage$ ./shellScript/3-invoke.sh
    {
        "StatusCode": 200,
        "ExecutedVersion": "$LATEST"
    }
    21

Let the script invoke the function a few times and then press `CRTL+C` to exit.

# Request
To make a request using Postman


    {
        "type": "search",   //student or search
        "data": "00002481"  //cardNumber
    }

AWS API gateway is created based on settings. If the request is not made in an abbreviated form, an error will occur.
    

# Cleanup
To delete the application, run `4-cleanup.sh`.

    haram-mileage$ ./shellScript/4-cleanup.sh
