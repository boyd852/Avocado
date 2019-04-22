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
import app.doscervezas.avocado.db.entity.Budget;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings("unchecked")
public final class BudgetDao_Impl implements BudgetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfBudget;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfBudget;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByBudgetName;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByBudgetId;

  public BudgetDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBudget = new EntityInsertionAdapter<Budget>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Budget`(`id`,`budgetName`,`budgetCurrency`,`dailyBudget`,`budgetDate`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Budget value) {
        stmt.bindLong(1, value.getId());
        if (value.getBudgetName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getBudgetName());
        }
        if (value.getBudgetCurrency() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getBudgetCurrency());
        }
        if (value.getDailyBudget() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindDouble(4, value.getDailyBudget());
        }
        final Long _tmp;
        _tmp = DateConverter.toTimeStamp(value.getBudgetDate());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
      }
    };
    this.__updateAdapterOfBudget = new EntityDeletionOrUpdateAdapter<Budget>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Budget` SET `id` = ?,`budgetName` = ?,`budgetCurrency` = ?,`dailyBudget` = ?,`budgetDate` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Budget value) {
        stmt.bindLong(1, value.getId());
        if (value.getBudgetName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getBudgetName());
        }
        if (value.getBudgetCurrency() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getBudgetCurrency());
        }
        if (value.getDailyBudget() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindDouble(4, value.getDailyBudget());
        }
        final Long _tmp;
        _tmp = DateConverter.toTimeStamp(value.getBudgetDate());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        stmt.bindLong(6, value.getId());
      }
    };
    this.__preparedStmtOfDeleteByBudgetName = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Budget WHERE budgetName = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByBudgetId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Budget WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertBudget(final Budget budget) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfBudget.insert(budget);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateBudget(final Budget updatedBudget) {
    __db.beginTransaction();
    try {
      __updateAdapterOfBudget.handle(updatedBudget);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteByBudgetName(final String budgetName) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByBudgetName.acquire();
    int _argIndex = 1;
    if (budgetName == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, budgetName);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteByBudgetName.release(_stmt);
    }
  }

  @Override
  public void deleteByBudgetId(final long id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByBudgetId.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteByBudgetId.release(_stmt);
    }
  }

  @Override
  public List<Budget> initBudgets() {
    final String _sql = "SELECT * FROM Budget";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBudgetName = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetName");
      final int _cursorIndexOfBudgetCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetCurrency");
      final int _cursorIndexOfDailyBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyBudget");
      final int _cursorIndexOfBudgetDate = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetDate");
      final List<Budget> _result = new ArrayList<Budget>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Budget _item;
        _item = new Budget();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpBudgetName;
        _tmpBudgetName = _cursor.getString(_cursorIndexOfBudgetName);
        _item.setBudgetName(_tmpBudgetName);
        final String _tmpBudgetCurrency;
        _tmpBudgetCurrency = _cursor.getString(_cursorIndexOfBudgetCurrency);
        _item.setBudgetCurrency(_tmpBudgetCurrency);
        final Double _tmpDailyBudget;
        if (_cursor.isNull(_cursorIndexOfDailyBudget)) {
          _tmpDailyBudget = null;
        } else {
          _tmpDailyBudget = _cursor.getDouble(_cursorIndexOfDailyBudget);
        }
        _item.setDailyBudget(_tmpDailyBudget);
        final Date _tmpBudgetDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfBudgetDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfBudgetDate);
        }
        _tmpBudgetDate = DateConverter.toDate(_tmp);
        _item.setBudgetDate(_tmpBudgetDate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Budget>> getAllBudgets() {
    final String _sql = "SELECT * FROM Budget";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Budget"}, new Callable<List<Budget>>() {
      @Override
      public List<Budget> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBudgetName = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetName");
          final int _cursorIndexOfBudgetCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetCurrency");
          final int _cursorIndexOfDailyBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyBudget");
          final int _cursorIndexOfBudgetDate = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetDate");
          final List<Budget> _result = new ArrayList<Budget>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Budget _item;
            _item = new Budget();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpBudgetName;
            _tmpBudgetName = _cursor.getString(_cursorIndexOfBudgetName);
            _item.setBudgetName(_tmpBudgetName);
            final String _tmpBudgetCurrency;
            _tmpBudgetCurrency = _cursor.getString(_cursorIndexOfBudgetCurrency);
            _item.setBudgetCurrency(_tmpBudgetCurrency);
            final Double _tmpDailyBudget;
            if (_cursor.isNull(_cursorIndexOfDailyBudget)) {
              _tmpDailyBudget = null;
            } else {
              _tmpDailyBudget = _cursor.getDouble(_cursorIndexOfDailyBudget);
            }
            _item.setDailyBudget(_tmpDailyBudget);
            final Date _tmpBudgetDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfBudgetDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfBudgetDate);
            }
            _tmpBudgetDate = DateConverter.toDate(_tmp);
            _item.setBudgetDate(_tmpBudgetDate);
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
  public Budget getBudget(final long id) {
    final String _sql = "SELECT * FROM Budget WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBudgetName = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetName");
      final int _cursorIndexOfBudgetCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetCurrency");
      final int _cursorIndexOfDailyBudget = CursorUtil.getColumnIndexOrThrow(_cursor, "dailyBudget");
      final int _cursorIndexOfBudgetDate = CursorUtil.getColumnIndexOrThrow(_cursor, "budgetDate");
      final Budget _result;
      if(_cursor.moveToFirst()) {
        _result = new Budget();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpBudgetName;
        _tmpBudgetName = _cursor.getString(_cursorIndexOfBudgetName);
        _result.setBudgetName(_tmpBudgetName);
        final String _tmpBudgetCurrency;
        _tmpBudgetCurrency = _cursor.getString(_cursorIndexOfBudgetCurrency);
        _result.setBudgetCurrency(_tmpBudgetCurrency);
        final Double _tmpDailyBudget;
        if (_cursor.isNull(_cursorIndexOfDailyBudget)) {
          _tmpDailyBudget = null;
        } else {
          _tmpDailyBudget = _cursor.getDouble(_cursorIndexOfDailyBudget);
        }
        _result.setDailyBudget(_tmpDailyBudget);
        final Date _tmpBudgetDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfBudgetDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfBudgetDate);
        }
        _tmpBudgetDate = DateConverter.toDate(_tmp);
        _result.setBudgetDate(_tmpBudgetDate);
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
  public long getIdFromBudgetName(final String budgetName) {
    final String _sql = "SELECT id FROM Budget WHERE budgetName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (budgetName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, budgetName);
    }
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

  @Override
  public List<String> getBudgetNames() {
    final String _sql = "SELECT budgetName FROM Budget";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final String _item;
        _item = _cursor.getString(0);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
