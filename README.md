# The Meal
This Recipe App displays delicious food recipes in three main categories: Beef, Chicken, and Seafood. The app uses data from TheMealDB API and implements a clean architecture with MVVM, Retrofit, and RxJava for a smooth and scalable experience.

Features:
- Display food recipes categorized into
- Fetch data from TheMealDB API
- Reactive programming with RxJava for efficient data binding
- MVVM Architecture to ensure clean, maintainable, and scalable code
- Repository Pattern for centralized data management
- Factory Pattern for creating ViewModel instances

Tech Stack
- Retrofit: HTTP client for making API calls
- RxJava: Used for managing asynchronous data streams and data binding
- MVVM: Ensures separation of concerns and organized project structure
- Android Jetpack Components: ViewModel, LiveData

Architecture Overview
This project follows the MVVM architecture:
- Model: Responsible for managing data from the repository, including API calls and local database operations.
- ViewModel: Acts as a link between the Model and the View, providing data to the UI and handling user interaction.
- View: The activity/fragment that displays data to the user and forwards user actions to the ViewModel.
  
Data Flow
- Retrofit makes API calls to TheMealDB.
- Repository handles API response, providing data to ViewModel.
- RxJava binds the data to the ViewModel and updates the UI reactively using LiveData.
