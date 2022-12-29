# CS306: Writing Mobile Apps - Assignment 3 (News App)

### Author: Tim Deville
### Student No: 2003506

___

Login credentials for app (Registration also works, no email verification so just requires a valid email format - stored in Firebase):

* Email: cs306@gmail.com
* Password: 2003506
___

Bonus Features
Described in CW1 (Main features):
* Weather fragment - displays the current weather at location based on search term.
* Social media integration - Ability to share articles.

 Additional Features:
* Ability to change username and password in app (and delete account).
* Ability to Search for news using keywords.
___

Design changes from CW1:
* Share & Bookmark buttons moved from newsfeed recyclerview to individual article page for a cleaner look and simpler implementation.
* Changed individual pages from activities to fragments, in line with Android recommendations. Top bar shared across fragments and altered programmatically.
___

Known Issues - If this app were to be published, the following bugs would be fixed as a priority:
* Returning from viewing an article to the news feed loses the position of the tab in the news TabLayout. 
* Article source icons - NewsData.io API returns a source id that is not formattted very well. Changing this involves an extra call to their Source API, which is a limitation on the free tier with only 200 calls/day.
* A large number of NewsAPI articles have no imageURL for displaying a recyclerview image. In App Settings, i've included a toggle button to hide these from being displayed. A better option would be to parse the URL and fetch the main image.
___

Features that could be improved/added before publishing:
* Cache articles offline and only refresh recyclerview data on pull-down/periodically. This would save calls to the API. Implemented using Room database, but would require each topic to be stored in a separate SQL table. I'd have a repository class that interacts with offline and online datasources.
* Add loading state to recyclerView when refreshing data. Add shimmer effect.
* The Bookmark feature currently 'knows' if an article is already saved and changes the icon accordingly. Could add a further press to delete the article from the database.
* Add other sign-in options for Firebase login. Send verification emails.
