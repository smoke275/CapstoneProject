package com.smokescreem.shash.foodscout.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Shash on 5/20/2017.
 */

@ContentProvider(authority = DiaryProvider.AUTHORITY,
        database = DiaryDatabase.class,
        packageName = "com.smokescreem.shash.foodscout.provider"
)
public class DiaryProvider {
    public static final String AUTHORITY = "com.smokescreem.shash.foodscout.DiaryProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = DiaryDatabase.MEMORY)
    public static class Memories {
        @ContentUri(path = DiaryDatabase.MEMORY,
                type = "vnd.android.cursor.item/memory",
                defaultSort = DiaryColumns.DATE + " ASC")
        public static final Uri CONTENT_URI = buildUri(DiaryDatabase.MEMORY);

        @InexactContentUri(
                name = "MEMORY_BY_ID",
                path = DiaryDatabase.MEMORY + "/#",
                type = "vnd.android.cursor.item/memory",
                whereColumn = DiaryColumns.ID,
                pathSegment = 1
        )
        public static Uri withId(String id) {
            return buildUri(DiaryDatabase.MEMORY, id);
        }

        @InexactContentUri(
                name = "MEMORY_BY_HEADER",
                path = DiaryDatabase.MEMORY + "/$",
                type = "vnd.android.cursor.item/memory",
                whereColumn = DiaryColumns.HEADER,
                pathSegment = 1
        )
        public static Uri withTitle(String header) {
            return buildUri(DiaryDatabase.MEMORY, header);
        }

    }
}