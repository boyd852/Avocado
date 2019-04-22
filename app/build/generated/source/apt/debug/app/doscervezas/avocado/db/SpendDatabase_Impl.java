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
import app.doscervezas.avocado.db.dao.BudgetDao;
import app.doscervezas.avocado.db.dao.BudgetDao_Impl;
import app.doscervezas.avocado.db.dao.SpendDao;
import app.doscervezas.avocado.db.dao.SpendDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class SpendDatabase_Impl extends SpendDatabase {
  private volatile SpendDao _spendDao;

  private volatile BudgetDao _budgetDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(12) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `SpendEntry` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `budgetId` INTEGER NOT NULL, `date` INTEGER, `cost` REAL NOT NULL, `description` TEXT, `currency` TEXT, `homeSpend` REAL NOT NULL, `rate` REAL NOT NULL, `homeRate` REAL NOT NULL, `isSynced` INTEGER NOT NULL, `category` TEXT, `spendEntryGroupId` INTEGER NOT NULL, `datesAreSplit` INTEGER NOT NULL, `startDate` INTEGER, `endDate` INTEGER, `totalSpend` REAL NOT NULL, FOREIGN KEY(`budgetId`) REFERENCES `Budget`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
        _db.execSQL("CREATE  INDEX `index_SpendEntry_budgetId` ON `SpendEntry` (`budgetId`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Budget` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `budgetName` TEXT, `budgetCurrency` TEXT, `dailyBudget` REAL, `budgetDate` INTEGER)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"615a116d9264bcea0f665a42dca8c6bb\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `SpendEntry`");
        _db.execSQL("DROP TABLE IF EXISTS `Budget`");
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
        _db.execSQL("PRAGMA foreign_keys = ON");
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
        final HashMap<String, TableInfo.Column> _columnsSpendEntry = new HashMap<String, TableInfo.Column>(16);
        _columnsSpendEntry.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsSpendEntry.put("budgetId", new TableInfo.Column("budgetId", "INTEGER", true, 0));
        _columnsSpendEntry.put("date", new TableInfo.Column("date", "INTEGER", false, 0));
        _columnsSpendEntry.put("cost", new TableInfo.Column("cost", "REAL", true, 0));
        _columnsSpendEntry.put("description", new TableInfo.Column("description", "TEXT", false, 0));
        _columnsSpendEntry.put("currency", new TableInfo.Column("currency", "TEXT", false, 0));
        _columnsSpendEntry.put("homeSpend", new TableInfo.Column("homeSpend", "REAL", true, 0));
        _columnsSpendEntry.put("rate", new TableInfo.Column("rate", "REAL", true, 0));
        _columnsSpendEntry.put("homeRate", new TableInfo.Column("homeRate", "REAL", true, 0));
        _columnsSpendEntry.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0));
        _columnsSpendEntry.put("category", new TableInfo.Column("category", "TEXT", false, 0));
        _columnsSpendEntry.put("spendEntryGroupId", new TableInfo.Column("spendEntryGroupId", "INTEGER", true, 0));
        _columnsSpendEntry.put("datesAreSplit", new TableInfo.Column("datesAreSplit", "INTEGER", true, 0));
        _columnsSpendEntry.put("startDate", new TableInfo.Column("startDate", "INTEGER", false, 0));
        _columnsSpendEntry.put("endDate", new TableInfo.Column("endDate", "INTEGER", false, 0));
        _columnsSpendEntry.put("totalSpend", new TableInfo.Column("totalSpend", "REAL", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSpendEntry = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysSpendEntry.add(new TableInfo.ForeignKey("Budget", "CASCADE", "CASCADE",Arrays.asList("budgetId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesSpendEntry = new HashSet<TableInfo.Index>(1);
        _indicesSpendEntry.add(new TableInfo.Index("index_SpendEntry_budgetId", false, Arrays.asList("budgetId")));
        final TableInfo _infoSpendEntry = new TableInfo("SpendEntry", _columnsSpendEntry, _foreignKeysSpendEntry, _indicesSpendEntry);
        final TableInfo _existingSpendEntry = TableInfo.read(_db, "SpendEntry");
        if (! _infoSpendEntry.equals(_existingSpendEntry)) {
          throw new IllegalStateException("Migration didn't properly handle SpendEntry(app.doscervezas.avocado.db.entity.SpendEntry).\n"
                  + " Expected:\n" + _infoSpendEntry + "\n"
                  + " Found:\n" + _existingSpendEntry);
        }
        final HashMap<String, TableInfo.Column> _columnsBudget = new HashMap<String, TableInfo.Column>(5);
        _columnsBudget.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsBudget.put("budgetName", new TableInfo.Column("budgetName", "TEXT", false, 0));
        _columnsBudget.put("budgetCurrency", new TableInfo.Column("budgetCurrency", "TEXT", false, 0));
        _columnsBudget.put("dailyBudget", new TableInfo.Column("dailyBudget", "REAL", false, 0));
        _columnsBudget.put("budgetDate", new TableInfo.Column("budgetDate", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBudget = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBudget = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBudget = new TableInfo("Budget", _columnsBudget, _foreignKeysBudget, _indicesBudget);
        final TableInfo _existingBudget = TableInfo.read(_db, "Budget");
        if (! _infoBudget.equals(_existingBudget)) {
          throw new IllegalStateException("Migration didn't properly handle Budget(app.doscervezas.avocado.db.entity.Budget).\n"
                  + " Expected:\n" + _infoBudget + "\n"
                  + " Found:\n" + _existingBudget);
        }
      }
    }, "615a116d9264bcea0f665a42dca8c6bb", "1af4083c21ab5ecc6e7178d2a47eabae");
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
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "SpendEntry","Budget");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `SpendEntry`");
      _db.execSQL("DELETE FROM `Budget`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public SpendDao spendDao() {
    if (_spendDao != null) {
      return _spendDao;
    } else {
      synchronized(this) {
        if(_spendDao == null) {
          _spendDao = new SpendDao_Impl(this);
        }
        return _spendDao;
      }
    }
  }

  @Override
  public BudgetDao budgetDao() {
    if (_budgetDao != null) {
      return _budgetDao;
    } else {
      synchronized(this) {
        if(_budgetDao == null) {
          _budgetDao = new BudgetDao_Impl(this);
        }
        return _budgetDao;
      }
    }
  }
}
