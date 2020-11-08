package util

import org.cef.OS
import java.io.IOException
import java.lang.IllegalStateException


object Utils {

    fun openURL(url: String) {
        val rt = Runtime.getRuntime()
        try {
            if (OS.isWindows()) {
                rt.exec("rundll32 url.dll,FileProtocolHandler $url").waitFor()
            } else if (OS.isMacintosh()) {
                val cmd = arrayOf("open", url)
                rt.exec(cmd).waitFor()
            } else if (OS.isLinux()) {
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