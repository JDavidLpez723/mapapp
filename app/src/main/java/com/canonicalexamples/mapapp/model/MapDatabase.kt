package com.canonicalexamples.mapapp.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.canonicalexamples.mapapp.app.MapApp

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
@Database(entities = [Node::class], version = 1, exportSchema = false)
abstract class MapDatabase: RoomDatabase() {
    abstract val nodeDao: NodeDao

    companion object {
        @Volatile
        private var INSTANCE: MapDatabase? = null
        fun getInstance(context: MapApp): MapDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MapDatabase::class.java,
                    "map_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
