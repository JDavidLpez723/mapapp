package com.canonicalexamples.mapapp.model

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

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
    @GET("7/62/42.png")
    fun getExample(): Call<ResponseBody>

    @GET("{zoom}/{x}/{y}.png")
    fun getTile(@Path(value = "zoom") zoom: Int,
                @Path(value = "x") x: Int,
                @Path(value = "y") y: Int): Call<ResponseBody>
}
