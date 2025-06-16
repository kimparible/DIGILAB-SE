package service;

import dao.MemberDAO;
import models.User;

import java.sql.SQLException;
import java.util.List;
import models.Member;

public class MemberService {
    private MemberDAO memberDAO;

    public MemberService() {
        this.memberDAO = new MemberDAO();
    }

    // ... (method-method lain, seperti getAllMembers) ...
    
    public List<User> getAllMembers() {
        return memberDAO.getAllMembers();
    }

    public User getMemberById(int memberId) throws SQLException {
        return memberDAO.getMemberById(memberId);
    }
    public boolean updateMemberProfile(int userId, String fullName, String bio, String description, String phone, String address) {
    return memberDAO.updateMemberProfile(userId, fullName, bio, description, phone, address);
}

}