# CS306: Writing Mobile Apps - Assignment 3 (News App)

### Author: Tim Deville
### Student No: 2003506


Login credentials for app (Registration also works, no email verification so just requires a valid email format - stored in Firebase):

* Username / Email: cs306@gmail.com
* password: 2003506

Design changes from CW1:
* Share & Bookmark buttons moved from newsfeed recyclerview to individual article page for a cleaner look and simpler implementation.
* Changed individual pages from activities to fragments, in line with Android recommendations. Top bar shared across fragments and altered programmatically.

Known Issues - If this app were to be published, the following bugs would be fixed first:
* Returning from viewing an article to the news feed loses the position of the tab in the news TabLayout. 
* Article source icons - NewsData.io API returns a source id that is not formattted very well. Changing this involves an extra call to their Source API, which is a limitation on the free tier with only 200 calls/day.

Features that could be improved/added before publishing:
* Cache articles offline and only refresh recyclerview data on pull-down/periodically. This would save calls to the API. Implemented using Room database, but would require each tab to be stored in a separate SQL table. I'd have a repository class that interacts with offline and online datasources.
* Topic selection could be stored in Firebase cloud database.
* Rather than a WebView, the article could be parsed from the URL and formatted nicely in the article fragment.
* Add animations to transitions between fragments.
* Add loading state to recyclerView when refreshing data. Add shimmer effect.
* The Bookmark feature for each article 'knows' if the article is already saved and changes the icon on launch. Could add a further press to delete the article from the database.
* Add other sign-in options for Firebase login. Send verification emails.
