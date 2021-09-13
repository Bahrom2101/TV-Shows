package uz.mobilestudio.tvshows.listeners

import uz.mobilestudio.tvshows.models.TVShow

interface TVShowsListener {

    fun onTVShowClicked(tvShow: TVShow)

}