package uz.mobilestudio.tvshows.listeners

import uz.mobilestudio.tvshows.models.TVShow

interface WatchlistListener {

    fun onTVShowClicked(tvShow: TVShow)

    fun removeTVShowFromWatchlist(tvShow: TVShow, position: Int)
}