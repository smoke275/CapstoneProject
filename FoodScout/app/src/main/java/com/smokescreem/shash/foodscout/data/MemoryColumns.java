package com.smokescreem.shash.foodscout.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Shash on 5/20/2017.
 */

public interface MemoryColumns {
    @DataType(DataType.Type.TEXT)
    @PrimaryKey
    String ID = "id";
    @DataType(DataType.Type.TEXT)
    @NotNull
    String DATE = "date";
    @DataType(DataType.Type.TEXT)
    String HEADER = "header";
    @DataType(DataType.Type.TEXT)
    String BODY = "body";
    @DataType(DataType.Type.TEXT)
    String LATITUDE = "latitude";
    @DataType(DataType.Type.TEXT)
    String LONGITUDE = "longitude";
}