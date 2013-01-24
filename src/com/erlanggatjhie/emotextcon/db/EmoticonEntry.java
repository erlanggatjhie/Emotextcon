package com.erlanggatjhie.emotextcon.db;

import android.provider.BaseColumns;

public abstract class EmoticonEntry implements BaseColumns{
	public static final String TABLE_NAME = "Emoticon";
	public static final String COLUMN_NAME_EMOTICON_ID = "emoticonid";
	public static final String COLUMN_NAME_DESCRIPTION = "description";
	public static final String COLUMN_NAME_CONTENT = "content";
}
