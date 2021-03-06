package com.lemonbily.springboot.entity;

import java.io.Serializable;

public class Like implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column like.LikeID
     *
     * @mbggenerated
     */
    private Integer likeid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column like.LUserID
     *
     * @mbggenerated
     */
    private Integer luserid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column like.LTOUserID
     *
     * @mbggenerated
     */
    private Integer ltopalid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table like
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    public Like() {
    }

    public Like(Integer likeid, Integer luserid, Integer ltopalid) {
        this.likeid = likeid;
        this.luserid = luserid;
        this.ltopalid = ltopalid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column like.LikeID
     *
     * @return the value of like.LikeID
     *
     * @mbggenerated
     */
    public Integer getLikeid() {
        return likeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column like.LikeID
     *
     * @param likeid the value for like.LikeID
     *
     * @mbggenerated
     */
    public void setLikeid(Integer likeid) {
        this.likeid = likeid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column like.LUserID
     *
     * @return the value of like.LUserID
     *
     * @mbggenerated
     */
    public Integer getLuserid() {
        return luserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column like.LUserID
     *
     * @param luserid the value for like.LUserID
     *
     * @mbggenerated
     */
    public void setLuserid(Integer luserid) {
        this.luserid = luserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column like.LTOUserID
     *
     * @return the value of like.LTOUserID
     *
     * @mbggenerated
     */
    public Integer getLtopalid() {
        return ltopalid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column like.LTOUserID
     *
     * @param ltopalid the value for like.LTOUserID
     *
     * @mbggenerated
     */
    public void setLtopalid(Integer ltopalid) {
        this.ltopalid = ltopalid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table like
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", likeid=").append(likeid);
        sb.append(", luserid=").append(luserid);
        sb.append(", ltopalid=").append(ltopalid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}