package com.bugprovider.seed.modules.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bugprovider.seed.modules.ums.mapper.UmsRoleResourceRelationMapper;
import com.bugprovider.seed.modules.ums.model.UmsRoleResourceRelation;
import com.bugprovider.seed.modules.ums.service.UmsRoleResourceRelationService;
import org.springframework.stereotype.Service;

/**
 * 角色资源关系管理Service实现类
 */
@Service
public class UmsRoleResourceRelationServiceImpl extends ServiceImpl<UmsRoleResourceRelationMapper, UmsRoleResourceRelation> implements UmsRoleResourceRelationService {
}
