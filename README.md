# CS306: Writing Mobile Apps - Assignment 3 (News App)

### Author: Tim Deville
### Student No: 2003506


Login credentials for app (also works for dummy Gmail account too check notifications):

* Username / Email: cs306_Tim@gmail.com
* password: 


Known Issues - If this app were to be published, the following bugs would be fixed first:
* Returning from viewing an article loses the position of the tab in the news TabLayout. I'd probably re-factor each category tab as a separate fragment.
* Article source icons - Currently the NewsData.io API returns a source id that is not formattted very well.

Features that could be improved/added before publishing:
* Cache articles offline and only refresh recyclerview data on pull-down. This would save calls to the API. Implement using Room database, but would require each tab to be stored in a separate SQL table. I'd have a repository class that interacts with offline and online datasources.
* Currently the login only serves as a barrier to entry, topic selection is stored on the device, this could be linked to the account and stored in the cloud.
* Rather than a WebView, the article could be parsed from the URL and formatted nicely in the Article fragment.
* Add animations to transitions between fragments.
* Add loading state to recyclerView when refreshing data.
* Saved articles are stored by ID order in Room database. Could attach a 'Saved At' date and edit the bookmark recycler view with a "bookmarked 10 days ago" textView.
