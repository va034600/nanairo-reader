package com.gmail.va034600.nreader.business;

//TOOD classに変更
public interface NanairoContext {

	void beginTransaction();

	void setTransactionSuccessful();

	void endTransaction();

}
