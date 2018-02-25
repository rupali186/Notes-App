package com.example.rupali.notes;

/**
 * Created by RUPALI on 24-02-2018.
 */

public class Comment {
    private String comment;
    private int commentId;
    private int noteId;

    public Comment(String comment,int commentId,int noteId) {
        this.comment = comment;
        this.commentId=commentId;
        this.noteId=noteId;
    }

    public Comment(String comment,int noteId) {
        this.comment = comment;
        this.noteId=noteId;
        commentId=-1;
    }
}
