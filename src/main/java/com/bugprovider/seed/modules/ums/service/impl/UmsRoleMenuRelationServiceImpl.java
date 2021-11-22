package com.bugprovider.seed.modules.ums.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bugprovider.seed.modules.ums.mapper.UmsRoleMenuRelationMapper;
import com.bugprovider.seed.modules.ums.model.UmsRoleMenuRelation;
import com.bugprovider.seed.modules.ums.service.UmsRoleMenuRelationService;
import org.springframework.stereotype.Service;

/**
 * 角色菜单关系管理Service实现类
 */
@Service
public class UmsRoleMenuRelationServiceImpl extends ServiceImpl<UmsRoleMenuRelationMapper, UmsRoleMenuRelation> implements UmsRoleMenuRelationService {
}
