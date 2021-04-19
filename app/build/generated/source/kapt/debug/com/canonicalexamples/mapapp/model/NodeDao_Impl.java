package com.canonicalexamples.mapapp.model;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class NodeDao_Impl implements NodeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Node> __insertionAdapterOfNode;

  private final EntityDeletionOrUpdateAdapter<Node> __updateAdapterOfNode;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public NodeDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNode = new EntityInsertionAdapter<Node>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `node_table` (`id`,`x`,`y`,`tag`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Node value) {
        stmt.bindLong(1, value.getId());
        stmt.bindDouble(2, value.getX());
        stmt.bindDouble(3, value.getY());
        if (value.getTag() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTag());
        }
      }
    };
    this.__updateAdapterOfNode = new EntityDeletionOrUpdateAdapter<Node>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `node_table` SET `id` = ?,`x` = ?,`y` = ?,`tag` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Node value) {
        stmt.bindLong(1, value.getId());
        stmt.bindDouble(2, value.getX());
        stmt.bindDouble(3, value.getY());
        if (value.getTag() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTag());
        }
        stmt.bindLong(5, value.getId());
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM node_table WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object create(final Node node, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfNode.insert(node);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object update(final Node node, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfNode.handle(node);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object delete(final int id, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, p1);
  }

  @Override
  public Object get(final int id, final Continuation<? super Node> p1) {
    final String _sql = "SELECT * FROM node_table WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.execute(__db, false, new Callable<Node>() {
      @Override
      public Node call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfX = CursorUtil.getColumnIndexOrThrow(_cursor, "x");
          final int _cursorIndexOfY = CursorUtil.getColumnIndexOrThrow(_cursor, "y");
          final int _cursorIndexOfTag = CursorUtil.getColumnIndexOrThrow(_cursor, "tag");
          final Node _result;
          if(_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpX;
            _tmpX = _cursor.getDouble(_cursorIndexOfX);
            final double _tmpY;
            _tmpY = _cursor.getDouble(_cursorIndexOfY);
            final String _tmpTag;
            _tmpTag = _cursor.getString(_cursorIndexOfTag);
            _result = new Node(_tmpId,_tmpX,_tmpY,_tmpTag);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, p1);
  }

  @Override
  public Object fetchNodes(final Continuation<? super List<Node>> p0) {
    final String _sql = "SELECT * FROM node_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.execute(__db, false, new Callable<List<Node>>() {
      @Override
      public List<Node> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfX = CursorUtil.getColumnIndexOrThrow(_cursor, "x");
          final int _cursorIndexOfY = CursorUtil.getColumnIndexOrThrow(_cursor, "y");
          final int _cursorIndexOfTag = CursorUtil.getColumnIndexOrThrow(_cursor, "tag");
          final List<Node> _result = new ArrayList<Node>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Node _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpX;
            _tmpX = _cursor.getDouble(_cursorIndexOfX);
            final double _tmpY;
            _tmpY = _cursor.getDouble(_cursorIndexOfY);
            final String _tmpTag;
            _tmpTag = _cursor.getString(_cursorIndexOfTag);
            _item = new Node(_tmpId,_tmpX,_tmpY,_tmpTag);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, p0);
  }

  @Override
  public Object getLastNode(final Continuation<? super Node> p0) {
    final String _sql = "SELECT * FROM node_table WHERE id = (SELECT MAX(id) FROM node_table)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.execute(__db, false, new Callable<Node>() {
      @Override
      public Node call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfX = CursorUtil.getColumnIndexOrThrow(_cursor, "x");
          final int _cursorIndexOfY = CursorUtil.getColumnIndexOrThrow(_cursor, "y");
          final int _cursorIndexOfTag = CursorUtil.getColumnIndexOrThrow(_cursor, "tag");
          final Node _result;
          if(_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final double _tmpX;
            _tmpX = _cursor.getDouble(_cursorIndexOfX);
            final double _tmpY;
            _tmpY = _cursor.getDouble(_cursorIndexOfY);
            final String _tmpTag;
            _tmpTag = _cursor.getString(_cursorIndexOfTag);
            _result = new Node(_tmpId,_tmpX,_tmpY,_tmpTag);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, p0);
  }

  @Override
  public Object getMaximumId(final Continuation<? super Integer> p0) {
    final String _sql = "SELECT MAX(id) FROM node_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.execute(__db, false, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, p0);
  }
}
