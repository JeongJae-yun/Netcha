package com.example.user.movieterm4;

public class MemberData {

    private String memberId;
    private String memberName;
    private String email;
    private String phoneNumber;

    public MemberData(String memberId, String memberName, String email, String phoneNumber) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getMemberId() {
        return memberId;
    }

    public MemberData setMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public String getMemberName() {
        return memberName;
    }

    public MemberData setMemberName(String memberName) {
        this.memberName = memberName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public MemberData setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public MemberData setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
