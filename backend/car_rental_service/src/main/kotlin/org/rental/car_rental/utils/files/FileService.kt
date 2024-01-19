package org.rental.car_rental.utils.files

interface FileService {
    fun uploadFile(baseDir: String, fileName: String, file: ByteArray, mimeType: String)

    fun deleteFile(baseDir: String, fileName: String)

    fun generateObjectId(file: ByteArray): String

    fun extractMimeType(mimeType: String): String
}

