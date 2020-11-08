package imageloader

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*


/**
 * Simple cache management.
 */

object ImageLoader {

    private val CACHE_DIR = ".cache"
    private val CACHE_INFO = ".cache" + File.separator + ".info"

    // url: localFileName
    private val cachedImage = arrayListOf<Pair<String, String>>()

    private val httpDownloadUtility = HttpDownloadUtility(CACHE_DIR)

    init {
        loadCacheData()
    }


    private fun loadCacheData() {

        val file = File(CACHE_INFO)
        val cachedDir = File(CACHE_DIR)

        if (!cachedDir.exists()) {
            cachedDir.mkdir()
        }

        if (!file.exists())
            file.createNewFile()

        val sc = Scanner(file)

        while (sc.hasNextLine()) {
            val line = sc.nextLine()
            val splatted = line.split(" ")
            cachedImage.add(Pair(splatted[0], splatted[1]))
        }

        println("Cached image: ${cachedImage}")

    }

    suspend fun getImage(url: String): String {

        cachedImage.forEach {
            if (it.first == url) {
                return CACHE_DIR + File.separator + it.second
            }
        }

        val fileName = httpDownloadUtility.downloadFile(url)
        saveToCacheInfo(url, fileName)
        return CACHE_DIR + File.separator + fileName
    }

    private fun saveToCacheInfo(url: String, localName: String) {
        val lock: Any? = null

        cachedImage.add(Pair(url, localName))

        try {
            Files.write(Paths.get(CACHE_INFO), Arrays.asList("$url $localName"), StandardOpenOption.APPEND)
        } catch (e: IOException) {
            //exception handling left as an exercise for the reader
        }

    }

    suspend fun getCachedAbsolute(url: String): String {
        val file = File(getImage(url))
        return file.absolutePath
    }


}