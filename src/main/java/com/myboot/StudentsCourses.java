package com.myboot;

import java.util.Objects;

public class StudentsCourses {
    private int stdId;
    private int crsId;

    public StudentsCourses() {

    }

    public int getStdId() {
        return stdId;
    }

    public void setStdId(int stdId) {
        this.stdId = stdId;
    }

    public int getCrsId() {
        return crsId;
    }

    public void setCrsId(int crsId) {
        this.crsId = crsId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StudentsCourses that = (StudentsCourses) o;
        return stdId == that.stdId && crsId == that.crsId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stdId, crsId);
    }
}
