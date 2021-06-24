package com.jun.jpa.specification;

import com.jun.jpa.dao.UserQueryVo;
import com.jun.jpa.model.Permission;
import com.jun.jpa.model.Role;
import com.jun.jpa.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author songjun
 * @date 2021-06-24
 * @desc
 */
public class UserSpecification implements Specification<User> {

    private UserQueryVo param;

    public UserSpecification(UserQueryVo userQueryVo) {
        this.param = userQueryVo;
    }

    @Override
    public Specification<User> and(Specification<User> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<User> or(Specification<User> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        /**
         * 2张表
         */
        if (StringUtils.hasLength(param.getRoleName())) {
            Join<Role, User> join = root.join("role", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("roleName"), param.getRoleName()));
        }
        /**
         * 跨表查询，3个表
         * 需要通过权限名称查出对应的用户，User.role.permission = param.getPermissionName()
         * JoinType.LEFT主要是说"role"这个属性是在哪个表中，这里是在User中。
         * 此处debug就可以发现，执行第一行之后，join中的Model就变成了Role。
         * 此处是要通过权限名称查出对应的用户。所以先root.join("role", JoinType.LEFT)拿到JpaRole，然后再join.get("permission")拿到Permission ，然后再匹配它的属性permissionName
         * 这里就是get出相应的属性，一直到你得到想要的属性为止。
         */
        if (StringUtils.hasLength(param.getPermissionName())) {
            Join<Permission, User> join = root.join("role", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(join.get("permission").get("permissionName"), param.getPermissionName()));
        }
        /**
         * in查询
         */
        if (StringUtils.hasLength(param.getIds())) {
            CriteriaBuilder.In in = criteriaBuilder.in(root.get("id").as(Long.class));
            List<String> ids = Arrays.asList(param.getIds().split(","));
            ids.forEach(s -> in.value(Long.parseLong(s)));
            predicates.add(criteriaBuilder.and(in));
        }
        /**
         * like查询
         */
        if (StringUtils.hasLength(param.getUserName())) {
            predicates.add(criteriaBuilder.like(root.get("userName"), "%" + param.getUserName() + "%"));
        }
        /**
         * 全等 = 查询
         */
        if (StringUtils.hasLength(param.getNickName())) {
            predicates.add(criteriaBuilder.equal(root.get("nickName"), param.getNickName()));
        }
        /**
         * 大于小于等等
         */
        if (param.getAge() != null) {
            predicates.add(criteriaBuilder.le(root.get("age"), param.getAge()));
            predicates.add(criteriaBuilder.gt(root.get("age"), param.getAge() - 10));
        }
        return predicates.isEmpty() ? criteriaBuilder.conjunction() : criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
