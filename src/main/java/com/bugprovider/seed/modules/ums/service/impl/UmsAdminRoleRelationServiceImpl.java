package com.bugprovider.seed.modules.ums.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bugprovider.seed.modules.ums.mapper.UmsAdminRoleRelationMapper;
import com.bugprovider.seed.modules.ums.model.UmsAdminRoleRelation;
import com.bugprovider.seed.modules.ums.service.UmsAdminRoleRelationService;
import org.springframework.stereotype.Service;

/**
 * 管理员角色关系管理Service实现类
 */
@Service
public class UmsAdminRoleRelationServiceImpl extends ServiceImpl<UmsAdminRoleRelationMapper, UmsAdminRoleRelation> implements UmsAdminRoleRelationService {
}
