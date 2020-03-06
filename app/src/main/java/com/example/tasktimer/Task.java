package com.example.tasktimer;

import java.io.Serializable;

class Task implements Serializable {
    public static final long serialVersionUID = 20200228L;
    private long m_Id;
    private final String mName;
    private final String mDescription;
    private final int mSortorder;

    public Task(long id, String name, String description, int sortorder) {
        m_Id = id;
        mName = name;
        mDescription = description;
        mSortorder = sortorder;
    }

    public long get_Id() {
        return m_Id;
    }

    public void setId(long id) {
        this.m_Id = id;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getSortorder() {
        return mSortorder;
    }

    @Override
    public String toString() {
        return "Task{" +
                "m_Id=" + m_Id +
                ", mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mSortorder='" + mSortorder + '\'' +
                '}';
    }
}
