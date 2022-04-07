package br.edu.infnet.caderno_anotacoes.ui.add

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.infnet.caderno_anotacoes.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AdicionarAnotacaoFragment : Fragment(), LocationListener {

    private lateinit var viewModel: AdicionarAnotacaoViewModel
    private lateinit var lblTitulo: EditText
    private lateinit var lblCorpo: EditText
    private lateinit var btnAdicionar: Button
    private lateinit var userId: String
    private var user: FirebaseUser? = null
    var latitude: String = ""
    var longitude: String = ""
    val FINE_REQUEST = 900

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_adicionar_anotacao, container, false)
        setupUser()
        setupWidgets(view)
        viewModel = ViewModelProvider(this).get(AdicionarAnotacaoViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAdicionar.setOnClickListener {
            if(!haCampoVazio()) {
                getLocationByGps()
                val corpo = "latitude: ${latitude}, longitude: ${longitude}\n${lblCorpo.text}"
                viewModel.adicionar(userId, lblTitulo.text.toString(), corpo)
                findNavController().popBackStack()
            }
        }
    }

    private fun setupWidgets(view: View) {
        lblTitulo = view.findViewById(R.id.fragment_adicionar_anotacao_txt_titulo)
        lblCorpo = view.findViewById(R.id.fragment_adicionar_anotacao_txt_corpo)
        btnAdicionar = view.findViewById(R.id.fragment_adicionar_anotacao_btn_adicionar)
    }

    private fun haCampoVazio():Boolean {
        if(lblTitulo.text.isNullOrBlank()) return true
        if(lblCorpo.text.isNullOrBlank()) return true
        return false
    }

    private fun setupUser() {
        user = Firebase.auth.currentUser
        userId = user?.uid.toString()
    }

    private fun getLocationByGps() {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isProviderEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(isProviderEnabled) {
            if(requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    2000L,
                    0f,
                    this
                )
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                latitude = location?.latitude.toString()
                longitude = location?.longitude.toString()

            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), FINE_REQUEST)
            }
        }
    }

    override fun onLocationChanged(p0: Location) {
        TODO("Not yet implemented")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == FINE_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocationByGps()
        }
    }
//teste
}
