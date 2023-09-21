package com.marmatsan.core_data.preferences

import androidx.datastore.core.Serializer
import com.marmatsan.core_domain.preferences.PreferencesData
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object PreferencesDataSerializer : Serializer<PreferencesData> {

    override val defaultValue: PreferencesData
        get() = PreferencesData()

    @Suppress("BlockingMethodInNonBlockingContext") // Actually, it is in a non-blocking context (suspend fun)
    override suspend fun writeTo(t: PreferencesData, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = PreferencesData.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

    override suspend fun readFrom(input: InputStream): PreferencesData {
        return try {
            Json.decodeFromString(
                deserializer = PreferencesData.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }
}