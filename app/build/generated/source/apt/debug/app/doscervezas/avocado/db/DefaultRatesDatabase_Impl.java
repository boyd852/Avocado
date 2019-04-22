package app.doscervezas.avocado.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import app.doscervezas.avocado.db.dao.DefaultRatesDao;
import app.doscervezas.avocado.db.dao.DefaultRatesDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class DefaultRatesDatabase_Impl extends DefaultRatesDatabase {
  private volatile DefaultRatesDao _defaultRatesDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `DefaultRatesData` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `currency` TEXT, `rate` REAL NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"02deab8aed5dfd845cba8aa6a18f3a24\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `DefaultRatesData`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsDefaultRatesData = new HashMap<String, TableInfo.Column>(3);
        _columnsDefaultRatesData.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsDefaultRatesData.put("currency", new TableInfo.Column("currency", "TEXT", false, 0));
        _columnsDefaultRatesData.put("rate", new TableInfo.Column("rate", "REAL", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDefaultRatesData = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDefaultRatesData = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDefaultRatesData = new TableInfo("DefaultRatesData", _columnsDefaultRatesData, _foreignKeysDefaultRatesData, _indicesDefaultRatesData);
        final TableInfo _existingDefaultRatesData = TableInfo.read(_db, "DefaultRatesData");
        if (! _infoDefaultRatesData.equals(_existingDefaultRatesData)) {
          throw new IllegalStateException("Migration didn't properly handle DefaultRatesData(app.doscervezas.avocado.db.entity.DefaultRatesData).\n"
                  + " Expected:\n" + _infoDefaultRatesData + "\n"
                  + " Found:\n" + _existingDefaultRatesData);
        }
      }
    }, "02deab8aed5dfd845cba8aa6a18f3a24", "957ba321521ea90d0bd7eb9c6ccaa609");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "DefaultRatesData");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `DefaultRatesData`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public DefaultRatesDao defaultRatesDao() {
    if (_defaultRatesDao != null) {
      return _defaultRatesDao;
    } else {
      synchronized(this) {
        if(_defaultRatesDao == null) {
          _defaultRatesDao = new DefaultRatesDao_Impl(this);
        }
        return _defaultRatesDao;
      }
    }
  }
}
