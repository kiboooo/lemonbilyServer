package com.lemonbily.springboot.entity;

import java.io.Serializable;

public class Account implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.AId
     *
     * @mbggenerated
     */
    private Integer AId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.AName
     *
     * @mbggenerated
     */
    private String AName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.ASex
     *
     * @mbggenerated
     */
    private String ASex;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.AAvatar
     *
     * @mbggenerated
     */
    private String AAvatar;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table account
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.AId
     *
     * @return the value of account.AId
     *
     * @mbggenerated
     */
    public Integer getAId() {
        return AId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.AId
     *
     * @param AId the value for account.AId
     *
     * @mbggenerated
     */
    public void setAId(Integer AId) {
        this.AId = AId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.AName
     *
     * @return the value of account.AName
     *
     * @mbggenerated
     */
    public String getAName() {
        return AName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.AName
     *
     * @param AName the value for account.AName
     *
     * @mbggenerated
     */
    public void setAName(String AName) {
        this.AName = AName == null ? null : AName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.ASex
     *
     * @return the value of account.ASex
     *
     * @mbggenerated
     */
    public String getASex() {
        return ASex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.ASex
     *
     * @param ASex the value for account.ASex
     *
     * @mbggenerated
     */
    public void setASex(String ASex) {
        this.ASex = ASex == null ? null : ASex.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.AAvatar
     *
     * @return the value of account.AAvatar
     *
     * @mbggenerated
     */
    public String getAAvatar() {
        return AAvatar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.AAvatar
     *
     * @param AAvatar the value for account.AAvatar
     *
     * @mbggenerated
     */
    public void setAAvatar(String AAvatar) {
        this.AAvatar = AAvatar == null ? null : AAvatar.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", AId=").append(AId);
        sb.append(", AName=").append(AName);
        sb.append(", ASex=").append(ASex);
        sb.append(", AAvatar=").append(AAvatar);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}