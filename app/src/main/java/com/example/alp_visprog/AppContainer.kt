package com.example.alp_visprog

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.alp_visprog.repository.AuthenticationRepository
import com.example.alp_visprog.repository.NetworkAuthenticationRepository
import com.example.alp_visprog.repository.NetworkPenaltyRepository
import com.example.alp_visprog.repository.NetworkProfileRepository
import com.example.alp_visprog.repository.NetworkReportRepository
import com.example.alp_visprog.repository.NetworkReservationRepository
//import com.example.alp_visprog.repository.NetworkTodoRepository
import com.example.alp_visprog.repository.NetworkUserRepository
import com.example.alp_visprog.repository.PenaltyRepository
import com.example.alp_visprog.repository.ProfileRepository
import com.example.alp_visprog.repository.ReportRepository
import com.example.alp_visprog.repository.ReservationRepository
//import com.example.alp_visprog.repository.TodoRepository
import com.example.alp_visprog.repository.UserRepository
import com.example.alp_visprog.service.AuthenticationAPIService
import com.example.alp_visprog.service.PenaltyAPIService
import com.example.alp_visprog.service.ProfileAPIService
import com.example.alp_visprog.service.ReportAPIService
import com.example.alp_visprog.service.ReservationAPIService
//import com.example.alp_visprog.service.TodoAPIService
import com.example.alp_visprog.service.UserAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// A container is an object that contains the dependencies that the app requires.
// These dependencies are used across the whole application, so they need to be in a common place that all activities can use.
// You can create a subclass of the Application class and store a reference to the container.
interface AppContainer {
    val authenticationRepository: AuthenticationRepository
    val userRepository: UserRepository
    val penaltyRepository: PenaltyRepository
    val profileRepository: ProfileRepository
    val reportRepository: ReportRepository
    val reservationRepository: ReservationRepository
}

class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>
): AppContainer {
    // change it to your own local ip please
    private val baseUrl = "http://192.168.178.230:3000/"
//    private val baseUrl = "http://localhost:3000/"
//    private val baseUrl = "http://127.0.0.1:3000/"
//    private val baseUrl = "http://10.0.2.2:3000/"

    // RETROFIT SERVICE
    // delay object creation until needed using lazy
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

    private val profileRetrofitService: ProfileAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(ProfileAPIService::class.java)
    }

    private val reportRetrofitService: ReportAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(ReportAPIService::class.java)
    }

    private val reservationRetrofitService: ReservationAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(ReservationAPIService::class.java)
    }

    // REPOSITORY INIT
    // Passing in the required objects is called dependency injection (DI). It is also known as inversion of control.
    override val authenticationRepository: AuthenticationRepository by lazy {
        NetworkAuthenticationRepository(authenticationRetrofitService)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(userDataStore, userRetrofitService)
    }

    override val penaltyRepository: PenaltyRepository by lazy {
        NetworkPenaltyRepository(penaltyRetrofitService)
    }

    override val profileRepository: ProfileRepository by lazy {
        NetworkProfileRepository(profileRetrofitService)
    }

    override val reportRepository: ReportRepository by lazy {
        NetworkReportRepository(reportRetrofitService)
    }

    override val reservationRepository: ReservationRepository by lazy {
        NetworkReservationRepository(reservationRetrofitService)
    }

    private fun initRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        return Retrofit
            .Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(client.build())
            .baseUrl(baseUrl)
            .build()
    }
}