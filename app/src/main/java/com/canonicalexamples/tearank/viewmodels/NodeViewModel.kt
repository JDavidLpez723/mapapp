package com.canonicalexamples.tearank.viewmodels

import androidx.lifecycle.*
import com.canonicalexamples.tearank.BuildConfig
import com.canonicalexamples.tearank.model.MapDatabase
import com.canonicalexamples.tearank.model.Node
import com.canonicalexamples.tearank.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.asinh
import kotlin.math.floor
import kotlin.math.tan

class NodeViewModel (private val database: MapDatabase, private val itemSelected: Int): ViewModel() {

    private val _go_to_main_fragment: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val go_to_main_fragment: LiveData<Event<Boolean>> = _go_to_main_fragment

    private var _node = MutableLiveData<Node> (Node())
    var node : LiveData<Node> = _node

    private var _zoom = MutableLiveData<Int>(-1)
    var zoom : LiveData<Int> = _zoom

    //Get last node
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val n = database.nodeDao.getLastNode()
            n?.let {
                _node.postValue(n!!)
//                println("node:" + n.tag + " " + n.id)
            }
        }

    }

    fun buttonBackClicked(){
        _go_to_main_fragment.value = Event(true)
    }

    fun prueba() = "Ahaha"

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
        return "https://api.maptiler.com/maps/streets/256/$z/${p.first}/${p.second}.png?key=" + BuildConfig.API_KEY
    }

    fun getXYTile(lat : Double, lon: Double, zoom : Int) : Pair<Int, Int> {
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
//        println("https://a.tile.openstreetmap.org/"+zoom+"/"+xtile+"/"+ytile+".png")
        return Pair(xtile, ytile)
    }

}

class NodeViewModelFactory(private val database: MapDatabase, private val itemSelected: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NodeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NodeViewModel(database, itemSelected) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}