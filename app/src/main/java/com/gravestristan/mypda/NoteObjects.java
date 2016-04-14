package com.gravestristan.mypda;

import java.util.UUID;

/**
 * This class defines a note object.
 * Created by Tristan on 3/26/2016.
 */
public class NoteObjects implements AppStatics {

    private UUID mId;
    private String mNoteContents;

    /**
     * Basic constructor for a note object. It generates a random UUID.
     */
    public NoteObjects(){
        mId = UUID.randomUUID();
    }

    /**
     * This method is used to get the UUID of a note object.
     * @return
     */
    public UUID getNoteId(){
        return mId;
    }

    /**
     * This method is used to set a UUID of a note object.
     * @param mId
     */
    public void setNoteId(UUID mId){
        this.mId = mId;
    }

    /**
     * This method is used to get the note contents of the note object.
     * @return
     */
    public String getNoteContents(){
        return mNoteContents;
    }

    /**
     * This method is used to set the note contents of the note object.
     * @param mNoteContents
     */
    public void setNoteContents(String mNoteContents){
        this.mNoteContents = mNoteContents;
    }

    /**
     * This method will return the objects UUID as a string. It should be used for debugging.
     * @return
     */
    @Override
    public String toString(){
        return "note UUID: " + mId;
    }
}
