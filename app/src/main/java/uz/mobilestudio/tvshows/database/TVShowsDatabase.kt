package uz.mobilestudio.tvshows.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.mobilestudio.tvshows.dao.TVShowDao
import uz.mobilestudio.tvshows.models.TVShow

@Database(entities = [TVShow::class], version = 1, exportSchema = false)
abstract class TVShowsDatabase : RoomDatabase() {

    abstract fun tvShowDao(): TVShowDao

    companion object {
        private var instance: TVShowsDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TVShowsDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    TVShowsDatabase::class.java,
                    "tv_shows_db"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return instance!!
        }
    }
}