package app.epf.ratp_eb_pf.ui.favoris

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.viewpager.widget.ViewPager
import app.epf.ratp_eb_pf.R
import app.epf.ratp_eb_pf.data.AppDatabase
import app.epf.ratp_eb_pf.data.LineDao
import app.epf.ratp_eb_pf.model.Line
import app.epf.ratp_eb_pf.ui.listeLines.LinesAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_favoris.view.*
import kotlinx.android.synthetic.main.fragment_favoris_lines.view.*
import kotlinx.coroutines.runBlocking

class FavorisLinesFragment : Fragment() {

    private var lineDaoSaved: LineDao? = null
    private lateinit var linesRecyclerView: RecyclerView
    private var lines: MutableList<Line>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favoris_lines, container, false)

        linesRecyclerView = view.findViewById(R.id.savedLines_recyclerview)
        linesRecyclerView.layoutManager = LinearLayoutManager(activity)

        view.noLinesImage.setOnClickListener {
            val navController = activity?.findNavController(R.id.nav_host_fragment)
            navController?.navigate(R.id.navigation_list_lignes)
        }

        val database =
            Room.databaseBuilder(requireContext(), AppDatabase::class.java, "savedDatabase")
                .build()

        lineDaoSaved = database.getLineDao()

        runBlocking {
            lines = lineDaoSaved?.getLines()
        }

        runBlocking {
            if (!lines.isNullOrEmpty()) {
                view.layoutNoSavedLine.visibility = View.GONE
            } else {
                view.layoutNoSavedLine.visibility = View.VISIBLE
            }
        }
        linesRecyclerView.adapter = LinesAdapter(lines ?: mutableListOf(), view)


        return view
    }

    override fun onResume() {
        super.onResume()

        linesRecyclerView.adapter = LinesAdapter(lines ?: mutableListOf(), requireView())
    }
}