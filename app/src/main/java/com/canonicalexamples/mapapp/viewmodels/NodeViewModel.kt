package com.canonicalexamples.mapapp.viewmodels

import android.graphics.Bitmap
import android.telecom.Call
import androidx.lifecycle.*
import com.canonicalexamples.mapapp.model.MapDatabase
import com.canonicalexamples.mapapp.model.Node
import com.canonicalexamples.mapapp.model.TileService
import com.canonicalexamples.mapapp.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.await
import kotlin.math.PI
import kotlin.math.asinh
import kotlin.math.floor
import kotlin.math.tan

class NodeViewModel(private val database: MapDatabase, private val webservice: TileService, private val nodeId: Int): ViewModel() {

    //Variable for going back to list of nodes (NodesListFragment)
    private val _goBack: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val goBack: LiveData<Event<Boolean>> = _goBack

    private var _node = MutableLiveData<Node> (Node())
    var node : LiveData<Node> = _node

    private var _body = MutableLiveData<ResponseBody>()
    var body: LiveData<ResponseBody> = _body

    private var _zoom = MutableLiveData<Int>(-1)
    var zoom : LiveData<Int> = _zoom


    //private var _tile = MutableLiveData<Bitmap> (Bitmap.createBitmap())

    init {
        println("[VIEW MODEL]: item= $nodeId")
        viewModelScope.launch(Dispatchers.IO) {
            val n = database.nodeDao.get(nodeId) ?: Node(tag="fallo")
            _node.postValue(n)
        }

    }

    fun setZoom(zoom: Int){
        _zoom.postValue(zoom)
    }
    fun addZoom() { setZoom( (_zoom?.value?:9) +1)}
    fun subsZoom() { setZoom( (_zoom?.value?:11) - 1) }

    fun getTileUri(): String {
        val x = _node.value?.x ?: 0.0
        val y = _node.value?.y ?: 0.0
        val z = zoom.value ?: 0
        val p = getXYTile(x, y, z)

        //BASIC
        //return "https://api.maptiler.com/maps/basic/256/$z/${p.first}/${p.second}.png?key=w3yoRskFIgZceY3WMSjy"
        //Streeets
        return "https://api.maptiler.com/maps/streets/256/$z/${p.first}/${p.second}.png?key=w3yoRskFIgZceY3WMSjy"
    }


    private fun getXYTile(lat : Double, lon: Double, zoom : Int) : Pair<Int, Int> {
        val latRad = Math.toRadians(lat)
        var xtile = floor( (lon + 180) / 360 * (1 shl zoom) ).toInt()
        var ytile = floor( (1.0 - asinh(tan(latRad)) / PI) / 2 * (1 shl zoom) ).toInt()

        if (xtile < 0) {
            xtile = 0
        }
        if (xtile >= (1 shl zoom)) {
            xtile= (1 shl zoom) - 1
        }
        if (ytile < 0) {
            ytile = 0
        }
        if (ytile >= (1 shl zoom)) {
            ytile = (1 shl zoom) - 1
        }
        return Pair(xtile, ytile)
    }

    fun getCoords(){
        viewModelScope.launch(Dispatchers.IO) {
            val r = webservice.getCoords().await()
            println("MEssage:" + r.string())
            //print(r.execute().isSuccessful)
        }
    }

    fun setCoords(data: String){
        viewModelScope.launch(Dispatchers.IO) {
            val r = webservice.setCoords(data).await()
            println("MEssage SetCoords:" + r.string())
            //print(r.execute().isSuccessful)
        }
    }
}

class NodeViewModelFactory(private val database: MapDatabase, private val webservice: TileService, private val nodeId: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NodeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NodeViewModel(database, webservice, nodeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}