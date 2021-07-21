package project.paveltoy.movietap.data

import android.content.res.Resources
import project.paveltoy.movietap.R

class MovieStandardSubunits(resources: Resources) : Subunits {
    val subunits: List<Subunit> = listOf(
        Subunit(0, resources.getString(R.string.subunit_now_playing)),
        Subunit(1, resources.getString(R.string.subunit_upcoming)),
        Subunit(2, resources.getString(R.string.subunit_top_rated)),
        Subunit(3, resources.getString(R.string.subunit_popular)),
        Subunit(4, resources.getString(R.string.subunit_latest)),
    )
}
