package de.fuchsch.email.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.fuchsch.email.database.dao.AccountDao
import de.fuchsch.email.database.dao.FolderDao
import de.fuchsch.email.database.entity.Account
import de.fuchsch.email.database.entity.FolderEntity
import de.fuchsch.email.database.entity.IMAPProtocolConverter

@Database(entities = [Account::class, FolderEntity::class], version = 2)
@TypeConverters(IMAPProtocolConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun accountDao(): AccountDao

    abstract fun folderDao(): FolderDao

}