package app.epf.ratp_eb_pf.ui.detailStation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import app.epf.ratp_eb_pf.R
import app.epf.ratp_eb_pf.daoSch
import app.epf.ratp_eb_pf.data.SchedulesDao
import app.epf.ratp_eb_pf.model.Schedules
import app.epf.ratp_eb_pf.model.Stations
import app.epf.ratp_eb_pf.retrofit
import app.epf.ratp_eb_pf.service.SchedulesService
import kotlinx.coroutines.runBlocking

class HoraireAFragment : Fragment(), StationDetailsActivity.RefreshPage {

    private var scheduleDao: SchedulesDao? = null
    private var horaires: MutableList<Schedules>? = null
    private lateinit var horairesRecyclerView: RecyclerView
    private var stationFromParent: Stations? = null

    private val type = "metros"
    private val way = "A"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_horaire_station, container, false)
        stationFromParent = arguments?.getSerializable("station") as Stations

        scheduleDao = daoSch(requireContext())

        horairesRecyclerView = view.findViewById(R.id.horaires_recyclerview)
        val itemsSwipeToRefresh = view.findViewById<SwipeRefreshLayout>(R.id.itemsswipetorefresh)
        horairesRecyclerView.layoutManager = LinearLayoutManager(activity)

        synchroStationData()

        itemsSwipeToRefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        )
        itemsSwipeToRefresh.setColorSchemeColors(Color.WHITE)

        // Actualise les 2 fragments avec le swipeToRefresh
        itemsSwipeToRefresh.setOnRefreshListener {
            val viewpager = activity?.findViewById<ViewPager>(R.id.fragment_pager_horaires)
            synchroStationData()
            val horaireBFragment =
                viewpager?.adapter?.instantiateItem(viewpager, 1) as HoraireBFragment
            horaireBFragment.refreshPage()
            itemsSwipeToRefresh.isRefreshing = false
        }

        return view
    }

    private fun synchroStationData() {
        val serviceSchedules = retrofit().create(SchedulesService::class.java)
        horaires?.clear()
        runBlocking {
            scheduleDao?.deleteSchedules()
            val result = serviceSchedules.getScheduleService(
                type,
                stationFromParent!!.line,
                stationFromParent!!.slug,
                way
            )
            var id = 1
            result.result.schedules.map {

                val schedules = Schedules(id, it.message, it.destination)
                scheduleDao?.addSchedules(schedules)
                id += 1
            }
            horaires = scheduleDao?.getSchedules()
            horairesRecyclerView.adapter = HorairesAdapter(horaires ?: mutableListOf())
        }
    }

    override fun refreshPage() {
        view?.let { synchroStationData() }
    }
}