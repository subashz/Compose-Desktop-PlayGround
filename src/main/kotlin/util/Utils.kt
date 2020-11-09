package util

import java.io.IOException
import java.lang.IllegalStateException


object Utils {

    fun openURL(url: String) {
        val rt = Runtime.getRuntime()
        try {
            val os = System.getProperty("os.name").toLowerCase()

            if (os.contains("win")) {
                rt.exec("rundll32 url.dll,FileProtocolHandler $url").waitFor()
            } else if (os.contains("mac")) {
                val cmd = arrayOf("open", url)
                rt.exec(cmd).waitFor()
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                val cmd = arrayOf("xdg-open", url)
                rt.exec(cmd).waitFor()
            } else {
                try {
                    throw IllegalStateException()
                } catch (e1: IllegalStateException) {
                    e1.printStackTrace()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

}