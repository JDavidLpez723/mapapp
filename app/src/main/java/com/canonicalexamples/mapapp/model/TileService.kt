package com.canonicalexamples.mapapp.model

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * 20210218. Initial version created by jorge
 * for a Canonical Examples training.
 *
 * Copyright 2021 Jorge D. Ortiz Fuentes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
interface TileService {

    @Headers(
        "kvstoreio_api_key: 68f55af1fda77bd6e58369f343a2f2e894e3a892403ef824817f2d766c0e5bdc"
    )
    @GET("collections/mapappcollection/items/coords")
    fun getCoords(): Call<ResponseBody>

    @Headers(
            "kvstoreio_api_key: 68f55af1fda77bd6e58369f343a2f2e894e3a892403ef824817f2d766c0e5bdc",
            "Content-Type: text/plain"
    )
    @PUT("collections/mapappcollection/items/coords")
    fun setCoords(@Body data: String): Call<ResponseBody>

    @Headers(
            "Content-Type: application/json"
    )
    @POST("users")
    fun registerUser(@Body params: RequestBody): Call<ResponseBody>



}
