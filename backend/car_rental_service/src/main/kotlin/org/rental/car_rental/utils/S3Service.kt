package org.rental.car_rental.utils

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.util.UUID


interface S3Service {
    fun uploadToS3(bucketName: String, key: String, file: ByteArray)

    fun generateObjectId(file: ByteArray): String
}

@Service
@Primary
class S3ServiceImpl : S3Service {
    private val s3 = S3Client.builder()
        .region(Region.EU_CENTRAL_1)
        .credentialsProvider(DefaultCredentialsProvider.create())
        .build()

    override fun uploadToS3(bucketName: String, key: String, file: ByteArray) {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType("image/jpeg")
            .contentDisposition("inline")
            .build()

        s3.putObject(
            putObjectRequest,
            RequestBody.fromByteBuffer(
                ByteBuffer.wrap(file)
            )
        )
    }

    override fun generateObjectId(file: ByteArray): String {
        val uuid = UUID.randomUUID().toString()
        val timeStamp = System.currentTimeMillis()

        val digest = MessageDigest.getInstance("SHA-256")
        val contentHash = digest.digest(file).joinToString("") { "%02x".format(it) }

        return "${uuid}_${timeStamp}_${contentHash}"
    }
}