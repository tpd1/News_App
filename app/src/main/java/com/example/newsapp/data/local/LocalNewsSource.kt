package com.example.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Interface for fetching and inserting saved articles in the Bookmarks fragment.
 * Designed by following Android code-labs tutorial (Room with a View):
 * https://developer.android.com/codelabs/android-room-with-a-view-kotlin
 * @param articleDao - Instance of the data access object to handle Room database calls.
 */
class LocalNewsSource(private val articleDao: ArticleDao) {

    /**
     * Inserts an article into the database.
     * @param article - The article to be inserted.
     */
    suspend fun insertArticle(article: SavedArticleEntity) {
        articleDao.insert(article)
    }

    /**
     * Deletes the selected article from the database
     * @param article - The article to be deleted.
     */
    suspend fun deleteArticle(article: SavedArticleEntity) {
        articleDao.deleteArticle(article)
    }

    fun getAllArticles(): Flow<List<SavedArticleEntity>> {
        return articleDao.getArticles()
    }

}

/**
 * Data access object interface - defines the SQL queries with method calls
 */
@Dao
interface ArticleDao {

    /**
     * Inserts the passed article into the database
     * @param article - The news article to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: SavedArticleEntity)

    /**
     * Fetches all articles from the database, sorted by ID
     */
    @Query("SELECT * FROM saved_articles_table order by artId ASC")
    fun getArticles(): Flow<List<SavedArticleEntity>>

    /**
     * Deletes all saved articles from the database.
     */
    @Query("DELETE FROM saved_articles_table")
    suspend fun deleteAll()

    /**
     * Deletes only the passed article from the database.
     * @param selectedArticle - The article to be deleted.
     */
    @Delete
    suspend fun deleteArticle(selectedArticle: SavedArticleEntity)
}

