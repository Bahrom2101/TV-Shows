package uz.mobilestudio.tvshows.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import uz.mobilestudio.tvshows.models.TVShow

@Dao
interface TVShowDao {

    @Query("SELECT * from tvShows")
    fun getWatchList() : Flowable<List<TVShow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWatchList(tvShow: TVShow): Completable

    @Delete
    fun removeFromWatchList(tvShow: TVShow) : Completable

    @Query("select * from tvShows where id=:tvShowId")
    fun getTVShowFromWatchlist(tvShowId:String) : Flowable<TVShow>
}