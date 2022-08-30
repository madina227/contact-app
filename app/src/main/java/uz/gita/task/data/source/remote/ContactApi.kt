package uz.gita.task.data.source.remote

import retrofit2.Call
import retrofit2.http.*
import uz.gita.task.data.models.ContactData
import uz.gita.task.data.models.ContactRequestData
import uz.gita.task.data.models.ResponseData

interface ContactApi {

    @GET("/contact")
    fun getAllContacts(): Call<ResponseData<List<ContactData>>>

    @DELETE("/contact/{id}")
    fun deleteContactById(@Path("id") contactId: Long): Call<ResponseData<ContactData>>

    @PUT("/contact")
    fun update(
        @Query("id") contactId: Long,//so'rov
        @Body data: ContactRequestData
    ): Call<ResponseData<ContactData>>

    @POST("/contact")
    fun insert(@Body data: ContactRequestData): Call<ResponseData<ContactData>>

}