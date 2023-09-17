# X Clone
![thumbnail](https://github.com/Chevinjeon/TwitterClone/assets/109643560/bdf7c576-360d-4500-bb69-e75b22627c29)

With Twitter's recent rebranding to X, this platform now has the largest user base ever for limitless interactivity.
In homage to the iconic bird, I ventured to construct my own social network. Armed with Android development and the elegance of Kotlin, I harnessed the Firebase APIs as the backbone of my network's infrastructure. This enabled seamless user management, login and signout functionalities, user profiles, tweets and retweets, home activities, and fragment screens. 


**The UI of the app has been updated in v2 according to the UI of Twitter's Android app v7.24.1**

| Version 1  | Version 2 (Twitter v7.24.1) |
| -------------  | -------------|
| ![twitter-ui-v1](https://github.com/Chevinjeon/TwitterClone/assets/109643560/1c51b600-ab46-42f1-a658-d7bb1693b3af) |  ![twitter-ui-v2](https://github.com/Chevinjeon/TwitterClone/assets/109643560/881157d4-0499-4272-838a-b72f663697c1) |


## Automatic Restart
Spring will automatically update your changes and restart the application. To use this, you need to run gradle.

Open two terminals.

terminal A
```
$ gradle bootRun
```
Edit your code after running gradle. you no longer need to bootRun again. your changes will be automatically updated

Terminal B
```
$ gradle classes
```




## Architecture

![architecture](https://github.com/Chevinjeon/TwitterClone/assets/109643560/6285c64e-8032-4f08-be49-8d9c644830a0)


### Let's delve into the technological marvels that underpin this endeavour:
| Main Features  | Description  |  Preview  | 
| -------------  | -------------| ------------- |
| Authentication | Create a new user by using the Firebase Authentication service  | ![Frame 75](https://github.com/Chevinjeon/TwitterClone/assets/109643560/2b9923d8-083d-412a-bb6d-2b62c05552bd) |
| User Profiles   | A tab to display user details and display all posts made by the user  | ![Frame 74](https://github.com/Chevinjeon/TwitterClone/assets/109643560/4d10897d-952a-4a9f-b55a-41a8966f15f4) |
| DM | Users can follow other users and send direct messages | ![Twitter Messages (1)](https://github.com/Chevinjeon/TwitterClone/assets/109643560/3bc5457a-8205-4506-95b6-81a548ebb789) |
| Create New Post | Functionality to upload new image/video as post |![Twitter Tweeting](https://github.com/Chevinjeon/TwitterClone/assets/109643560/8421ac05-1f8d-4bdd-a40c-80d7f7148e28) |

