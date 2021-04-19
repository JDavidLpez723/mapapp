package com.canonicalexamples.tearank.app

import android.app.Application
import com.canonicalexamples.tearank.model.Node
import com.canonicalexamples.tearank.model.MapDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * 20210211. Initial version created by jorge
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
class MapApp: Application() {
    val database by lazy { MapDatabase.getInstance(this) }
//    val webservice by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://jsonplaceholder.typicode.com/")
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build().create(TodoService::class.java)
//    }
    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            database.clearAllTables()
            database.nodeDao.apply {
                this.create(node = Node(id=1, x=40.33224, y=-3.76809, tag="Sabatini Legnaes"))
                this.create(node = Node(id=2, x=38.34607, y=-0.49059, tag="LUCEROS ALICANTE"))
//                this.create(node = Node(id=3, x=1.0, y=90.0, tag="Parke2"))
//                this.create(node = Node(id=4, x=800.0, y=-1200.0, tag="Parke3"))
            }
        }
    }
}
