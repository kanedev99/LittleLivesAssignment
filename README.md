# Introduction
The app list all user’s stories fetched from an JSON asset file.

# Feature
* Infinite scrolling: The app uses the Paging 3 library to implement infinite scrolling, which allows the user to load more items as they scroll down the list.

* Network API integration: The app fetches data from a local JSON file (but also build a RESTful API for mock) and displays the data in a Recyclerview.

* MVVM architecture: The app is built using the Model-View-ViewModel (MVVM) architecture, which separates the presentation logic from the business logic and data management.

* Use of Android Jetpack components: The app uses several Android Jetpack components, including LiveData, ViewModel, and DataBinding, to implement the MVVM architecture and improve the app's overall performance.

* Error handling: The app includes error handling mechanisms to handle network errors and display appropriate error messages to the user.

* Responsive UI: The app's user interface is designed to be responsive and adapts to different screen sizes and orientations.

* Scaling: Have the scalability when there are more upcoming event types.

<img src="https://github.com/kanedev99/LittleLivesAssignment/blob/paging3/app/src/main/assets/Screenshot_20230417_030908.png" alt="alt text" width="360" height="640">

# Libraries
* [Android Support Library](https://developer.android.com/topic/libraries/support-library/index.html)
* [Android Architecture Components](https://developer.android.com/arch)
* [Hilt](https://dagger.dev/hilt/) for dependency injection
* [Retrofit](http://square.github.io/retrofit) for REST api communication
* [Glide](https://github.com/bumptech/glide) for image loading
