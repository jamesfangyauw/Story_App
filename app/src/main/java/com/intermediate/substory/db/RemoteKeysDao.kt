package com.dicoding.picodiploma.loginwithanimation.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM remote_key_entity WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): RemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeyEntity: List<RemoteKeyEntity>)

    @Query("DELETE FROM remote_key_entity")
    suspend fun deleteRemoteKeys()
}