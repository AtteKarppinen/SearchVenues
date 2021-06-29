## Search Venues

This Android application searches venues near the user's location and displays them in a simple list

## FourSquare

The application uses [FourSquare endpoint](https://developer.foursquare.com/start/search) to search venues. 
If you'd like to try the application, you will have to get a client id and secret from the page and concatenate them 
following FourSquare's [documentation](https://developer.foursquare.com/docs/api-reference/venues/search/) and
finally replacing api_access_point value in [gradle.properties file](gradle.properties).

### Programming Language

Excluding one exception file, the project is made using Kotlin

#### Required Permissions

The application requires the user to grant location access for the search function to work.

#### Architecture

This project attempts to follow Model-View-Presenter (MVP) architecture as closely as possible. Also some influence has been taken from
Clean Architecture (use cases and entities represent the most pure Kotlin code with minimal references to any ecosystems or frameworks)