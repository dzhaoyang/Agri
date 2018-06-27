package com.sunsea.parkinghere.biz.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.sunsea.parkinghere.biz.model.Group;
import com.sunsea.parkinghere.biz.model.Role;
import com.sunsea.parkinghere.biz.model.User;
import com.sunsea.parkinghere.biz.repository.custom.UserRepositoryCustom;

@Repository
public interface UserRepository extends
                               AbstractRepository<User>,
                               UserRepositoryCustom {
	
	public User findByUsernameOrPhoneNumber(String username,String phoneNumber);
    
    public User findByUsername(String username);
    
    public List<User> findByGroups(Group group);
    
    public User findByEmail(String email);
    
    public List<User> findByUsernameLike(String username);
    
    public Page<User> findByUsernameLike(String username, Pageable pageable);
    
    public Page<User> findByUsername(String username, Pageable pageable);
    
    @Query("{'$and':[{'username':{$regex : ?0}}, {'domain':{'$ref':'TenancyDomains','$id':?1}}, {'username' : {$ne :'administrator'}}]}")
    public Page<User> findByUsernameWithoutAdmin(String username,
                                                 String domainId,
                                                 Pageable pageable);
    
    public Page<User> findByGroups(Group group, Pageable pageable);
    
    public Page<User> findByGroupsAndUsernameNot(Group group,
                                                 String username,
                                                 Pageable pageable);
    
    @Query("{'$and':[{'groups':{'$ref':'Groups','$id':?0}},{'username':{$regex : ?1}},{'username' : {$ne :'administrator'}}]}")
    public Page<User> findByGroupWithoutAdmin(String groupId,
                                              String username,
                                              Pageable pageable);
    
    public Page<User> findByRoles(Role role, Pageable pageable);
    
    public Page<User> findByRolesAndUsernameLike(Role role,
                                                 String username,
                                                 Pageable pageable);
    
    public Page<User> findByRolesAndUsernameNot(Role role,
                                                String username,
                                                Pageable pageable);
    
    @Query("{'$and':[{'roles': {'$ref':'Roles','$id':?0}},{'username':{$regex : ?1}},{'domain':{'$ref':'TenancyDomains','$id':?2}},{'username' : {$ne :'administrator'}}]}")
    public Page<User> findByRolesWithoutAdmin(ObjectId roleId,
                                              String username,
                                              String domainId,
                                              Pageable pageable);
    
    @Query("{'$and':[" + "{'username':{$regex : ?0}},"
           + "{'email':{$regex : ?1}},"
           + "{'name':{$regex : ?2}},"
           + "{'phoneNumber':{$regex : ?3}}"
           + "]}")
    public Page<User> findByParams(String username,
                                   String email,
                                   String name,
                                   String phoneNumber,
                                   Pageable pageable);
    
    @Query("{'$and':[" + "{'username':{$regex : ?0}},"
           + "{'email':{$regex : ?1}},"
           + "{'name':{$regex : ?2}},"
           + "{'phoneNumber':{$regex : ?3}},"
           + "{'roles': {'$ref':'Roles','$id':{'$oid':?4}}}"
           + "]}")
    public Page<User> findByParamsWithRoles(String username,
                                            String email,
                                            String name,
                                            String phoneNumber,
                                            String roleId,
                                            Pageable pageable);
    
    @Query("{'$and':[" + "{'username':{$regex : ?0}},"
           + "{'email':{$regex : ?1}},"
           + "{'name':{$regex : ?2}},"
           + "{'phoneNumber':{$regex : ?3}},"
           + "{'groups':{'$ref':'Groups','$id':{'$oid':?4}}}"
           + "]}")
    public Page<User> findByParamsWithGroups(String username,
                                             String email,
                                             String name,
                                             String phoneNumber,
                                             String groupId,
                                             Pageable pageable);
    
    @Query("{'$and':[" + "{'username':{$regex : ?0}},"
           + "{'email':{$regex : ?1}},"
           + "{'name':{$regex : ?2}},"
           + "{'phoneNumber':{$regex : ?3}},"
           + "{'roles': {'$ref':'Roles','$id':{'$oid':?4}}},"
           + "{'groups':{'$ref':'Groups','$id':{'$oid':?5}}}"
           + "]}")
    public Page<User> findByParamsWithGroupsAndRoles(String username,
                                                     String email,
                                                     String name,
                                                     String phoneNumber,
                                                     String roleId,
                                                     String groupId,
                                                     Pageable pageable);
}
