# CS306: Writing Mobile Apps - Assignment 3 (News App)

### Author: Tim Deville
### Student No: 2003506


Login credentials for app (also works for dummy Gmail account too check notifications):

* Username / Email: cs306_Tim@gmail.com
* password: 

Design changes from CW1:
* Share & Bookmark buttons moved from newsfeel recyclerview to individual article page for a cleaner look and simpler implementation.
* Changed individual pages from activities to fragments, in line with Android recommendations. Top bar shared across fragments and altered programmatically.

Known Issues - If this app were to be published, the following bugs would be fixed first:
* Returning from viewing an article to the news feed loses the position of the tab in the news TabLayout. 
* Article source icons - NewsData.io API returns a source id that is not formattted very well. Changing this involves an extra call to their Source API, which is a limitation on the free tier with only 200 calls/day.

Features that could be improved/added before publishing:
* Cache articles offline and only refresh recyclerview data on pull-down/periodically. This would save calls to the API. Implemented using Room database, but would require each tab to be stored in a separate SQL table. I'd have a repository class that interacts with offline and online datasources.
* Currently the login mainly serves as a barrier to entry, topic selection is stored on the device, this could be linked to the account and stored in the cloud.
* Rather than a WebView, the article could be parsed from the URL and formatted nicely in the article fragment.
* Add animations to transitions between fragments.
* Add loading state to recyclerView when refreshing data. Add shimmer effect.
* The Bookmark feature for each article 'knows' if the article is already saved and changes the icon on launch. This could be extended, with a further press deleting the article from the database.
