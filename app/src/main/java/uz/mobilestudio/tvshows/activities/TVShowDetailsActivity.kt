package uz.mobilestudio.tvshows.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.mobilestudio.tvshows.R
import uz.mobilestudio.tvshows.databinding.ActivityTvshowDetailsBinding

class TVShowDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityTvshowDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvshowDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}