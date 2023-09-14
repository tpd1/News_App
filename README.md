# NewsLink - An Android News Aggregator App

### Author: Tim Deville

An native Android application created as part of a final year BSc Computer Science module at Swansea University.

The aim of the app is to provide the user with the latest news articles for selected topics and also by search term. Users can save bookmarks for later viewing, share articles to social media, and receive notifications from selected topics.

The app also allows the user to view the current weather for a selected location.

Video Demonstration: [https://youtu.be/pIQc24PyByo](https://youtu.be/94pBjzKcyMI?si=hcV6E27ca3KvzJ7X)

## Bonus Features
* Weather fragment - displays the current weather at location based on search term.
* Social media integration - Ability to share articles.

 ## Additional Features:
* Ability to change username and password in app (and delete account).
* Ability to Search for news using keywords.
* Dark theme to match system perferences (only for APK level 29 or newer).
___

## Design changes from earlier prototype:
* Share & Bookmark buttons moved from newsfeed recyclerview to individual article page for a cleaner look and simpler implementation.
* Changed individual pages from activities to fragments, in line with Android recommendations. Top bar shared across fragments and altered programmatically.
___

## Known Issues - If this app were to be published, the following bugs would be fixed as a priority:
* Article source icons - NewsData.io API returns a source id that is not formattted very well. Changing this involves an extra call to their Source API, which is a limitation on the free tier with only 200 calls/day.
* A large number of NewsAPI articles have no imageURL for displaying a recyclerview image. In App Settings, i've included a toggle button to hide these from being displayed. A better option would be to parse the HTML and fetch the main image.
___

## Features that could be improved/added before publishing:
* Cache articles offline and only refresh recyclerview data on pull-down/periodically. This would save calls to the API. Implemented using Room database, but would require each topic to be stored in a separate SQL table. I'd have a repository class that interacts with offline and online datasources.
* Add loading state to recyclerView when refreshing data. Add shimmer effect.
* The Bookmark feature currently 'knows' if an article is already saved and changes the icon accordingly. Could add a further press to delete the article from the database.
* Add other sign-in options for Firebase login. Send verification emails.


**The News API utilised in this project changed their paging implementation shortly after this app was submitted as part of a university project. This version is no longer functional in its current state and requires a future update if it were to be developed further.**
