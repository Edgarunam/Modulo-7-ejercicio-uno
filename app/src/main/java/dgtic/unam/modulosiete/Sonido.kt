package dgtic.unam.modulosiete
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dgtic.unam.modulosiete.databinding.ActivitySonidoBinding
import dgtic.unam.sonido.ModeloAudio
import dgtic.unam.video.RecipeAdapter2
import java.io.File
class Sonido : AppCompatActivity() {
    private lateinit var archivos: ArrayList<ModeloAudio>
    private lateinit var binding: ActivitySonidoBinding
    private lateinit var adap: RecipeAdapter2
    private var mediaPlayer: MediaPlayer? = null
    private var indice: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySonidoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        val stadoSD: String = Environment.getExternalStorageState()
        if (stadoSD == Environment.MEDIA_MOUNTED) {
            println("sistema=: " +
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).path)
            println("sistema2: " + this.getExternalFilesDir(null))
            val ff =
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).path)
            archivos = ArrayList()
            ff.listFiles()!!.forEach {
                if (it.isFile) {
                    println(it)
                    archivos.add(ModeloAudio(it.name, R.drawable.musica_img, it.path))
                }
            }
            archivos.add(ModeloAudio("Magenta_Moon_Part_II.mp3", R.drawable.musica_img,
                "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-15.mp3"))
        }
        adap = RecipeAdapter2(this, archivos)
        binding.list.adapter = adap
        binding.play.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(archivos.get(indice).path)
                    prepare()
                    start()
                }
            }else{
                mediaPlayer!!.start()
            }
        }
        binding.stop.setOnClickListener{
            if (mediaPlayer != null) {
                mediaPlayer!!.release()
                mediaPlayer=null
            }
        }
        binding.pause.setOnClickListener{
            if(mediaPlayer!=null){
                mediaPlayer!!.pause()
            }
        }
        binding.path.setOnClickListener{
            Toast.makeText(this, archivos.get(indice).path, Toast.LENGTH_SHORT).show()
        }
        binding.list.setOnItemClickListener { parent, view, position, id ->
            val data: ModeloAudio = archivos.get(position)
            indice=position
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(data.path)
                    prepare()
                    start()
                }
            } else {
                mediaPlayer!!.release()
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(data.path)
                    prepare()
                    start()
                }
            }
            Toast.makeText(this, data.namefile, Toast.LENGTH_SHORT).show()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}