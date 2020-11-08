package imageloader

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * A utility that downloads a file from a URL.
 * @author www.codejava.net
 */
class HttpDownloadUtility(private val cacheDir: String) {
    private val BUFFER_SIZE = 4096

    /**
     * Downloads a file from a URL
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @throws IOException
     */
    fun downloadFile(fileURL: String, log: Boolean = false): String {
        val url = URL(fileURL)
        val httpConn = url.openConnection() as HttpURLConnection
        val responseCode = httpConn.responseCode

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            var fileName = ""
            val disposition = httpConn.getHeaderField("Content-Disposition")
            val contentType = httpConn.contentType
            val contentLength = httpConn.contentLength
            if (disposition != null) {
                // extracts file name from header field
                val index = disposition.indexOf("filename=")
                if (index > 0) {
                    fileName = disposition.substring(
                        index + 10,
                        disposition.length - 1
                    )
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(
                    fileURL.lastIndexOf("/") + 1,
                    fileURL.length
                )
            }

            if (log) {
                println("Content-Type = $contentType")
                println("Content-Disposition = $disposition")
                println("Content-Length = $contentLength")
                println("fileName = $fileName")
            }

            // opens input stream from the HTTP connection
            val inputStream = httpConn.inputStream
            val saveFilePath = cacheDir + File.separator + fileName

            // opens an output stream to save into file
            val outputStream = FileOutputStream(saveFilePath)
            var bytesRead = -1
            val buffer = ByteArray(BUFFER_SIZE)
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.close()
            inputStream.close()
//            println("File downloaded")
            httpConn.disconnect()
            return fileName
        } else {
            if (log)
                println("No file to download. Server replied HTTP code: $responseCode")

            httpConn.disconnect()
            return ""
        }
    }

    fun getImageAsset(url: String) {


    }
}