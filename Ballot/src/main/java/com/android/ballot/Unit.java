package com.android.ballot;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * TODO
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @version 1.0.0
 * @since 2020/12/02 16:49
 */
public class Unit implements Parcelable {
    private List<UnitBean> unit;

    public Unit() {

    }

    public List<UnitBean> getUnit() {
        return unit;
    }

    public void setUnit(List<UnitBean> unit) {
        this.unit = unit;
    }

    protected Unit(Parcel in) {
        unit = in.createTypedArrayList(UnitBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(unit);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Unit> CREATOR = new Creator<Unit>() {
        @Override
        public Unit createFromParcel(Parcel in) {
            return new Unit(in);
        }

        @Override
        public Unit[] newArray(int size) {
            return new Unit[size];
        }
    };

    public static class UnitBean implements Parcelable {

        public UnitBean() {
        }

        private String unitName;

        private List<ClassBean> classList;

        protected UnitBean(Parcel in) {
            unitName = in.readString();
            classList = in.createTypedArrayList(ClassBean.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(unitName);
            dest.writeTypedList(classList);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<UnitBean> CREATOR = new Creator<UnitBean>() {
            @Override
            public UnitBean createFromParcel(Parcel in) {
                return new UnitBean(in);
            }

            @Override
            public UnitBean[] newArray(int size) {
                return new UnitBean[size];
            }
        };

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public List<ClassBean> getClassList() {
            return classList;
        }

        public void setClassList(List<ClassBean> classList) {
            this.classList = classList;
        }


    }

    public static class ClassBean implements Parcelable {
        /**
         * IS_RECOMMEND isRecommend 推荐类型
         * IS_BALLOT isBallot 是抽选类型
         * IS_NULL isNull 没被抽中的班级  default
         */
        private String ballotType;
        private String className;
        private String unitName;

        public ClassBean() {

        }

        protected ClassBean(Parcel in) {
            ballotType = in.readString();
            className = in.readString();
            unitName = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(ballotType);
            dest.writeString(className);
            dest.writeString(unitName);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ClassBean> CREATOR = new Creator<ClassBean>() {
            @Override
            public ClassBean createFromParcel(Parcel in) {
                return new ClassBean(in);
            }

            @Override
            public ClassBean[] newArray(int size) {
                return new ClassBean[size];
            }
        };

        public String getBallotType() {
            return ballotType;
        }

        public void setBallotType(String ballotType) {
            this.ballotType = ballotType;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }
    }
}
