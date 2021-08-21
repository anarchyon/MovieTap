package project.paveltoy.movietap.data.repository.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import project.paveltoy.app.App
import project.paveltoy.movietap.R
import project.paveltoy.movietap.data.repository.local.FavoriteDao

private const val TABLE_NAME = "FavoriteMovies"
private const val URI_CODE = 1

class MyContentProvider : ContentProvider() {
    private var authorities: String? = null
    private lateinit var uriMatcher: UriMatcher
    private var entityContentType: String? = null
    private lateinit var contentUri: Uri

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val favoriteDao = App.getFavoriteDao()
        val id = ContentUris.parseId(uri)
        favoriteDao.deleteById(id)
        context?.contentResolver?.notifyChange(uri, null)
        return 1
    }

    override fun getType(uri: Uri): String? {
        return entityContentType
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        authorities = context?.resources?.getString(R.string.authorities)
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI(authorities, TABLE_NAME, URI_CODE)
        entityContentType = "vnd.android.cursor.dir/vnd.$authorities.$TABLE_NAME"
        contentUri = Uri.parse("content://$authorities/$TABLE_NAME")
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val favoriteDao = App.getFavoriteDao()
        val cursor = favoriteDao.getFavoriteMoviesCursor()
        cursor.setNotificationUri(context!!.contentResolver, contentUri)
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}