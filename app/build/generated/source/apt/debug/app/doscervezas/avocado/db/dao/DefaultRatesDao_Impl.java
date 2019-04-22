package app.doscervezas.avocado.db.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import app.doscervezas.avocado.db.entity.DefaultRatesData;
import java.lang.Double;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class DefaultRatesDao_Impl implements DefaultRatesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfDefaultRatesData;

  private final EntityInsertionAdapter __insertionAdapterOfDefaultRatesData_1;

  private final SharedSQLiteStatement __preparedStmtOfClearDatabase;

  public DefaultRatesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDefaultRatesData = new EntityInsertionAdapter<DefaultRatesData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `DefaultRatesData`(`id`,`currency`,`rate`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DefaultRatesData value) {
        stmt.bindLong(1, value.id);
        if (value.getCurrency() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCurrency());
        }
        stmt.bindDouble(3, value.getRate());
      }
    };
    this.__insertionAdapterOfDefaultRatesData_1 = new EntityInsertionAdapter<DefaultRatesData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `DefaultRatesData`(`id`,`currency`,`rate`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DefaultRatesData value) {
        stmt.bindLong(1, value.id);
        if (value.getCurrency() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCurrency());
        }
        stmt.bindDouble(3, value.getRate());
      }
    };
    this.__preparedStmtOfClearDatabase = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM DefaultRatesData";
        return _query;
      }
    };
  }

  @Override
  public void addDefaultRates(final DefaultRatesData... defaultRatesDatas) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfDefaultRatesData.insert(defaultRatesDatas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final DefaultRatesData... defaultRatesDatas) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfDefaultRatesData_1.insert(defaultRatesDatas);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clearDatabase() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearDatabase.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfClearDatabase.release(_stmt);
    }
  }

  @Override
  public Double getDefaultRate(final String currency) {
    final String _sql = "SELECT rate FROM DefaultRatesData WHERE (currency = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (currency == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, currency);
    }
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final Double _result;
      if(_cursor.moveToFirst()) {
        if (_cursor.isNull(0)) {
          _result = null;
        } else {
          _result = _cursor.getDouble(0);
        }
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
  public List<DefaultRatesData> selectAll() {
    final String _sql = "SELECT * FROM DefaultRatesData";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "currency");
      final int _cursorIndexOfRate = CursorUtil.getColumnIndexOrThrow(_cursor, "rate");
      final List<DefaultRatesData> _result = new ArrayList<DefaultRatesData>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DefaultRatesData _item;
        _item = new DefaultRatesData();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        final String _tmpCurrency;
        _tmpCurrency = _cursor.getString(_cursorIndexOfCurrency);
        _item.setCurrency(_tmpCurrency);
        final double _tmpRate;
        _tmpRate = _cursor.getDouble(_cursorIndexOfRate);
        _item.setRate(_tmpRate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<String> selectAllCurrencies() {
    final String _sql = "SELECT currency FROM DefaultRatesData";
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
