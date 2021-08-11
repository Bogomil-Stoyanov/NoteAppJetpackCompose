package eu.bbsapps.notecompose.data.remote

import eu.bbsapps.notecompose.data.local.entities.Note
import eu.bbsapps.notecompose.data.remote.requests.AccountRequest
import eu.bbsapps.notecompose.data.remote.requests.AddOwnerRequest
import eu.bbsapps.notecompose.data.remote.requests.DeleteNoteRequest
import eu.bbsapps.notecompose.data.remote.responces.SimpleResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NoteApi {

    @POST("/register")
    suspend fun register(@Body registerRequest: AccountRequest): Response<SimpleResponse>

    @POST("/login")
    suspend fun login(@Body loginRequest: AccountRequest): Response<SimpleResponse>

    @POST("/addNote")
    suspend fun addNote(@Body note: Note):Response<ResponseBody>

    @POST("/deleteNote")
    suspend fun deleteNote(@Body deleteNoteRequest: DeleteNoteRequest):Response<ResponseBody>

    @POST("/addOwnerToNote")
    suspend fun addOwnerToNote(@Body addOwnerRequest: AddOwnerRequest):Response<SimpleResponse>

    @GET("/getNotes")
    suspend fun getNotes():Response<List<Note>>
}