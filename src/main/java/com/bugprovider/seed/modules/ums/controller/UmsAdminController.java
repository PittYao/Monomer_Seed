package com.bugprovider.seed.modules.ums.controller;

import cn.hutool.core.collection.CollUtil;
import cn.monitor4all.logRecord.annotation.OperationLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bugprovider.seed.common.api.CommonPage;
import com.bugprovider.seed.common.api.CommonResult;
import com.bugprovider.seed.common.domain.AnchorInfo;
import com.bugprovider.seed.common.domain.LogParam;
import com.bugprovider.seed.common.enums.AnchorState;
import com.bugprovider.seed.common.exception.Asserts;
import com.bugprovider.seed.modules.ums.dto.UmsAdminLoginParam;
import com.bugprovider.seed.modules.ums.dto.UmsAdminParam;
import com.bugprovider.seed.modules.ums.dto.UpdateAdminPasswordParam;
import com.bugprovider.seed.modules.ums.model.UmsAdmin;
import com.bugprovider.seed.modules.ums.model.UmsRole;
import com.bugprovider.seed.modules.ums.service.UmsAdminCacheService;
import com.bugprovider.seed.modules.ums.service.UmsAdminService;
import com.bugprovider.seed.modules.ums.service.UmsCaptchaService;
import com.bugprovider.seed.modules.ums.service.UmsRoleService;
import com.bugprovider.seed.security.config.captcha.CaptchaDTO;
import com.bugprovider.seed.utils.LogUtils;
import com.bugprovider.seed.utils.TaskInfoUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 后台用户管理
 */
@Slf4j
@RestController
@Api(tags = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {
    private static final String LOG_BIZ_TYPE = "UmsAdminController";

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsRoleService roleService;
    @Autowired
    private UmsAdminCacheService adminCacheService;
    @Autowired
    private UmsCaptchaService captchaService;

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public UmsAdmin register(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        return umsAdmin;
    }

    @ApiOperation(value = "登录以后返回token")
    @PostMapping(value = "/login")
    public Map<String, String> login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        // 检验验证码
        captchaService.checkCaptcha(umsAdminLoginParam);

        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return tokenMap;
    }

    @ApiOperation(value = "刷新token")
    @GetMapping(value = "/refreshToken")
    public Map<String, String> refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            Asserts.fail("token已经过期");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return tokenMap;
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping(value = "/info")
    @OperationLog(bizId = "#principal.name", bizType = "ums", msg = "'获取当前登录用户信息'")
    public CommonResult getAdminInfo(Principal principal) {
        if (principal == null) {
            Asserts.fail("当前登录用户不存在");
        }
        String username = principal.getName();
        UmsAdmin umsAdmin = adminService.getAdminByUsername(username);

        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        data.put("menus", roleService.getMenuList(umsAdmin.getId()));
        data.put("icon", umsAdmin.getIcon());

        List<UmsRole> roleList = adminService.getRoleList(umsAdmin.getId());
        if (CollUtil.isNotEmpty(roleList)) {
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles", roles);
        }
        return CommonResult.success(data);
    }

    @ApiOperation(value = "登出功能")
    @PostMapping(value = "/logout")
    public CommonResult logout() {
        // TODO 删除token
        return CommonResult.success(null);
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @GetMapping(value = "/list")
    public CommonPage<UmsAdmin> list(@RequestParam(value = "keyword", required = false) String keyword,
                                     @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsAdmin> adminList = adminService.list(keyword, pageSize, pageNum);
        return CommonPage.restPage(adminList);
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping(value = "/{id}")
    public UmsAdmin getItem(@PathVariable Long id) {
        UmsAdmin admin = adminService.getById(id);
        return admin;
    }

    @ApiOperation("修改指定用户信息")
    @PostMapping(value = "/update/{id}")
    public Boolean update(@PathVariable Long id, @RequestBody UmsAdmin admin) {
        boolean success = adminService.update(id, admin);
        if (!success) {
            Asserts.fail("修改失败");
        }
        return true;
    }

    @ApiOperation("修改指定用户密码")
    @PostMapping(value = "/updatePassword")
    public CommonResult updatePassword(@Validated @RequestBody UpdateAdminPasswordParam updatePasswordParam) {
        int status = adminService.updatePassword(updatePasswordParam);
        if (status > 0) {
            return CommonResult.success(status);
        } else if (status == -1) {
            return CommonResult.failed("提交参数不合法");
        } else if (status == -2) {
            return CommonResult.failed("找不到该用户");
        } else if (status == -3) {
            return CommonResult.failed("旧密码错误");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除指定用户信息")
    @PostMapping(value = "/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean success = adminService.delete(id);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    /**
     * 获取验证码
     *
     * @return base64 二维码
     */
    @ApiOperation("获取验证码")
    @GetMapping("/code/image")
    @OperationLog(bizId = "#code", bizType = "image", msg = "'单纯的字符串'")
    public CaptchaDTO getImageCode(String code) {
        return captchaService.cacheCaptcha();
    }

    @ApiOperation("修改帐号状态")
    @PostMapping(value = "/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setStatus(status);
        boolean success = adminService.update(id, umsAdmin);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("给用户分配角色")
    @PostMapping(value = "/role/update")
    public CommonResult updateRole(@RequestParam("adminId") Long adminId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = adminService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping(value = "/role/{adminId}")
    @OperationLog(bizId = "#adminId", bizType = "ums", msg = "'获取指定用户的角色'")
    public List<UmsRole> getRoleList(@PathVariable Long adminId) {
        List<UmsRole> roleList = adminService.getRoleList(adminId);
        return roleList;
    }

    /**
     * test
     *
     * @return string
     */
    @GetMapping("/test")
    public String test() {
        // 示例记录日志信息
        LogUtils.print(LogParam.builder().bizType(LOG_BIZ_TYPE).object(null).build(), AnchorInfo.builder().businessId(TaskInfoUtils.generateBusinessId()).state(AnchorState.RECEIVE.getCode()).build());
        return "ok";
    }


}
