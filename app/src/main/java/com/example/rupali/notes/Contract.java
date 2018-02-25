package com.example.rupali.notes;

/**
 * Created by RUPALI on 20-02-2018.
 */

public class Contract {

    static final String DB_NAME="notes_db";
    static final int VERSION=1;
    class Notes{
        static final String TABLE_NAME="notes_table";
        static final String TITLE="notes_title";
        static final String DESCRIPTION="notes_des";
        static final String ID="notes_id";
    }
    class Comments{
        static final String TABLE_NAME="comments_table";
        static final String COMMENT="comments_title";
        static final String NOTE_ID="note_id";
        static final String COMMENT_ID="comments_id";
    }
}
