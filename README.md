# CatBreedLookup
This Android app allows users to look up and get some information (eg. name, alternate name, description, life span, etc...) on cat breeds.

The following API is used to retrieve the data:

https://api.thecatapi.com

# Description

User sees a scrollable list of cat breeds (by name and alternate name).

This list of cat breeds is retrieved by making requests the the server; user does not need to initiate this request other than via scrolling in the list.

Each successful request made will update the list; otherwise an error dialog will be displayed.

User can then select a cat breed from the list to view an image of and details of the cat in a new screen.

User can return to the main screen to view more cat breeds.

# Skills used

Java, Kotlin, Retrofit, Moshi, RxJava, and MVI architecture.

In addition, open source library Roxie (https://github.com/ww-tech/roxie) is used to implement the reactive flow.
