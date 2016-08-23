package com.proper.enterprise.isj.user.repository;

import com.proper.enterprise.isj.user.model.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by think on 2016/8/12 0012.
 *
 */
public interface UserInfoRepository extends MongoRepository<UserInfo, String> {

	UserInfo getByUserId(String userId);

	UserInfo getByTelephone(String telephone);
}
