package app.doscervezas.avocado.db.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import app.doscervezas.avocado.db.converter.DateConverter;
import app.doscervezas.avocado.db.entity.SpendEntry;
import java.lang.Boolean;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings("unchecked")
public final class SpendDao_Impl implements SpendDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfSpendEntry;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfSpendEntry;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfSpendEntry;

  private final SharedSQLiteStatement __preparedStmtOfUpdateRate;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSpendEntryGroup;

  public SpendDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSpendEntry = new EntityInsertionAdapter<SpendEntry>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `SpendEntry`(`id`,`budgetId`,`date`,`cost`,`description`,`currency`,`homeSpend`,`rate`,`homeRate`,`isSynced`,`category`,`spendEntryGroupId`,`datesAreSplit`,`startDate`,`endDate`,`totalSpend`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SpendEntry value) {
        stmt.bindLong(1, value.id);
        stmt.bindLong(2, value.getBudgetId());
        final Long _tmp;
        _tmp = DateConverter.toTimeStamp(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        stmt.bindDouble(4, value.getCost());
        if (value.getDescription() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescription());
        }
        if (value.getCurrency() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCurrency());
        }
        stmt.bindDouble(7, value.getHomeSpend());
        stmt.bindDouble(8, value.getRate());
        stmt.bindDouble(9, value.getHomeRate());
        final int _tmp_1;
        _tmp_1 = value.isSynced() ? 1 : 0;
        stmt.bindLong(10, _tmp_1);
        if (value.getCategory() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getCategory());
        }
        stmt.bindLong(12, value.getSpendEntryGroupId());
        final int _tmp_2;
        _tmp_2 = value.datesAreSplit() ? 1 : 0;
        stmt.bindLong(13, _tmp_2);
        final Long _tmp_3;
        _tmp_3 = DateConverter.toTimeStamp(value.getStartDate());
        if (_tmp_3 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindLong(14, _tmp_3);
        }
        final Long _tmp_4;
        _tmp_4 = DateConverter.toTimeStamp(value.getEndDate());
        if (_tmp_4 == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindLong(15, _tmp_4);
        }
        stmt.bindDouble(16, value.getTotalSpend());
      }
    };
    this.__deletionAdapterOfSpendEntry = new EntityDeletionOrUpdateAdapter<SpendEntry>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `SpendEntry` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SpendEntry value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfSpendEntry = new EntityDeletionOrUpdateAdapter<SpendEntry>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `SpendEntry` SET `id` = ?,`budgetId` = ?,`date` = ?,`cost` = ?,`description` = ?,`currency` = ?,`homeSpend` = ?,`rate` = ?,`homeRate` = ?,`isSynced` = ?,`category` = ?,`spendEntryGroupId` = ?,`datesAreSplit` = ?,`startDate` = ?,`endDate` = ?,`totalSpend` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, SpendEntry value) {
        stmt.bindLong(1, value.id);
        stmt.bindLong(2, value.getBudgetId());
        final Long _tmp;
        _tmp = DateConverter.toTimeStamp(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        stmt.bindDouble(4, value.getCost());
        if (value.getDescription() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDescription());
        }
        if (value.getCurrency() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCurrency());
        }
        stmt.bindDouble(7, value.getHomeSpend());
        stmt.bindDouble(8, value.getRate());
        stmt.bindDouble(9, value.getHomeRate());
        final int _tmp_1;
        _tmp_1 = value.isSynced() ? 1 : 0;
        stmt.bindLong(10, _tmp_1);
        if (value.getCategory() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getCategory());
        }
        stmt.bindLong(12, value.getSpendEntryGroupId());
        final int _tmp_2;
        _tmp_2 = value.datesAreSplit() ? 1 : 0;
        stmt.bindLong(13, _tmp_2);
        final Long _tmp_3;
        _tmp_3 = DateConverter.toTimeStamp(value.getStartDate());
        if (_tmp_3 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindLong(14, _tmp_3);
        }
        final Long _tmp_4;
        _tmp_4 = DateConverter.toTimeStamp(value.getEndDate());
        if (_tmp_4 == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindLong(15, _tmp_4);
        }
        stmt.bindDouble(16, value.getTotalSpend());
        stmt.bindLong(17, value.id);
      }
    };
    this.__preparedStmtOfUpdateRate = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE SpendEntry SET rate = ?, homeRate = ?, isSynced = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteSpendEntryGroup = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM SpendEntry WHERE spendEntryGroupId = ?";
        return _query;
      }
    };
  }

  @Override
  public long addSpendEntry(final SpendEntry spendEntry) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfSpendEntry.insertAndReturnId(spendEntry);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteEntry(final SpendEntry spendEntry) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfSpendEntry.handle(spendEntry);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateSpendEntry(final SpendEntry spendEntry) {
    __db.beginTransaction();
    try {
      __updateAdapterOfSpendEntry.handle(spendEntry);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateRate(final Double rate, final Double homeRate, final long id,
      final Boolean isSynced) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateRate.acquire();
    int _argIndex = 1;
    if (rate == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindDouble(_argIndex, rate);
    }
    _argIndex = 2;
    if (homeRate == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindDouble(_argIndex, homeRate);
    }
    _argIndex = 3;
    final Integer _tmp;
    _tmp = isSynced == null ? null : (isSynced ? 1 : 0);
    if (_tmp == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindLong(_argIndex, _tmp);
    }
    _argIndex = 4;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateRate.release(_stmt);
    }
  }

  @Override
  public void deleteSpendEntryGroup(final long spendEntryGroupId) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSpendEntryGroup.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, spendEntryGroupId);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteSpendEntryGroup.release(_stmt);
    }
  }

  @Override
  public List<SpendEntry> initSpendEntries(final long budgetId) {
    final String _sql = "SELECT * FROM SpendEntry WHERE budgetId= ? ORDER BY date";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, budgetId);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBudgetId = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetId");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfHomeSpend = CursorUtil.getColumnIndexOrThrow(_cursor, "homeSpend");
      final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
      final int _cursorIndexOfHomeRate = CursorUtil.getColumnIndexOrThrow(_cursor, "homeRate");
      final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfSpendEntryGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "spendEntryGroupId");
      final int _cursorIndexOfDatesAreSplit = CursorUtil.getColumnIndexOrThrow(_cursor, "datesAreSplit");
      final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
      final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
      final int _cursorIndexOfTotalSpend = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSpend");
      final List<SpendEntry> _result = new ArrayList<SpendEntry>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final SpendEntry _item;
        _item = new SpendEntry();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        final long _tmpBudgetId;
        _tmpBudgetId = _cursor.getLong(_cursorIndexOfBudgetId);
        _item.setBudgetId(_tmpBudgetId);
        final Date _tmpDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDate);
        }
        _tmpDate = DateConverter.toDate(_tmp);
        _item.setDate(_tmpDate);
        final double _tmpCost;
        _tmpCost = _cursor.getDouble(_cursorIndexOfCost);
        _item.setCost(_tmpCost);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        final String _tmpCurrency;
        _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
        _item.setCurrency(_tmpCurrency);
        final double _tmpHomeSpend;
        _tmpHomeSpend = _cursor.getDouble(_cursorIndexOfHomeSpend);
        _item.setHomeSpend(_tmpHomeSpend);
        final double _tmpRate;
        _tmpRate = _cursor.getDouble(_cursorIndexOfRate);
        _item.setRate(_tmpRate);
        final double _tmpHomeRate;
        _tmpHomeRate = _cursor.getDouble(_cursorIndexOfHomeRate);
        _item.setHomeRate(_tmpHomeRate);
        final boolean _tmpIsSynced;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
        _tmpIsSynced = _tmp_1 != 0;
        _item.setSynced(_tmpIsSynced);
        final String _tmpCategory;
        _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        _item.setCategory(_tmpCategory);
        final long _tmpSpendEntryGroupId;
        _tmpSpendEntryGroupId = _cursor.getLong(_cursorIndexOfSpendEntryGroupId);
        _item.setSpendEntryGroupId(_tmpSpendEntryGroupId);
        final boolean _tmpDatesAreSplit;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfDatesAreSplit);
        _tmpDatesAreSplit = _tmp_2 != 0;
        _item.setDatesAreSplit(_tmpDatesAreSplit);
        final Date _tmpStartDate;
        final Long _tmp_3;
        if (_cursor.isNull(_cursorIndexOfStartDate)) {
          _tmp_3 = null;
        } else {
          _tmp_3 = _cursor.getLong(_cursorIndexOfStartDate);
        }
        _tmpStartDate = DateConverter.toDate(_tmp_3);
        _item.setStartDate(_tmpStartDate);
        final Date _tmpEndDate;
        final Long _tmp_4;
        if (_cursor.isNull(_cursorIndexOfEndDate)) {
          _tmp_4 = null;
        } else {
          _tmp_4 = _cursor.getLong(_cursorIndexOfEndDate);
        }
        _tmpEndDate = DateConverter.toDate(_tmp_4);
        _item.setEndDate(_tmpEndDate);
        final double _tmpTotalSpend;
        _tmpTotalSpend = _cursor.getDouble(_cursorIndexOfTotalSpend);
        _item.setTotalSpend(_tmpTotalSpend);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Double> getTotalSpend(final long budgetId, final long date) {
    final String _sql = "SELECT SUM((homeRate/rate)*cost) FROM SpendEntry WHERE budgetId= ? AND date >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, budgetId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, date);
    return __db.getInvalidationTracker().createLiveData(new String[]{"SpendEntry"}, new Callable<Double>() {
      @Override
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final Double _result;
          if(_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Double> getTotalDaySpend(final Long date, final long budgetId) {
    final String _sql = "SELECT SUM((homeRate/rate)*cost) FROM SpendEntry WHERE date = ? AND budgetId= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, date);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, budgetId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"SpendEntry"}, new Callable<Double>() {
      @Override
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final Double _result;
          if(_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<SpendEntry>> getSpendEntriesForDate(final long date, final long budgetId) {
    final String _sql = "SELECT * FROM SpendEntry WHERE date = ? AND budgetId= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, date);
    _argIndex = 2;
    _statement.bindLong(_argIndex, budgetId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"SpendEntry"}, new Callable<List<SpendEntry>>() {
      @Override
      public List<SpendEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBudgetId = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfHomeSpend = CursorUtil.getColumnIndexOrThrow(_cursor, "homeSpend");
          final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
          final int _cursorIndexOfHomeRate = CursorUtil.getColumnIndexOrThrow(_cursor, "homeRate");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSpendEntryGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "spendEntryGroupId");
          final int _cursorIndexOfDatesAreSplit = CursorUtil.getColumnIndexOrThrow(_cursor, "datesAreSplit");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfTotalSpend = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSpend");
          final List<SpendEntry> _result = new ArrayList<SpendEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final SpendEntry _item;
            _item = new SpendEntry();
            _item.id = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBudgetId;
            _tmpBudgetId = _cursor.getLong(_cursorIndexOfBudgetId);
            _item.setBudgetId(_tmpBudgetId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp);
            _item.setDate(_tmpDate);
            final double _tmpCost;
            _tmpCost = _cursor.getDouble(_cursorIndexOfCost);
            _item.setCost(_tmpCost);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item.setDescription(_tmpDescription);
            final String _tmpCurrency;
            _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            _item.setCurrency(_tmpCurrency);
            final double _tmpHomeSpend;
            _tmpHomeSpend = _cursor.getDouble(_cursorIndexOfHomeSpend);
            _item.setHomeSpend(_tmpHomeSpend);
            final double _tmpRate;
            _tmpRate = _cursor.getDouble(_cursorIndexOfRate);
            _item.setRate(_tmpRate);
            final double _tmpHomeRate;
            _tmpHomeRate = _cursor.getDouble(_cursorIndexOfHomeRate);
            _item.setHomeRate(_tmpHomeRate);
            final boolean _tmpIsSynced;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_1 != 0;
            _item.setSynced(_tmpIsSynced);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            _item.setCategory(_tmpCategory);
            final long _tmpSpendEntryGroupId;
            _tmpSpendEntryGroupId = _cursor.getLong(_cursorIndexOfSpendEntryGroupId);
            _item.setSpendEntryGroupId(_tmpSpendEntryGroupId);
            final boolean _tmpDatesAreSplit;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfDatesAreSplit);
            _tmpDatesAreSplit = _tmp_2 != 0;
            _item.setDatesAreSplit(_tmpDatesAreSplit);
            final Date _tmpStartDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfStartDate);
            }
            _tmpStartDate = DateConverter.toDate(_tmp_3);
            _item.setStartDate(_tmpStartDate);
            final Date _tmpEndDate;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfEndDate);
            }
            _tmpEndDate = DateConverter.toDate(_tmp_4);
            _item.setEndDate(_tmpEndDate);
            final double _tmpTotalSpend;
            _tmpTotalSpend = _cursor.getDouble(_cursorIndexOfTotalSpend);
            _item.setTotalSpend(_tmpTotalSpend);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<SpendEntry> getUnsyncedEntries(final long budgetId) {
    final String _sql = "SELECT * FROM SpendEntry WHERE isSynced = 0 AND budgetId= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, budgetId);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBudgetId = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetId");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfHomeSpend = CursorUtil.getColumnIndexOrThrow(_cursor, "homeSpend");
      final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
      final int _cursorIndexOfHomeRate = CursorUtil.getColumnIndexOrThrow(_cursor, "homeRate");
      final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfSpendEntryGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "spendEntryGroupId");
      final int _cursorIndexOfDatesAreSplit = CursorUtil.getColumnIndexOrThrow(_cursor, "datesAreSplit");
      final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
      final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
      final int _cursorIndexOfTotalSpend = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSpend");
      final List<SpendEntry> _result = new ArrayList<SpendEntry>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final SpendEntry _item;
        _item = new SpendEntry();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        final long _tmpBudgetId;
        _tmpBudgetId = _cursor.getLong(_cursorIndexOfBudgetId);
        _item.setBudgetId(_tmpBudgetId);
        final Date _tmpDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDate);
        }
        _tmpDate = DateConverter.toDate(_tmp);
        _item.setDate(_tmpDate);
        final double _tmpCost;
        _tmpCost = _cursor.getDouble(_cursorIndexOfCost);
        _item.setCost(_tmpCost);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        final String _tmpCurrency;
        _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
        _item.setCurrency(_tmpCurrency);
        final double _tmpHomeSpend;
        _tmpHomeSpend = _cursor.getDouble(_cursorIndexOfHomeSpend);
        _item.setHomeSpend(_tmpHomeSpend);
        final double _tmpRate;
        _tmpRate = _cursor.getDouble(_cursorIndexOfRate);
        _item.setRate(_tmpRate);
        final double _tmpHomeRate;
        _tmpHomeRate = _cursor.getDouble(_cursorIndexOfHomeRate);
        _item.setHomeRate(_tmpHomeRate);
        final boolean _tmpIsSynced;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
        _tmpIsSynced = _tmp_1 != 0;
        _item.setSynced(_tmpIsSynced);
        final String _tmpCategory;
        _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        _item.setCategory(_tmpCategory);
        final long _tmpSpendEntryGroupId;
        _tmpSpendEntryGroupId = _cursor.getLong(_cursorIndexOfSpendEntryGroupId);
        _item.setSpendEntryGroupId(_tmpSpendEntryGroupId);
        final boolean _tmpDatesAreSplit;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfDatesAreSplit);
        _tmpDatesAreSplit = _tmp_2 != 0;
        _item.setDatesAreSplit(_tmpDatesAreSplit);
        final Date _tmpStartDate;
        final Long _tmp_3;
        if (_cursor.isNull(_cursorIndexOfStartDate)) {
          _tmp_3 = null;
        } else {
          _tmp_3 = _cursor.getLong(_cursorIndexOfStartDate);
        }
        _tmpStartDate = DateConverter.toDate(_tmp_3);
        _item.setStartDate(_tmpStartDate);
        final Date _tmpEndDate;
        final Long _tmp_4;
        if (_cursor.isNull(_cursorIndexOfEndDate)) {
          _tmp_4 = null;
        } else {
          _tmp_4 = _cursor.getLong(_cursorIndexOfEndDate);
        }
        _tmpEndDate = DateConverter.toDate(_tmp_4);
        _item.setEndDate(_tmpEndDate);
        final double _tmpTotalSpend;
        _tmpTotalSpend = _cursor.getDouble(_cursorIndexOfTotalSpend);
        _item.setTotalSpend(_tmpTotalSpend);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public SpendEntry getSpendEntryFromId(final long id) {
    final String _sql = "SELECT * FROM SpendEntry WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBudgetId = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetId");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfHomeSpend = CursorUtil.getColumnIndexOrThrow(_cursor, "homeSpend");
      final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
      final int _cursorIndexOfHomeRate = CursorUtil.getColumnIndexOrThrow(_cursor, "homeRate");
      final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfSpendEntryGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "spendEntryGroupId");
      final int _cursorIndexOfDatesAreSplit = CursorUtil.getColumnIndexOrThrow(_cursor, "datesAreSplit");
      final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
      final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
      final int _cursorIndexOfTotalSpend = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSpend");
      final SpendEntry _result;
      if(_cursor.moveToFirst()) {
        _result = new SpendEntry();
        _result.id = _cursor.getLong(_cursorIndexOfId);
        final long _tmpBudgetId;
        _tmpBudgetId = _cursor.getLong(_cursorIndexOfBudgetId);
        _result.setBudgetId(_tmpBudgetId);
        final Date _tmpDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDate);
        }
        _tmpDate = DateConverter.toDate(_tmp);
        _result.setDate(_tmpDate);
        final double _tmpCost;
        _tmpCost = _cursor.getDouble(_cursorIndexOfCost);
        _result.setCost(_tmpCost);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _result.setDescription(_tmpDescription);
        final String _tmpCurrency;
        _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
        _result.setCurrency(_tmpCurrency);
        final double _tmpHomeSpend;
        _tmpHomeSpend = _cursor.getDouble(_cursorIndexOfHomeSpend);
        _result.setHomeSpend(_tmpHomeSpend);
        final double _tmpRate;
        _tmpRate = _cursor.getDouble(_cursorIndexOfRate);
        _result.setRate(_tmpRate);
        final double _tmpHomeRate;
        _tmpHomeRate = _cursor.getDouble(_cursorIndexOfHomeRate);
        _result.setHomeRate(_tmpHomeRate);
        final boolean _tmpIsSynced;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
        _tmpIsSynced = _tmp_1 != 0;
        _result.setSynced(_tmpIsSynced);
        final String _tmpCategory;
        _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        _result.setCategory(_tmpCategory);
        final long _tmpSpendEntryGroupId;
        _tmpSpendEntryGroupId = _cursor.getLong(_cursorIndexOfSpendEntryGroupId);
        _result.setSpendEntryGroupId(_tmpSpendEntryGroupId);
        final boolean _tmpDatesAreSplit;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfDatesAreSplit);
        _tmpDatesAreSplit = _tmp_2 != 0;
        _result.setDatesAreSplit(_tmpDatesAreSplit);
        final Date _tmpStartDate;
        final Long _tmp_3;
        if (_cursor.isNull(_cursorIndexOfStartDate)) {
          _tmp_3 = null;
        } else {
          _tmp_3 = _cursor.getLong(_cursorIndexOfStartDate);
        }
        _tmpStartDate = DateConverter.toDate(_tmp_3);
        _result.setStartDate(_tmpStartDate);
        final Date _tmpEndDate;
        final Long _tmp_4;
        if (_cursor.isNull(_cursorIndexOfEndDate)) {
          _tmp_4 = null;
        } else {
          _tmp_4 = _cursor.getLong(_cursorIndexOfEndDate);
        }
        _tmpEndDate = DateConverter.toDate(_tmp_4);
        _result.setEndDate(_tmpEndDate);
        final double _tmpTotalSpend;
        _tmpTotalSpend = _cursor.getDouble(_cursorIndexOfTotalSpend);
        _result.setTotalSpend(_tmpTotalSpend);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<SpendEntry> getSpendEntriesForBudget(final long budgetId) {
    final String _sql = "SELECT * FROM SpendEntry WHERE budgetId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, budgetId);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBudgetId = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetId");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfHomeSpend = CursorUtil.getColumnIndexOrThrow(_cursor, "homeSpend");
      final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
      final int _cursorIndexOfHomeRate = CursorUtil.getColumnIndexOrThrow(_cursor, "homeRate");
      final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfSpendEntryGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "spendEntryGroupId");
      final int _cursorIndexOfDatesAreSplit = CursorUtil.getColumnIndexOrThrow(_cursor, "datesAreSplit");
      final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
      final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
      final int _cursorIndexOfTotalSpend = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSpend");
      final List<SpendEntry> _result = new ArrayList<SpendEntry>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final SpendEntry _item;
        _item = new SpendEntry();
        _item.id = _cursor.getLong(_cursorIndexOfId);
        final long _tmpBudgetId;
        _tmpBudgetId = _cursor.getLong(_cursorIndexOfBudgetId);
        _item.setBudgetId(_tmpBudgetId);
        final Date _tmpDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDate);
        }
        _tmpDate = DateConverter.toDate(_tmp);
        _item.setDate(_tmpDate);
        final double _tmpCost;
        _tmpCost = _cursor.getDouble(_cursorIndexOfCost);
        _item.setCost(_tmpCost);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        final String _tmpCurrency;
        _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
        _item.setCurrency(_tmpCurrency);
        final double _tmpHomeSpend;
        _tmpHomeSpend = _cursor.getDouble(_cursorIndexOfHomeSpend);
        _item.setHomeSpend(_tmpHomeSpend);
        final double _tmpRate;
        _tmpRate = _cursor.getDouble(_cursorIndexOfRate);
        _item.setRate(_tmpRate);
        final double _tmpHomeRate;
        _tmpHomeRate = _cursor.getDouble(_cursorIndexOfHomeRate);
        _item.setHomeRate(_tmpHomeRate);
        final boolean _tmpIsSynced;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
        _tmpIsSynced = _tmp_1 != 0;
        _item.setSynced(_tmpIsSynced);
        final String _tmpCategory;
        _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        _item.setCategory(_tmpCategory);
        final long _tmpSpendEntryGroupId;
        _tmpSpendEntryGroupId = _cursor.getLong(_cursorIndexOfSpendEntryGroupId);
        _item.setSpendEntryGroupId(_tmpSpendEntryGroupId);
        final boolean _tmpDatesAreSplit;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfDatesAreSplit);
        _tmpDatesAreSplit = _tmp_2 != 0;
        _item.setDatesAreSplit(_tmpDatesAreSplit);
        final Date _tmpStartDate;
        final Long _tmp_3;
        if (_cursor.isNull(_cursorIndexOfStartDate)) {
          _tmp_3 = null;
        } else {
          _tmp_3 = _cursor.getLong(_cursorIndexOfStartDate);
        }
        _tmpStartDate = DateConverter.toDate(_tmp_3);
        _item.setStartDate(_tmpStartDate);
        final Date _tmpEndDate;
        final Long _tmp_4;
        if (_cursor.isNull(_cursorIndexOfEndDate)) {
          _tmp_4 = null;
        } else {
          _tmp_4 = _cursor.getLong(_cursorIndexOfEndDate);
        }
        _tmpEndDate = DateConverter.toDate(_tmp_4);
        _item.setEndDate(_tmpEndDate);
        final double _tmpTotalSpend;
        _tmpTotalSpend = _cursor.getDouble(_cursorIndexOfTotalSpend);
        _item.setTotalSpend(_tmpTotalSpend);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<SpendEntry>> getSpendEntriesForBudgetLive(final long budgetId) {
    final String _sql = "SELECT * FROM SpendEntry WHERE budgetId = ? ORDER BY date";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, budgetId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"SpendEntry"}, new Callable<List<SpendEntry>>() {
      @Override
      public List<SpendEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBudgetId = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCost = CursorUtil.getColumnIndexOrThrow(_cursor, "cost");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
          final int _cursorIndexOfHomeSpend = CursorUtil.getColumnIndexOrThrow(_cursor, "homeSpend");
          final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
          final int _cursorIndexOfHomeRate = CursorUtil.getColumnIndexOrThrow(_cursor, "homeRate");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSpendEntryGroupId = CursorUtil.getColumnIndexOrThrow(_cursor, "spendEntryGroupId");
          final int _cursorIndexOfDatesAreSplit = CursorUtil.getColumnIndexOrThrow(_cursor, "datesAreSplit");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfTotalSpend = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSpend");
          final List<SpendEntry> _result = new ArrayList<SpendEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final SpendEntry _item;
            _item = new SpendEntry();
            _item.id = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBudgetId;
            _tmpBudgetId = _cursor.getLong(_cursorIndexOfBudgetId);
            _item.setBudgetId(_tmpBudgetId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp);
            _item.setDate(_tmpDate);
            final double _tmpCost;
            _tmpCost = _cursor.getDouble(_cursorIndexOfCost);
            _item.setCost(_tmpCost);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item.setDescription(_tmpDescription);
            final String _tmpCurrency;
            _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
            _item.setCurrency(_tmpCurrency);
            final double _tmpHomeSpend;
            _tmpHomeSpend = _cursor.getDouble(_cursorIndexOfHomeSpend);
            _item.setHomeSpend(_tmpHomeSpend);
            final double _tmpRate;
            _tmpRate = _cursor.getDouble(_cursorIndexOfRate);
            _item.setRate(_tmpRate);
            final double _tmpHomeRate;
            _tmpHomeRate = _cursor.getDouble(_cursorIndexOfHomeRate);
            _item.setHomeRate(_tmpHomeRate);
            final boolean _tmpIsSynced;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_1 != 0;
            _item.setSynced(_tmpIsSynced);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            _item.setCategory(_tmpCategory);
            final long _tmpSpendEntryGroupId;
            _tmpSpendEntryGroupId = _cursor.getLong(_cursorIndexOfSpendEntryGroupId);
            _item.setSpendEntryGroupId(_tmpSpendEntryGroupId);
            final boolean _tmpDatesAreSplit;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfDatesAreSplit);
            _tmpDatesAreSplit = _tmp_2 != 0;
            _item.setDatesAreSplit(_tmpDatesAreSplit);
            final Date _tmpStartDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfStartDate);
            }
            _tmpStartDate = DateConverter.toDate(_tmp_3);
            _item.setStartDate(_tmpStartDate);
            final Date _tmpEndDate;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfEndDate);
            }
            _tmpEndDate = DateConverter.toDate(_tmp_4);
            _item.setEndDate(_tmpEndDate);
            final double _tmpTotalSpend;
            _tmpTotalSpend = _cursor.getDouble(_cursorIndexOfTotalSpend);
            _item.setTotalSpend(_tmpTotalSpend);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public long getLastSpendEntryGroupId() {
    final String _sql = "SELECT spendEntryGroupId FROM SpendEntry ORDER BY spendEntryGroupId DESC LIMIT 1;";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final long _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getLong(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
