package com.payup.data.repository

import com.payup.model.Contact
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Lazy
import dagger.Provides
import io.reactivex.Single
import okio.Okio
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ContactRepositoryImpl @Inject constructor(
        @Named("contactJsonFile") private val contactJsonFile: Provider<Lazy<InputStream>>,
        private val moshi: Moshi
) : ContactRepository {

    override fun getContacts(): Single<List<Contact>> {
        return Single.create {
            val buffer = Okio.buffer(Okio.source(contactJsonFile.get().get()))
            val json = buffer.readUtf8()
            buffer.close()

            val type = Types.newParameterizedType(List::class.java, Contact::class.java)
            it.onSuccess(moshi.adapter<List<Contact>>(type).fromJson(json))
        }
    }
}
