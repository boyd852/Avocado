package app.doscervezas.avocado.db.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import app.doscervezas.avocado.db.converter.DateConverter;
import app.doscervezas.avocado.db.entity.RateData;
import java.lang.Double;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
public final class RateDao_Impl implements RateDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfRateData;

  public RateDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRateData = new EntityInsertionAdapter<RateData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `RateData`(`id`,`currency`,`rate`,`date`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RateData value) {
        stmt.bindLong(1, value.id);
        if (value.getCurrency() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCurrency());
        }
        if (value.getRate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindDouble(3, value.getRate());
        }
        final Long _tmp;
        _tmp = DateConverter.toTimeStamp(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
      }
    };
  }

  @Override
  public void addRates(final RateData... rateData) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRateData.insert(rateData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void addRate(final RateData rateData) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRateData.insert(rateData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Double getRate(final Long date, final String currency) {
    final String _sql = "SELECT rate FROM RateData WHERE (date = ? AND currency = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, date);
    }
    _argIndex = 2;
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
  public Double getTempRate(final Long date, final String currency) {
    final String _sql = "SELECT rate FROM RateData WHERE (date = ? AND currency = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, date);
    }
    _argIndex = 2;
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
  public List<Date> getDates() {
    final String _sql = "SELECT date FROM RateData";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final List<Date> _result = new ArrayList<Date>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Date _item;
        final Long _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(0);
        }
        _item = DateConverter.toDate(_tmp);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
