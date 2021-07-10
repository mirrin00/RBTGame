package summer.practice.infty

import javafx.scene.image.Image
import summer.practice.infty.rbt.RedBlackTree
import java.lang.IllegalArgumentException

object ResourceLoader {
    private val map = RedBlackTree<String, Image>()

    init{
        loadImage("/cave.jpg", "Underground")
        loadImage("/cave2.jpg", "Underground2")
        loadImage("/forest.jpg", "Usual")
        loadImage("/lava.jpg", "Hellish")
        loadImage("/sand.jpg", "Sandy")
        loadImage("/sea.jpg", "Marine")
        loadImage("/sea2.jpg", "Marine2")
        loadImage("/sky.jpg", "Heavenly")
        loadImage("/snow.jpg", "Frosty")
    }

    private fun loadImage(filename: String, key: String){
        try {
            map.insert(key, Image(filename))
        }catch(e: IllegalArgumentException){
            println("Error: ResourceLoader: Can't find file $filename")
        }catch(e: Exception){
            throw RuntimeException("ResourceLoader: Error while loading file $filename")
        }
    }

    fun getImage(key: String): Image? = map.find(key)
}