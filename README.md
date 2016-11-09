# activities-ui
[![Build Status](https://travis-ci.org/mahanhz/activities-ui-app.svg?branch=master)](https://travis-ci.org/mahanhz/activities-ui-app)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b577171d4ff34477a272dd5012c61304)](https://www.codacy.com/app/mahanhz/activities-ui-app?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mahanhz/activities-ui-app&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/mahanhz/activities-ui-app/badge.svg?branch=master)](https://coveralls.io/github/mahanhz/activities-ui-app?branch=master)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/mahanhz/activities-ui-app.svg)](http://isitmaintained.com/project/mahanhz/activities-ui-app "Average time to resolve an issue")
[![Percentage of issues still open](http://isitmaintained.com/badge/open/mahanhz/activities-ui-app.svg)](http://isitmaintained.com/project/mahanhz/activities-ui-app "Percentage of issues still open")

The purpose of this microservice is to learn about Spring Boot + (Vaadin / Angular2) + Spring Security + Hystrix.

## Why Spring Boot?
As a Java developer who likes the functionality that Spring provides it is an attractive option.

## Why Vaadin?
Web pages are developed in Java, reducing the need to have to learn a lot of client side technologies.

## Why Angular2?
An alternative to Vaadin where one has much more control (and more responsibility) over the client.

## Why Spring Security?
Spring provides an ecosystem, so it's an opportunity to learn another Spring based solution

## Why Hystrix?
To be resilient against external systems, and Spring Cloud Netflix allows for easy integration.

## Main profiles

### Offline
This profile is used when there is no network connectivity.

Example usage: 
```
gradle bootRun -Dspring.profiles.active=offline
```

### Online
This profile is used when there is network connectivity (e.g. there is access to the eureka server and config server)

Example usage:
```
gradle bootRun -Dspring.profiles.active=online,local -Deureka.client.serviceUrl.defaultZone=http://192.168.1.33:13303/eurekaServer/eureka/,http://192.168.1.34:13303/eurekaServer/eureka/
```

## URLs

**Vaadin**: http://localhost:13309/activities/ui/vaadin (vaadin/p)

**Angular2**: http://localhost:13309/activities/ui/angular/index.html (angular/p)

**Togglz**: http://localhost:13309/activities/ui/togglz-console (admin/p)

**Management**: http://192.168.1.33:13310/activities/ui/manage (admin/p)
