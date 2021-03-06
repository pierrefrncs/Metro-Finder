package app.epf.ratp_eb_pf.ui.listeLines.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.epf.ratp_eb_pf.R
import app.epf.ratp_eb_pf.model.Line
import app.epf.ratp_eb_pf.model.Traffic
import kotlinx.android.synthetic.main.fragment_traffic_details.view.*

// Sous-fragment (de DetailsLineActivity) qui contient l'état du traffic d'une ligne

class TrafficDetailsFragment : Fragment() {

    private var trafficFromParent: Traffic? = null
    private var lineFromParent: Line? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_traffic_details, container, false)

        // Recupère les infos de la ligne du fragment/activity parent
        lineFromParent = arguments?.getSerializable("line") as Line
        trafficFromParent = arguments?.getSerializable("traffic") as Traffic

        view.etatTraffic.text = trafficFromParent?.title
        view.messageTraffic.text = trafficFromParent?.message

        return view
    }
}