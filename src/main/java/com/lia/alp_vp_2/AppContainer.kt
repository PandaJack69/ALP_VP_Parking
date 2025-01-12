package com.lia.alp_vp_2

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.lia.alp_vp_2.repository.AuthenticationRepository
import com.lia.alp_vp_2.repository.NetworkAuthenticationRepository
import com.lia.alp_vp_2.repository.NetworkPenaltyRepository
import com.lia.alp_vp_2.repository.PenaltyRepository
import com.lia.alp_vp_2.repository.UserRepository
import com.lia.alp_vp_2.service.AuthenticationAPIService
import com.lia.alp_vp_2.service.PenaltyAPIService
import com.lia.alp_vp_2.service.UserAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val authenticationRepository: AuthenticationRepository
    val userRepository: UserRepository
    val penaltyRepository: PenaltyRepository
}

class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>,
    private val context: Context
) : AppContainer {
    private val baseUrl = "http://192.168.178.230:3000/"

    private val authenticationRetrofitService: AuthenticationAPIService by lazy {
        val retrofit = initRetrofit()
        retrofit.create(AuthenticationAPIService::class.java)
    }

    private val userRetrofitService: UserAPIService by lazy {
        val retrofit = initRetrofit()
        retrofit.create(UserAPIService::class.java)
    }

    private val penaltyRetrofitService: PenaltyAPIService by lazy {
        val retrofit = initRetrofit()
        retrofit.create(PenaltyAPIService::class.java)
    }

    override val authenticationRepository: AuthenticationRepository by lazy {
        NetworkAuthenticationRepository(authenticationRetrofitService, context)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userRetrofitService)
    }

    override val penaltyRepository: PenaltyRepository by lazy {
        NetworkPenaltyRepository(penaltyRetrofitService)
    }

    private fun initRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        return Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .baseUrl(baseUrl)
            .build()
    }
}
